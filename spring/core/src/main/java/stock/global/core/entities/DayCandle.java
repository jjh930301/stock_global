package stock.global.core.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "day_candles")
public class DayCandle {

    @EmbeddedId
    private DayCandleId id;

    @Column(name = "open", precision = 10, scale = 3)
    private BigDecimal open;

    @Column(name = "high", precision = 10, scale = 3)
    private BigDecimal high;

    @Column(name = "low", precision = 10, scale = 3)
    private BigDecimal low;

    @Column(name = "close", precision = 10, scale = 3)
    private BigDecimal close;

    @Column(name = "volume", nullable = false)
    private Long volume;

    @MapsId(value="symbol")
    @ManyToOne(targetEntity=Ticker.class)
    @JoinColumn(name = "symbol" , columnDefinition="VARCHAR(12) NOT NULL")
    private Ticker ticker;
}