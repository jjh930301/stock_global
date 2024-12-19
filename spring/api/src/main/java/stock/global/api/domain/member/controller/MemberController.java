package stock.global.api.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import stock.global.api.domain.member.service.MemberService;
import stock.global.core.annotations.ControllerInfo;
import stock.global.core.annotations.SwaggerInfo;
import stock.global.core.annotations.TokenRole;

import org.springframework.web.bind.annotation.GetMapping;

import stock.global.api.domain.auth.dto.MembersResponseDto;
import stock.global.api.domain.ticker.dto.PaginationDto;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.models.ApiRes;
import stock.global.core.models.TokenInfo;


@Tag(name="member")
@ControllerInfo(path = "member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(
        MemberService memberService
    ) {
        this.memberService = memberService;
    }

    @SwaggerInfo(summary="request members")
    @GetMapping
    public ResponseEntity<ApiRes<List<MembersResponseDto>>> getReqMembers(
        @TokenRole(MemberTypeEnum.ADMIN) TokenInfo token,
        PaginationDto page
    ) {
        return ResponseEntity
            .ok()
            .body(this.memberService.getReqMembers(page));
    }
    
    
}
