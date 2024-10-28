package stock.global.api.domain.ticker.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class TickerDslRepositoryImpl implements TickerDslRepository {
    private final JPAQueryFactory factory;

    public TickerDslRepositoryImpl(JPAQueryFactory factory) {
        this.factory = factory;
    }
}
