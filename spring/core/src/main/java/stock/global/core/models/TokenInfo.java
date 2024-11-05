package stock.global.core.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenInfo {
    private Long id;
    private String accountId;
    private int type;
}
