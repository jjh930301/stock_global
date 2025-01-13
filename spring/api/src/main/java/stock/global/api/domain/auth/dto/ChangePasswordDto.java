package stock.global.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangePasswordDto {
    private final String prevPassword;
    private final String password;
    private final String confirmPassword;
}
