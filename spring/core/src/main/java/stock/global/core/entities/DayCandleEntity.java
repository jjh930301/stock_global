package stock.global.core.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DayCandleId.class)
@Entity
@Table(name = "day_candles")
public class DayCandleEntity {

    @Id
    @Column(name = "symbol", length = 12, nullable = false)
    private String symbol;

    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;

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

    @ManyToOne
    @JoinColumn(name = "symbol", insertable = false, updatable = false)
    private TickerEntity ticker;
}