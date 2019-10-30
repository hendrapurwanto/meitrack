package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLatestVehicleInfo is a Querydsl query type for LatestVehicleInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLatestVehicleInfo extends EntityPathBase<LatestVehicleInfo> {

    private static final long serialVersionUID = 1940567910L;

    public static final QLatestVehicleInfo latestVehicleInfo = new QLatestVehicleInfo("latestVehicleInfo");

    public final NumberPath<Integer> altitude = createNumber("altitude", Integer.class);

    public final StringPath branch = createString("branch");

    public final NumberPath<Integer> direction = createNumber("direction", Integer.class);

    public final StringPath driver = createString("driver");

    public final StringPath geom = createString("geom");

    public final BooleanPath gps = createBoolean("gps");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ignition = createString("ignition");

    public final NumberPath<Long> imei = createNumber("imei", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath model = createString("model");

    public final StringPath phone = createString("phone");

    public final StringPath plate = createString("plate");

    public final NumberPath<Integer> speed = createNumber("speed", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public final StringPath type = createString("type");

    public final NumberPath<Integer> yearMade = createNumber("yearMade", Integer.class);

    public QLatestVehicleInfo(String variable) {
        super(LatestVehicleInfo.class, forVariable(variable));
    }

    public QLatestVehicleInfo(Path<? extends LatestVehicleInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLatestVehicleInfo(PathMetadata metadata) {
        super(LatestVehicleInfo.class, metadata);
    }

}

