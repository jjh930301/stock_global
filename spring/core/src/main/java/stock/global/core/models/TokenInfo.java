package stock.global.core.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import stock.global.core.enums.MemberTypeEnum;

@Getter
@Setter
@Builder
public class TokenInfo {
    private Long id;
    private String accountId;
    private MemberTypeEnum type;
}
