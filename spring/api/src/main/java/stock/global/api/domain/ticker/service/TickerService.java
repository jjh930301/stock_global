package stock.global.api.domain.ticker.service;

import org.springframework.stereotype.Service;

import stock.global.api.domain.ticker.dto.TickerResponseDto;
import stock.global.api.repositories.TickerRepository;
import stock.global.core.models.ApiRes;

@Service
public class TickerService {
    private final TickerRepository tickerRepository;

    public TickerService(
        TickerRepository tickerRepository
    ) {
        this.tickerRepository = tickerRepository;
    }

    public ApiRes<TickerResponseDto> getTicker(String symbol) {
        return ApiRes
            .<TickerResponseDto>builder()
            .payload(this.tickerRepository.findCandlesBySymbol(symbol))
            .build();
    }
}
