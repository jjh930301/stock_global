package stock.global.core.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tickers")
public class Ticker{
    
    @Id
    @Column(name = "symbol", length = 12, nullable = false)
    private String symbol;

    @Column(name = "stock_type", length = 20)
    private String stockType;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "ko_name", length = 100)
    private String koName;

    @Column(name = "nation_type", length = 12)
    private String nationType;

    @Column(name = "nation_code", length = 12)
    private String nationCode;

    @Column(name = "start_time", length = 20)
    private String startTime;

    @Column(name = "end_time", length = 20)
    private String endTime;

    @Column(name = "market_type", length = 20)
    private String marketType;

    @Column(name = "reuters_code", length = 20)
    private String reutersCode;

    @Column(name = "market_status", length = 10)
    private String marketStatus;

    @Column(name = "market_value", precision = 13, scale = 2)
    private BigDecimal marketValue;

    @Column(name = "stop_code", length = 20)
    private String stopCode;

    @Column(name = "stop_text", length = 100)
    private String stopText;

    @Column(name = "stop_name", length = 100)
    private String stopName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(
        targetEntity = DayCandle.class,
        fetch = FetchType.LAZY ,
        mappedBy="ticker"
    )
    private List<DayCandle> dayCandles;
}