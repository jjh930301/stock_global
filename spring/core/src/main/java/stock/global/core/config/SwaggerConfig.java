package stock.global.core.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import stock.global.core.exceptions.ApiException;
import stock.global.core.models.ApiRes;

@Profile({"dev" , "local"})
@Configuration
public class SwaggerConfig {
	@Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            ApiResponses apiResponses = operation.getResponses();
            if (apiResponses == null) {
                apiResponses = new ApiResponses();
                operation.setResponses(apiResponses);
            }
            apiResponses.putAll(this.getCommonResponses());
            return operation;
        };
    }

    private Map<String, ApiResponse> getCommonResponses() {
        Map<String, ApiResponse> responses = new HashMap<>();
        responses.put("400", this.badRequest());
        responses.put("500", this.internalServerResponse());
        return responses;
    }

    private ApiResponse badRequest() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setDescription("""
                reason
                """);
		apiResponse.setContent(
			new Content()
		);
        addContent(apiResponse, HttpStatus.BAD_REQUEST.value(), "Bad request");
        return apiResponse;
    }

    private ApiResponse internalServerResponse() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setDescription("""
                Internal Server Error 
                """);
        addContent(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        return apiResponse;
    }

    private void addContent(ApiResponse apiResponse, int status, String message) {
        Content content = new Content();
        MediaType mediaType = new MediaType();
        Schema<ApiRes<ApiException>> schema = new Schema<>();
        schema.example(
            ApiRes.builder()
                .status(status)
                .messages(Arrays.asList(message))
                .payload(null)
                .build()
        );
        mediaType.setSchema(schema);
        content.addMediaType("application/json", mediaType);
        apiResponse.setContent(content);
    }

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
