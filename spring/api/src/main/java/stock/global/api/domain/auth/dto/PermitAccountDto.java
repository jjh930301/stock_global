package stock.global.api.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import stock.global.core.enums.MemberTypeEnum;

@Getter
public class PermitAccountDto {
    @Email
    private String email;
    
    @Type(MemberTypeEnum.class)
    private MemberTypeEnum type;
}
