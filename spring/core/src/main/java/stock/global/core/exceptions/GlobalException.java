package stock.global.core.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.PersistenceException;
import stock.global.core.models.ApiRes;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiRes<?>> methodArgException(MethodArgumentNotValidException e) {
		List<String> messages = new ArrayList<>();
		for(Object arg : e.getDetailMessageArguments()) {
			if(!arg.toString().equals("")) messages.add(arg.toString());
		}
		messages.add(e.getMessage());
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
