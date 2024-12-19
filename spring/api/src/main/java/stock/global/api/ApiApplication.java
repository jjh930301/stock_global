package stock.global.api;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import stock.global.core.aop.RateLimitAspect;
import stock.global.core.config.JpaConfig;
import stock.global.core.config.MybatisConfig;
import stock.global.core.config.QueryDslConfig;
import stock.global.core.config.SecurityConfig;
import stock.global.core.config.SmtpConfig;
import stock.global.core.config.SwaggerConfig;
import stock.global.core.config.TokenInterceptor;
import stock.global.core.config.TokenRoleResolver;
import stock.global.core.config.WebConfig;
import stock.global.core.exceptions.GlobalException;
import stock.global.core.util.JwtUtil;
import stock.global.core.util.StringUtil;

@SpringBootApplication
@Import({
	WebConfig.class,
	SecurityConfig.class, 
	JpaConfig.class,
	GlobalException.class, 
	SwaggerConfig.class,
	MybatisConfig.class,
	QueryDslConfig.class,
	JwtUtil.class,
	TokenInterceptor.class,
	TokenRoleResolver.class,
	SmtpConfig.class,
	RateLimitAspect.class,
	StringUtil.class
})
@EnableJpaAuditing
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApiApplication.class);
		app.setDefaultProperties(
			Collections.singletonMap(
				"server.port", 
				System.getenv("SPRING_API_PORT") == null 
					? "8090" 
					: System.getenv("SPRING_API_PORT")
			)
		);
		app.run(args);
	}

}
