package stock.global.core.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTickerEntity is a Querydsl query type for TickerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTickerEntity extends EntityPathBase<TickerEntity> {

    private static final long serialVersionUID = -1227895810L;

    public static final QTickerEntity tickerEntity = new QTickerEntity("tickerEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<DayCandleEntity, QDayCandleEntity> dayCandles = this.<DayCandleEntity, QDayCandleEntity>createList("dayCandles", DayCandleEntity.class, QDayCandleEntity.class, PathInits.DIRECT2);

    public final StringPath endTime = createString("endTime");

    public final StringPath koName = createString("koName");

    public final StringPath marketStatus = createString("marketStatus");

    public final StringPath marketType = createString("marketType");

    public final NumberPath<java.math.BigDecimal> marketValue = createNumber("marketValue", java.math.BigDecimal.class);

    public final StringPath name = createString("name");

    public final StringPath nationCode = createString("nationCode");

    public final StringPath nationType = createString("nationType");

    public final StringPath reutersCode = createString("reutersCode");

    public final StringPath startTime = createString("startTime");

    public final StringPath stockType = createString("stockType");

    public final StringPath stopCode = createString("stopCode");

    public final StringPath stopName = createString("stopName");

    public final StringPath stopText = createString("stopText");

    public final StringPath symbol = createString("symbol");

    public QTickerEntity(String variable) {
        super(TickerEntity.class, forVariable(variable));
    }

    public QTickerEntity(Path<? extends TickerEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTickerEntity(PathMetadata metadata) {
        super(TickerEntity.class, metadata);
    }

}

