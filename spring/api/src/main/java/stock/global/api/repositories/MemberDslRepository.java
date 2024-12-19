package stock.global.api.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import stock.global.api.domain.auth.dto.MembersResponseDto;
import stock.global.api.domain.ticker.dto.PaginationDto;

@Repository
public interface MemberDslRepository {
    List<MembersResponseDto> findMembers(PaginationDto page);
}
