package stock.global.core.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class DayCandleId implements Serializable{
    @Column(name="symbol")
    private String symbol;

    @Column(name="date")
    private LocalDate date;
}
