package mins.study.session;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class Welcome {

    private final RedisIndexedSessionRepository redisIndexedSessionRepository;

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails principal = (UserDetails) context.getAuthentication().getPrincipal();
        model.addAttribute("username", principal.getUsername());

        return "welcome";
    }

    @GetMapping("/session/find")
    @ResponseBody
    public ResponseEntity<Object> getSessionInfo(String username) {
        Map<String, ? extends Session> byPrincipalName = redisIndexedSessionRepository.findByPrincipalName(username);
        return ResponseEntity.ok(byPrincipalName);
    }

    @GetMapping("/session/make")
    @ResponseBody
    public ResponseEntity<Object> makeSessionWithoutSecurityFilter(String username, HttpServletRequest request) {
        request.getSession().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);

        return ResponseEntity.ok("OK");
    }
}
