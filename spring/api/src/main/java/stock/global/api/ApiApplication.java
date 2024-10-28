package stock.global.api;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import stock.global.core.config.GlobalException;
import stock.global.core.config.JpaConfig;
import stock.global.core.config.MybatisConfig;
import stock.global.core.config.QueryDslConfig;
import stock.global.core.config.SecurityConfig;
import stock.global.core.config.SwaggerConfig;

@SpringBootApplication
@Import({
	SecurityConfig.class, 
	JpaConfig.class,
	GlobalException.class, 
	SwaggerConfig.class,
	MybatisConfig.class,
	QueryDslConfig.class
})
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApiApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
		app.run(args);
	}

}
