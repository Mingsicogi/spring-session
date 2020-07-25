package mins.study.session;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@Configuration
@RequiredArgsConstructor
public class RedisSessionConfiguration {

    private final RedisIndexedSessionRepository redisIndexedSessionRepository;

}
