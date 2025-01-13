package stock.global.core.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiException extends RuntimeException {
    private String message;
    @Builder.Default
    private HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public ApiException(String message) {
        this.message = message;
    }

    public ApiException(String message , HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}