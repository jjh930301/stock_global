package stock.global.core.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class DayCandleId implements Serializable{
    @Column(name = "symbol", length = 12, nullable = false)
    private String symbol;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
