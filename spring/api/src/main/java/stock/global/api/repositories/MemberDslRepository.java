package stock.global.api.repositories;

import stock.global.api.domain.auth.dto.MembersResponseDto;
import stock.global.api.domain.ticker.dto.PaginationDto;

public interface MemberDslRepository {
    MembersResponseDto findMembers(PaginationDto page);
}
