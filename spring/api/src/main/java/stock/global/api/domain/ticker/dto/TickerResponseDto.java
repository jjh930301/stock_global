package stock.global.api.domain.ticker.dto;

import java.math.BigDecimal;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TickerResponseDto {
    private String symbol;
    private BigDecimal marketValue;
    private List<DayCandleResponseDto> dayCandles;

    @QueryProjection
    public TickerResponseDto(
        String symbol,
        BigDecimal marketValue,
        List<DayCandleResponseDto> dayCandles
    ) {
        this.symbol = symbol;
        this.marketValue = marketValue;
        this.dayCandles = dayCandles;
    }

    public TickerResponseDto(String symbol, BigDecimal marketValue) {
        this.symbol = symbol;
        this.marketValue = marketValue;
    }

    public void setDayCandles(List<DayCandleResponseDto> dayCandles) {
        this.dayCandles = dayCandles;
    }
}
