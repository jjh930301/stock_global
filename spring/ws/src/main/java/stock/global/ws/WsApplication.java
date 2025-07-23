package stock.global.ws;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import stock.global.core.config.JpaConfig;
import stock.global.core.config.RedisConfig;
import stock.global.core.config.SecurityConfig;
import stock.global.core.config.TokenInterceptor;
import stock.global.core.config.WebConfig;
import stock.global.core.util.JwtUtil;

@Import({
	WebConfig.class,
	TokenInterceptor.class,
	RedisConfig.class,
	SecurityConfig.class,
	JwtUtil.class,
	JpaConfig.class
})
@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WsApplication.class);
		app.setDefaultProperties(
			Collections.singletonMap(
				"server.port", 
				System.getenv("WS_PORT") == null 
					? "8100" 
					: System.getenv("WS_PORT")
			)
		);
		app.run(args);
	}

}
