package stock.global.api.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    @Schema
    @Size(min = 8 , message = "id min length is 8")
    private String accountId;

    @Schema
    @Size(min = 8 , message = "password min length is 8")
    private String accountPassword;

    public MemberDto(
        String accountId,
        String accountPassword
    ) {
        this.setAccountId(accountId);
        this.setAccountPassword(accountPassword);
    }
}
