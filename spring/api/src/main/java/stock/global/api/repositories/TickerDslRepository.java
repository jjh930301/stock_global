package stock.global.api.repositories;

import org.springframework.stereotype.Repository;

import stock.global.api.domain.ticker.dto.TickerResponseDto;

@Repository
public interface TickerDslRepository {
    TickerResponseDto findCandlesBySymbol(String symbol);
}
