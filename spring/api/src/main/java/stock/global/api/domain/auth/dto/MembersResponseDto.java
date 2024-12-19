package stock.global.api.domain.auth.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import stock.global.core.enums.MemberTypeEnum;

@Getter
@Setter
@AllArgsConstructor
public class MembersResponseDto {
    private String accountId;
    private LocalDateTime createdAt;
    private MemberTypeEnum type;
}
