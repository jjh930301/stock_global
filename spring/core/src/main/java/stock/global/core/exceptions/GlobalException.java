package stock.global.core.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.PersistenceException;
import stock.global.core.models.ApiRes;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
	public ResponseEntity<ApiRes<?>> handleValidationExceptions(Exception e) {
		List<String> messages = new ArrayList<>();
	
		if (e instanceof MethodArgumentNotValidException ex) {
			ex.getBindingResult().getFieldErrors().forEach(error ->
				messages.add(error.getDefaultMessage())
			);
		} 
		if (e instanceof MissingServletRequestParameterException ex) {
			messages.add("Missing parameter: " + ex.getParameterName());
		}
	
		messages.add(e != null ? e.getMessage() : "");
	
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(
				ApiRes.builder()
					.payload(null)
					.messages(messages)
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
	@ExceptionHandler(PersistenceException.class)
	public ResponseEntity<ApiRes<?>> databaseException(PersistenceException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(
				ApiRes
					.builder()
					.payload(null)
					.messages(List.of(
						e.getMessage(),
						e.getClass().getName()
					))
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
					.messages(List.of(
						e.getMessage() , 
						e.getClass().getName()
					))
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.build()	
			);
	}

}
