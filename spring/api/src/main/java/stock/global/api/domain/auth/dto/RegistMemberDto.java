package stock.global.api.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import stock.global.core.enums.MemberTypeEnum;

@Getter
public class RegistMemberDto extends MemberDto{

    @Schema
    @NotNull(message="type is required")
    private MemberTypeEnum type; 
}
