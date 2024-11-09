package stock.global.api.domain.ticker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.tags.Tag;
import stock.global.api.domain.ticker.dto.TickerResponseDto;
import stock.global.api.domain.ticker.service.TickerService;
import stock.global.core.annotations.ControllerInfo;
import stock.global.core.annotations.SwaggerInfo;
import stock.global.core.annotations.TokenRole;
import stock.global.core.models.ApiRes;
import stock.global.core.models.TokenInfo;



@Tag(name="ticker")
@ControllerInfo(path = "ticker")
public class TickerController {
    private final TickerService tickerService;

    public TickerController(TickerService tickerService) {
        this.tickerService = tickerService;
    }

    @SwaggerInfo(summary="get symbol")
    @GetMapping("/{symbol}")
    public ResponseEntity<ApiRes<TickerResponseDto>> getTicker(
        @PathVariable("symbol") String symbol,
        @TokenRole TokenInfo token
    ) {
        return ResponseEntity
            .ok()
            .body(this.tickerService.getTicker(symbol));
    }
    
    
}
