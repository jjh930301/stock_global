package stock.global.core.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDayCandleEntity is a Querydsl query type for DayCandleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayCandleEntity extends EntityPathBase<DayCandleEntity> {

    private static final long serialVersionUID = 1190457595L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDayCandleEntity dayCandleEntity = new QDayCandleEntity("dayCandleEntity");

    public final NumberPath<java.math.BigDecimal> close = createNumber("close", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> high = createNumber("high", java.math.BigDecimal.class);

    public final QDayCandleId id;

    public final NumberPath<java.math.BigDecimal> low = createNumber("low", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> open = createNumber("open", java.math.BigDecimal.class);

    public final QTickerEntity ticker;

    public final NumberPath<Long> volume = createNumber("volume", Long.class);

    public QDayCandleEntity(String variable) {
        this(DayCandleEntity.class, forVariable(variable), INITS);
    }

    public QDayCandleEntity(Path<? extends DayCandleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDayCandleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDayCandleEntity(PathMetadata metadata, PathInits inits) {
        this(DayCandleEntity.class, metadata, inits);
    }

    public QDayCandleEntity(Class<? extends DayCandleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QDayCandleId(forProperty("id")) : null;
        this.ticker = inits.isInitialized("ticker") ? new QTickerEntity(forProperty("ticker")) : null;
    }

}

