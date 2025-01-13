package stock.global.api.repositories;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;
import stock.global.core.entities.QMemberHistory;

@Slf4j
@Repository
public class MemberHistoryDslRepositoryImpl implements MemberHistoryDslRepository{

    private final JPAQueryFactory factory;
    private final QMemberHistory history = QMemberHistory.memberHistory;

    public MemberHistoryDslRepositoryImpl(JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean existByMemberId(Long id) {
        Integer fetchOne = factory
            .selectOne()
            .from(history)
            .where(history.id.id.eq(id))
            .orderBy(history.id.createdAt.desc())
            .fetchFirst();
        log.info("msg {}" , fetchOne);
        return fetchOne == null; 
    }
    
}
