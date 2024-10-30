package stock.global.api.domain.ticker.service;

import org.springframework.stereotype.Service;

import stock.global.api.dao.TickerDao;
import stock.global.api.domain.ticker.dto.TickerResponseDto;
import stock.global.api.repositories.TickerRepository;
import stock.global.core.models.ApiRes;

@Service
public class TickerService {
    private final TickerDao tickerDao;
    private final TickerRepository tickerRepository;

    public TickerService(
        TickerDao tickerDao,
        TickerRepository tickerRepository
    ) {
        this.tickerDao = tickerDao;
        this.tickerRepository = tickerRepository;
    }

    public ApiRes<TickerResponseDto> getTicker(String symbol) {
        return ApiRes
            .<TickerResponseDto>builder()
            .payload(this.tickerRepository.findCandlesBySymbol(symbol))
            .build();
    }
}
