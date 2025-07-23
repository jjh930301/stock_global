package stock.global.kiwoom;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import stock.global.core.config.JpaConfig;

@SpringBootApplication
@Import({
	JpaConfig.class,
	
})
public class KiwoomApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(KiwoomApplication.class);
		app.setDefaultProperties(
			Collections.singletonMap(
				"server.port", 
				System.getenv("SPRING_API_PORT") == null 
					? "8110" 
					: System.getenv("SPRING_API_PORT")
			)
		);
		app.run(args);
	}

}
