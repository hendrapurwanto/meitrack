package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvents is a Querydsl query type for Events
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEvents extends EntityPathBase<Events> {

    private static final long serialVersionUID = -1012124794L;

    public static final QEvents events = new QEvents("events");

    public final StringPath description = createString("description");

    public final NumberPath<Long> eventCode = createNumber("eventCode", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath liveRule = createBoolean("liveRule");

    public final StringPath name = createString("name");

    public final StringPath rules = createString("rules");

    public QEvents(String variable) {
        super(Events.class, forVariable(variable));
    }

    public QEvents(Path<? extends Events> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvents(PathMetadata metadata) {
        super(Events.class, metadata);
    }

}

