package mins.study.session;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * Spring Security 관련 설정
 *
 * @author minssogi
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SpringSessionBackedSessionRegistry<? extends Session> springSessionBackedSessionRegistry;
    private final GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    /**
     * test계정 설정
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception e
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("minssogi")
                .password(this.passwordEncoder().encode("123"))
                .roles("ADMIN")

        ;
    }


    /**
     * spring security config
     *
     * @param http HttpSecurity
     * @throws Exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/session/**").permitAll(); // test 를 위한 uri pattern은 예외 처리

        http.formLogin().defaultSuccessUrl("/welcome");

        http.oauth2Login()
//                .successHandler(googleOAuth2SuccessHandler)
                .defaultSuccessUrl("/idpWelcome")

        ;

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션이 필요하면 생성하도록 셋팅
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(springSessionBackedSessionRegistry); // username 기준으로 사용할 수 있는 session 갯수

        http.authorizeRequests().anyRequest().authenticated(); // 나머지 페이지는 로그인 후 이용 가능
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
