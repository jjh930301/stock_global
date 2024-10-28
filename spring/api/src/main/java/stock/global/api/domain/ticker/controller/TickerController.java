package stock.global.api.domain.ticker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import stock.global.api.domain.ticker.service.TickerService;


@Tag(name="ticker")
@RequestMapping(path="ticker")
@RestController
public class TickerController {
    private final TickerService tickerService;

    public TickerController(TickerService tickerService) {
        this.tickerService = tickerService;
    }
    
}
