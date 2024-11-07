package stock.global.api.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import stock.global.core.enums.MemberTypeEnum;

@Getter
@Setter
public class RegistMemberDto extends MemberDto{

    @Schema
    @NotNull(message="type is required")
    private MemberTypeEnum type; 

    public RegistMemberDto(String accountId , String password , MemberTypeEnum type) {
        this.setAccountId(accountId);
        this.setAccountPassword(password);
        this.setType(type);
    }
}
