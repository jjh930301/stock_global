package stock.global.api.repositories;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import stock.global.api.domain.ticker.dto.DayCandleResponseDto;
import stock.global.api.domain.ticker.dto.TickerResponseDto;
import stock.global.core.entities.QDayCandle;
import stock.global.core.entities.QTicker;


public class TickerDslRepositoryImpl implements TickerDslRepository {
    private final JPAQueryFactory factory;
    private final QTicker ticker = QTicker.ticker;
    private final QDayCandle dayCandles = QDayCandle.dayCandle;

    public TickerDslRepositoryImpl(JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public TickerResponseDto findCandlesBySymbol(String symbol) {
        
        TickerResponseDto t = factory
            .select(
                Projections.constructor(
                    TickerResponseDto.class, 
                    ticker.symbol.as("symbol"),
                    ticker.marketValue.as("marketvalue")
                )
            )
            .from(ticker)
            .where(ticker.symbol.eq(symbol))
            .fetchOne();
        if(t == null) return null;
        List<DayCandleResponseDto> candles = factory
            .select(
                Projections.constructor(
                    DayCandleResponseDto.class, 
                    dayCandles.close.as("close"),
                    dayCandles.open.as("open"),
                    dayCandles.high.as("high"),
                    dayCandles.low.as("low"),
                    dayCandles.volume.as("volume"),
                    dayCandles.id.date.as("date")
                )
            )
            .from(dayCandles)
            .where(dayCandles.id.symbol.eq(t.getSymbol()))
            .offset(1)
            .limit(100)
            .orderBy(dayCandles.id.date.desc())
            .fetch();
        t.setDayCandles(candles);
        return t;

    }
}
