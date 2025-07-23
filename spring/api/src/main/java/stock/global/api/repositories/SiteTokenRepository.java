package stock.global.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.SiteToken;

public interface SiteTokenRepository extends JpaRepository<SiteToken , Long>{
    
}
