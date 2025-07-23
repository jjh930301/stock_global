package stock.global.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.KrIndexDayCandle;
import stock.global.core.entities.KrIndexDayCandleId;

public interface KrIndexDayCandleRepository extends JpaRepository<KrIndexDayCandle, KrIndexDayCandleId> , KrIndexDayCandleDslRepository{
    
}
