package mins.study.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * Session 관련 설정
 *  - redis session 사용.
 *
 *
 * @author minssogi
 */
@EnableRedisHttpSession(
        maxInactiveIntervalInSeconds = 60 // session expire time(second) for testing
)
@Configuration
public class RedisSessionConfiguration {

    @Bean
    public SpringSessionBackedSessionRegistry<? extends Session> springSessionBackedSessionRegistry(RedisIndexedSessionRepository redisIndexedSessionRepository) {
        return new SpringSessionBackedSessionRegistry<>(redisIndexedSessionRepository);
    }
}
