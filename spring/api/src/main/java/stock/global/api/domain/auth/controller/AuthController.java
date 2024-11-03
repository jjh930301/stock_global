package stock.global.api.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import stock.global.api.domain.auth.dto.MemberDto;
import stock.global.api.domain.auth.dto.MemberResponseDto;
import stock.global.api.domain.auth.dto.RegistMemberDto;
import stock.global.api.domain.auth.service.AuthService;
import stock.global.core.annotations.ControllerInfo;
import stock.global.core.annotations.SwaggerInfo;
import stock.global.core.models.ApiRes;


@Tag(name = "auth")
@ControllerInfo(path = "auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @SwaggerInfo(summary="sign-in")
    @PostMapping("")
    public ResponseEntity<ApiRes<MemberResponseDto>> registUser(
        @Valid @RequestBody RegistMemberDto dto
    ) {
        return ResponseEntity
            .ok()
            .body(this.authService.registUser(dto));
    }

    @SwaggerInfo(summary="login")
    @PostMapping("login")
    public ResponseEntity<ApiRes<MemberResponseDto>> loginMember(@Valid @RequestBody MemberDto dto) {
        return ResponseEntity
            .ok()
            .body(this.authService.loginMember(dto));
    }
}
