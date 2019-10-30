package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventsLog is a Querydsl query type for EventsLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEventsLog extends EntityPathBase<EventsLog> {

    private static final long serialVersionUID = -1539243554L;

    public static final QEventsLog eventsLog = new QEventsLog("eventsLog");

    public final DatePath<java.time.LocalDate> ended = createDate("ended", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> endTimes = createTime("endTimes", java.time.LocalTime.class);

    public final NumberPath<Long> eventCode = createNumber("eventCode", Long.class);

    public final NumberPath<Long> eventId = createNumber("eventId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> logAutoEventId = createNumber("logAutoEventId", Long.class);

    public final NumberPath<Integer> outputParameter = createNumber("outputParameter", Integer.class);

    public final StringPath outputValue = createString("outputValue");

    public final DatePath<java.time.LocalDate> started = createDate("started", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> times = createTime("times", java.time.LocalTime.class);

    public final NumberPath<Long> vehicle = createNumber("vehicle", Long.class);

    public QEventsLog(String variable) {
        super(EventsLog.class, forVariable(variable));
    }

    public QEventsLog(Path<? extends EventsLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventsLog(PathMetadata metadata) {
        super(EventsLog.class, metadata);
    }

}

