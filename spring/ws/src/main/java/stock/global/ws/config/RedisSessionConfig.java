package stock.global.ws.config;

import java.util.Set;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import jakarta.servlet.SessionTrackingMode;

@Profile("!test")
@Configuration
public class RedisSessionConfig extends AbstractHttpSessionApplicationInitializer {

    @Bean
    public ServletContextInitializer sessionTrackingModeInitializer() {
        return servletContext -> {
            // 세션 추적 방식을 쿠키로 설정
            servletContext.getSessionCookieConfig().setMaxAge(30 * 60); // 세션 만료 시간 (30분)
            servletContext.setSessionTrackingModes(Set.of(SessionTrackingMode.COOKIE));  // 세션 추적 방식으로 쿠키 사용
        };
    }
}