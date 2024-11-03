package stock.global.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Profile({"dev" , "local"})
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		String jwt = "JWT";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt); 
		Components components = new Components().addSecuritySchemes(
			jwt, 
			new SecurityScheme()
				.name(jwt)
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")
				.in(SecurityScheme.In.HEADER)
				.name(HttpHeaders.AUTHORIZATION)
		);
		return new OpenAPI()
			.info(new Info()
			.description("")
			.title("stock global"))
			.addSecurityItem(securityRequirement)
			.components(components);
	}
}
