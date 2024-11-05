package stock.global.core.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDayCandleId is a Querydsl query type for DayCandleId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDayCandleId extends BeanPath<DayCandleId> {

    private static final long serialVersionUID = 22636083L;

    public static final QDayCandleId dayCandleId = new QDayCandleId("dayCandleId");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath symbol = createString("symbol");

    public QDayCandleId(String variable) {
        super(DayCandleId.class, forVariable(variable));
    }

    public QDayCandleId(Path<? extends DayCandleId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDayCandleId(PathMetadata metadata) {
        super(DayCandleId.class, metadata);
    }

}

