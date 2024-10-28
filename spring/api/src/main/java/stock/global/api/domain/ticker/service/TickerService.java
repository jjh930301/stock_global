package stock.global.api.domain.ticker.service;

import org.springframework.stereotype.Service;

import stock.global.api.domain.ticker.dao.TickerDao;
import stock.global.api.domain.ticker.repository.TickerRepository;

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
}
