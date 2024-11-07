package stock.global.api.domain.auth.service;

import java.util.Arrays;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import stock.global.api.domain.auth.dto.MemberDto;
import stock.global.api.domain.auth.dto.MemberResponseDto;
import stock.global.api.domain.auth.dto.RegistMemberDto;
import stock.global.api.repositories.MemberRepository;
import stock.global.core.entities.MemberEntity;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.exceptions.ApiException;
import stock.global.core.models.ApiRes;
import stock.global.core.util.JwtUtil;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(
        MemberRepository memberRepository,
        BCryptPasswordEncoder bcryptEncoder,
        JwtUtil jwtUtil
    ) {
        this.memberRepository = memberRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    public ApiRes<MemberResponseDto> registUser(RegistMemberDto dto) {
        MemberEntity member = MemberEntity
            .builder()
            .accountId(dto.getAccountId())
            .password(this.bcryptEncoder.encode(dto.getAccountPassword()))
            .type(
                dto.getType() == MemberTypeEnum.ADMIN 
                    ? MemberTypeEnum.ADMIN 
                    : MemberTypeEnum.USER
            )
            .build();
        this.memberRepository.save(member);

        String accessToken = this.jwtUtil.createToken(
            member.getId(), 
            member.getAccountId(), 
            member.getType()
        );
        return ApiRes
            .<MemberResponseDto>builder()
            .payload(
                MemberResponseDto
                    .builder()
                    .accountId(member.getAccountId())
                    .accessToken(accessToken)
                    .type(member.getType())
                    .build()
            )
            .build();
    }

    public ApiRes<MemberResponseDto> loginMember(MemberDto dto) {
        MemberEntity member = this.memberRepository.findByAccountId(dto.getAccountId());
        if(
            member == null || 
            !this.bcryptEncoder.matches(dto.getAccountPassword(), member.getPassword())
        ) throw new ApiException("check account id and password");
        return ApiRes
            .<MemberResponseDto>builder()
            .messages(Arrays.asList("Successfully login %s".formatted(member.getAccountId())))
            .payload(
                MemberResponseDto
                    .builder()
                    .accountId(member.getAccountId())
                    .accessToken(
                        this.jwtUtil.createToken(
                            member.getId(), 
                            member.getAccountId(), 
                            member.getType()
                        )
                    )
                    .type(member.getType())
                    .build()
            )
            .build();
    }
}
