package stock.global.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.Ticker;

public interface TickerRepository extends JpaRepository<Ticker, String> , TickerDslRepository{
    
}
