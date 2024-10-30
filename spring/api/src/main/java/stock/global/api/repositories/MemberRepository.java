package stock.global.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByAccountId(String accountId);
}
