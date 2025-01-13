package stock.global.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.global.core.entities.Member;

public interface MemberRepository extends JpaRepository<Member, Long> , MemberDslRepository{
    Optional<Member> findByAccountId(String accountId);
    Optional<Member> findByAccountIdAndDeletedAtIsNull(String accountId);
}
