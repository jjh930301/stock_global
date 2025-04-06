package stock.global.api.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import stock.global.api.domain.kr.dto.IndexDayCandleDto;

@Repository
public interface KrIndexDayCandleDslRepository {
    List<IndexDayCandleDto> findAllByMarket(int market , LocalDate date);
}
