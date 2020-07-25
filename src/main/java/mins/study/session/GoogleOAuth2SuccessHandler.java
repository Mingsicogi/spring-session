package mins.study.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public static final Map<String, String> idpRepository = new HashMap<>();
    private final AuthenticationManagerBuilder auth;

    @PostConstruct
    public void init() {
        idpRepository.put("codedoctor119@gmail.com", "minssogi");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = null;
        try {
            DefaultOidcUser user = (DefaultOidcUser)authentication.getPrincipal();
            String usernameInSpringSecurity = idpRepository.get(user.getUserInfo().getEmail());
            userDetails = auth.inMemoryAuthentication().getUserDetailsService().loadUserByUsername(usernameInSpringSecurity);

            log.info("############ {}, login by google idp service", userDetails.getUsername());
        }catch (Exception e) {
            log.error("ERROR!! Cannot get inMemoryAuthentication repository!!");
            return;
        }
    }

}
