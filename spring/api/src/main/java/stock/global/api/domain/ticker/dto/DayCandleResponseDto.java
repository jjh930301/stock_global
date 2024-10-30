package stock.global.api.domain.ticker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DayCandleResponseDto {
    private LocalDate date;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private Long volume;

    public DayCandleResponseDto(
        BigDecimal close,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        Long volume,
        LocalDate date
    ) {
        this.close = close;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.date = date;
    }
}
