package stock.global.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.TickerEntity;

public interface TickerRepository extends JpaRepository<TickerEntity, String> , TickerDslRepository{
    
}
