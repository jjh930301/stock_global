package stock.global.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> , MemberDslRepository{
    Optional<MemberEntity> findByAccountId(String accountId);
    Optional<MemberEntity> findByAccountIdAndDeletedAtIsNull(String accountId);
}
