package stock.global.api.repositories;

import org.springframework.stereotype.Repository;

import stock.global.api.domain.auth.dto.MembersResponseDto;
import stock.global.api.domain.ticker.dto.PaginationDto;

@Repository
public interface MemberDslRepository {
    MembersResponseDto findMembers(PaginationDto page);
}
