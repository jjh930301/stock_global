package stock.global.core.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ApiException(String message) {
        this.message = message;
    }
}