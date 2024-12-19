package stock.global.api.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import stock.global.api.domain.auth.dto.MembersResponseDto;
import stock.global.api.domain.ticker.dto.PaginationDto;
import stock.global.api.repositories.MemberRepository;
import stock.global.core.models.ApiRes;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(
        MemberRepository memberRepository
    ) {
        this.memberRepository = memberRepository;
    }

    public ApiRes<List<MembersResponseDto>> getReqMembers(PaginationDto page) {
        return ApiRes
            .<List<MembersResponseDto>>builder()
            .payload(this.memberRepository.findMembers(page))
            .build();
    }
}
