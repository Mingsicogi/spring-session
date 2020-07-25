package mins.study.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Session 관련 설정
 *  - redis session 사용.
 *
 *
 * @author minssogi
 */
@EnableRedisHttpSession
@Configuration
public class RedisSessionConfiguration {


}
