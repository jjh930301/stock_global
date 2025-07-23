package stock.global.core.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "kr_index_day_candles")
public class KrIndexDayCandle {

    @EmbeddedId
    private KrIndexDayCandleId id;
    
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
}
