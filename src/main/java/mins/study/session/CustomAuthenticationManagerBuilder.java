package mins.study.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Primary
@Configuration(value = "CustomAuthenticationManagerBuilder")
public class CustomAuthenticationManagerBuilder extends AuthenticationManagerBuilder {

    /**
     * Creates a new instance
     *
     * @param objectPostProcessor the {@link ObjectPostProcessor} instance to use.
     */
    public CustomAuthenticationManagerBuilder(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }


}
