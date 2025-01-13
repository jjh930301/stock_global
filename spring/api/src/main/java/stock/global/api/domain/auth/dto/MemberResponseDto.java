package stock.global.api.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import stock.global.core.enums.MemberTypeEnum;

@Builder
@Getter
public class MemberResponseDto {
    private final String accountId;
    private final MemberTypeEnum type;
    private final boolean isHIstory;
    private final String accessToken;
}
