package mins.study.session;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration extends SpringBootServletInitializer {

    private final SpringSessionBackedSessionRegistry<? extends Session> springSessionBackedSessionRegistry;

//    @Bean
    public FilterRegistrationBean<SessionAdditionalInfoFilter> sessionAdditionalInfoFilterFilterRegistrationBean() {
        FilterRegistrationBean<SessionAdditionalInfoFilter> filter = new FilterRegistrationBean<>();
        filter.addUrlPatterns("/session/*");
//        filter.setOrder(5001);
        filter.setFilter(new SessionAdditionalInfoFilter());

        return filter;
    }

//    @Bean
    public FilterRegistrationBean<SessionManagementFilter> sessionManagementFilterFilterRegistrationBean() {
        FilterRegistrationBean<SessionManagementFilter> filter = new FilterRegistrationBean<>();
        filter.addUrlPatterns("/session/*");
//        filter.setOrder(5000);
        filter.setFilter(new SessionManagementFilter(new HttpSessionSecurityContextRepository(),
                new ConcurrentSessionControlAuthenticationStrategy(springSessionBackedSessionRegistry)));

        return filter;
    }
}
