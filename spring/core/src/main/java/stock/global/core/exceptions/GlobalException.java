package stock.global.core.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import stock.global.core.models.ApiRes;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiRes<?>> methodArgException(MethodArgumentNotValidException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(
				ApiRes.builder()
					.payload(null)
					.messages(Arrays.asList(
						e.getDetailMessageCode(),
						e.getMessage()
					))
					.status(HttpStatus.BAD_REQUEST.value())
					.build()
			);
	}

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiRes<?>> apiException(ApiException e) {
		return ResponseEntity
			.status(e.getHttpStatus())
			.body(
				ApiRes.builder()
					.payload(e.getCause())
					.messages(Arrays.asList(e.getMessage()))
					.status(
						e.getHttpStatus() == null 
							? HttpStatus.BAD_REQUEST.value() 
							: e.getHttpStatus().value()
					)
					.build()
			);
    }

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiRes<?>> globalException(Exception e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(
				ApiRes.builder()
					.payload(null)
					.messages(Arrays.asList(e.getMessage() , Arrays.toString(e.getStackTrace())))
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.build()	
			);
	}

}
