package stock.global.api.repositories;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import stock.global.api.domain.kr.dto.IndexDayCandleDto;
import stock.global.core.entities.QKrIndexDayCandle;

public class KrIndexDayCandleDslRepositoryImpl implements  KrIndexDayCandleDslRepository{
    private final JPAQueryFactory factory;
    private final QKrIndexDayCandle dayCandle = QKrIndexDayCandle.krIndexDayCandle;

    public KrIndexDayCandleDslRepositoryImpl(
        JPAQueryFactory factory
    ) {
        this.factory = factory;
    }

    @Override
    public List<IndexDayCandleDto> findAllByMarket(
        int market,
        LocalDate date
    ) {
        BooleanBuilder condition = new BooleanBuilder();
        JPAQuery<IndexDayCandleDto> query = factory
            .select(
                Projections.constructor(
                    IndexDayCandleDto.class, 
                    dayCandle.id.date.as("date"),
                    dayCandle.id.market.as("market"),
                    dayCandle.open.as("open"),
                    dayCandle.high.as("high"),
                    dayCandle.low.as("low"),
                    dayCandle.close.as("close"),
                    dayCandle.volume.as("volume")
                )
            )
            .from(dayCandle);
        if (date != null) {
            condition.and(dayCandle.id.date.before(date));
        }
        condition.and(dayCandle.id.market.eq(market));
        query.where(condition);
        return query
            .limit(120)
            .orderBy(dayCandle.id.date.desc())
            .fetch();
    }

    
}
