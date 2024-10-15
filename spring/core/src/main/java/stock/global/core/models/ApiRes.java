package stock.global.core.models;

import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiRes<T> {
    private T payload;
    @Builder.Default
    private List<String> messages = new ArrayList();
    private int status;

    public ApiRes() {
        this.payload = null;
    }

    public ApiRes(T payload) {
        this.payload = payload;
    }

    public ApiRes(T payload , String message) {
        this.payload = payload;
        this.messages.add(message);
    }

    public ApiRes(T payload , int status) {
        this.payload = payload;
        this.status = status;
    }

    public ApiRes(T payload , int status , String message) {
        this.payload = payload;
        this.status = status;
        this.messages.add(message);
    }
}
