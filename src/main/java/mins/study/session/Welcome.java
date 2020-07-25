package mins.study.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.List;
import java.util.Map;

import static mins.study.session.GoogleOAuth2SuccessHandler.idpRepository;

@Controller
@RequiredArgsConstructor
public class Welcome {

    private final RedisIndexedSessionRepository redisIndexedSessionRepository;
    private final SpringSessionBackedSessionRegistry<? extends Session> springSessionBackedSessionRegistry;
    private final ObjectMapper objectMapper;

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails principal = (UserDetails) context.getAuthentication().getPrincipal();
        model.addAttribute("username", principal.getUsername());

        return "welcome";
    }

    @RequestMapping("/idpWelcome")
    public String idpWelcome(Model model, OAuth2AuthenticationToken authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        DefaultOidcUser principal = (DefaultOidcUser) context.getAuthentication().getPrincipal();

        String username = idpRepository.get(principal.getEmail());
        model.addAttribute("username", username);
        model.addAttribute("idpId", principal.getEmail());
        model.addAttribute("idpType", authentication.getAuthorizedClientRegistrationId());

        return "idpWelcome";
    }

    @GetMapping("/session/info")
    @ResponseBody
    public ResponseEntity<Object> getSessionFindAll(HttpServletRequest request) {
        SessionInformation sessionInformation = springSessionBackedSessionRegistry.getSessionInformation(request.getSession().getId());
        return ResponseEntity.ok(sessionInformation);
    }

    @GetMapping("/session/find")
    @ResponseBody
    public ResponseEntity<Object> getSessionInfo(String username) {
        Map<String, ? extends Session> byPrincipalName = redisIndexedSessionRepository.findByPrincipalName(username);
        return ResponseEntity.ok(byPrincipalName);
    }

    @GetMapping("/session/info/details")
    @ResponseBody
    public ResponseEntity<Object> getSessionInfoDetails(String username, HttpServletRequest request) {
        Map<String, ? extends Session> sessionInfo = redisIndexedSessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
        Session session = sessionInfo.get(request.getSession().getId());

        SessionAdditionalInfo addInfo = null;
        if(session != null)
            addInfo = objectMapper.convertValue(session.getAttribute("addInfo"), SessionAdditionalInfo.class);

        return ResponseEntity.ok(addInfo);
    }

    @GetMapping("/session/make")
    @ResponseBody
    public ResponseEntity<Object> makeSessionWithoutSecurityFilter(String username, HttpServletRequest request) {
        request.getSession().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);

        return ResponseEntity.ok("OK");
    }
}
