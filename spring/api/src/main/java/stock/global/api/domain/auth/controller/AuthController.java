package stock.global.api.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import stock.global.api.domain.auth.dto.ChangePasswordDto;
import stock.global.api.domain.auth.dto.MemberDto;
import stock.global.api.domain.auth.dto.MemberResponseDto;
import stock.global.api.domain.auth.dto.PermitAccountDto;
import stock.global.api.domain.auth.service.AuthService;
import stock.global.core.annotations.ControllerInfo;
import stock.global.core.annotations.RateLimit;
import stock.global.core.annotations.SwaggerInfo;
import stock.global.core.annotations.TokenRole;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.models.ApiRes;
import stock.global.core.models.TokenInfo;


@Tag(name = "auth")
@ControllerInfo(path = "auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @SwaggerInfo(summary="login")
    @PostMapping("login")
    public ResponseEntity<ApiRes<MemberResponseDto>> loginMember(
        @Valid @RequestBody MemberDto dto,
        HttpServletRequest request
    ) {
        return ResponseEntity
            .ok()
            .body(this.authService.loginMember(dto , request.getRemoteAddr()));
    }

    @SwaggerInfo(summary="request create account")
    @RateLimit(limit = 1, duration = 60 , message="try it 1min later")
    @PostMapping("/{email}")
    public ResponseEntity<ApiRes<Boolean>> postAccount(
        @PathVariable String email
    ) {
        return ResponseEntity
            .ok()
            .body(this.authService.postAccount(email));
    }

    @SwaggerInfo(summary="permit account")
    @PutMapping
    public ResponseEntity<ApiRes<?>> putPermitAccount(
        @Valid
        @RequestBody 
        PermitAccountDto dto,
        @TokenRole(MemberTypeEnum.ADMIN) TokenInfo token
    ) {
        return ResponseEntity
            .ok()
            .body(this.authService.permitAccount(dto , token.getId()));
    }


    @SwaggerInfo(summary="member info")
    @GetMapping
    public ResponseEntity<ApiRes<MemberResponseDto>> getMemberInfo(
        @TokenRole TokenInfo token
    ) {
        return ResponseEntity
            .ok()
            .body(this.authService.getUserInfo(token));
    }

    @SwaggerInfo(summary="change password")
    @PatchMapping
    public ResponseEntity<ApiRes<Boolean>> patchPassword(
        @TokenRole TokenInfo token,
        @RequestBody ChangePasswordDto body
    ) {
        return ResponseEntity
            .ok()
            .body(this.authService.patchPassword(token, body));
    }
    
    
}
