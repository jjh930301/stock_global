package stock.global.api.domain.member.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import stock.global.api.domain.auth.dto.MembersResponseDto;
import stock.global.api.domain.ticker.dto.PaginationDto;
import stock.global.api.repositories.MemberRepository;
import stock.global.core.models.ApiRes;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(
        MemberRepository memberRepository
    ) {
        this.memberRepository = memberRepository;
    }

    public ApiRes<MembersResponseDto> getReqMembers(PaginationDto page) {
        return ApiRes
            .<MembersResponseDto>builder()
            .payload(this.memberRepository.findMembers(page))
            .messages(Arrays.asList("successfully getting members"))
            .build();
    }
}
