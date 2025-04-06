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
public class KrIndexDayCandleId implements Serializable{
    @Column(name = "market" , columnDefinition = "tinyint")
    private int market;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
