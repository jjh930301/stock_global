package stock.global.ws;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WsApplication.class);
		app.setDefaultProperties(
			Collections.singletonMap(
				"server.port", 
				System.getenv("WS_PORT") == null 
					? "8090" 
					: System.getenv("WS_PORT")
			)
		);
		app.run(args);
	}

}
