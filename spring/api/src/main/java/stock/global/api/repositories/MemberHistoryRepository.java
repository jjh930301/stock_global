package stock.global.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.MemberHistory;
import stock.global.core.entities.MemberHistoryId;

public interface  MemberHistoryRepository extends JpaRepository<MemberHistory , MemberHistoryId> , MemberHistoryDslRepository{
    
}
