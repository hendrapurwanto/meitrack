package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLatestAutomaticEventReport is a Querydsl query type for LatestAutomaticEventReport
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLatestAutomaticEventReport extends EntityPathBase<LatestAutomaticEventReport> {

    private static final long serialVersionUID = -1456051881L;

    public static final QLatestAutomaticEventReport latestAutomaticEventReport = new QLatestAutomaticEventReport("latestAutomaticEventReport");

    public final NumberPath<Integer> altitude = createNumber("altitude", Integer.class);

    public final StringPath analogInputValue = createString("analogInputValue");

    public final StringPath baseStationInfo = createString("baseStationInfo");

    public final NumberPath<Integer> direction = createNumber("direction", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endTrip = createDateTime("endTrip", java.time.LocalDateTime.class);

    public final NumberPath<Long> eventCode = createNumber("eventCode", Long.class);

    public final StringPath fuelPercentage = createString("fuelPercentage");

    public final StringPath geom = createString("geom");

    public final NumberPath<Integer> gsmSignalNumber = createNumber("gsmSignalNumber", Integer.class);

    public final NumberPath<Double> hdop = createNumber("hdop", Double.class);

    public final StringPath ignition = createString("ignition");

    public final NumberPath<Long> imeiNumber = createNumber("imeiNumber", Long.class);

    public final StringPath ioPortStatus = createString("ioPortStatus");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Long> mileage = createNumber("mileage", Long.class);

    public final NumberPath<Integer> numberOfSatellites = createNumber("numberOfSatellites", Integer.class);

    public final StringPath positioningStatus = createString("positioningStatus");

    public final NumberPath<Long> runtime = createNumber("runtime", Long.class);

    public final NumberPath<Integer> speed = createNumber("speed", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public QLatestAutomaticEventReport(String variable) {
        super(LatestAutomaticEventReport.class, forVariable(variable));
    }

    public QLatestAutomaticEventReport(Path<? extends LatestAutomaticEventReport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLatestAutomaticEventReport(PathMetadata metadata) {
        super(LatestAutomaticEventReport.class, metadata);
    }

}

