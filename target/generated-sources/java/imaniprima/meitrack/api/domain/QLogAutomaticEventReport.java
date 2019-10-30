package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLogAutomaticEventReport is a Querydsl query type for LogAutomaticEventReport
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLogAutomaticEventReport extends EntityPathBase<LogAutomaticEventReport> {

    private static final long serialVersionUID = 1778685914L;

    public static final QLogAutomaticEventReport logAutomaticEventReport = new QLogAutomaticEventReport("logAutomaticEventReport");

    public final NumberPath<Integer> altitude = createNumber("altitude", Integer.class);

    public final StringPath analogInputValue = createString("analogInputValue");

    public final StringPath baseStationInfo = createString("baseStationInfo");

    public final NumberPath<Integer> direction = createNumber("direction", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endTrip = createDateTime("endTrip", java.time.LocalDateTime.class);

    public final NumberPath<Integer> eventCode = createNumber("eventCode", Integer.class);

    public final StringPath fuelPercentage = createString("fuelPercentage");

    public final StringPath geom = createString("geom");

    public final NumberPath<Integer> gsmSignalNumber = createNumber("gsmSignalNumber", Integer.class);

    public final NumberPath<Double> hdop = createNumber("hdop", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> imeiNumber = createNumber("imeiNumber", Long.class);

    public final StringPath ioPortStatus = createString("ioPortStatus");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Long> mileage = createNumber("mileage", Long.class);

    public final NumberPath<Integer> numberOfSatellites = createNumber("numberOfSatellites", Integer.class);

    public final StringPath positioningStatus = createString("positioningStatus");

    public final NumberPath<Long> raw = createNumber("raw", Long.class);

    public final NumberPath<Long> runtime = createNumber("runtime", Long.class);

    public final NumberPath<Integer> speed = createNumber("speed", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public QLogAutomaticEventReport(String variable) {
        super(LogAutomaticEventReport.class, forVariable(variable));
    }

    public QLogAutomaticEventReport(Path<? extends LogAutomaticEventReport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLogAutomaticEventReport(PathMetadata metadata) {
        super(LogAutomaticEventReport.class, metadata);
    }

}

