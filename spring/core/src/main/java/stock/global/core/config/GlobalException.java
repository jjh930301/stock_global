package stock.global.core.config;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import stock.global.core.exceptions.ApiException;
import stock.global.core.models.ApiRes;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiRes<?>> globalException(ApiException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(
				ApiRes.builder()
					.payload(e.getCause())
					.messages(Arrays.asList(e.getMessage()))
					.status(HttpStatus.BAD_REQUEST.value())
					.build()
			);
    }

}
