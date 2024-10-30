package stock.global.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation()
@ApiResponses(value = {
    @ApiResponse(responseCode = "200")
})
@SecurityRequirement(name = "bearerAuth")
public @interface SwaggerInfo {
    String summary() default "";

    String description() default "";

    boolean deprecated() default false;

    Class<?> implementation() default Void.class;
}
