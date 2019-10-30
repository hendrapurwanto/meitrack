package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRawMessage is a Querydsl query type for RawMessage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRawMessage extends EntityPathBase<RawMessage> {

    private static final long serialVersionUID = -2070071636L;

    public static final QRawMessage rawMessage = new QRawMessage("rawMessage");

    public final NumberPath<Long> cluster = createNumber("cluster", Long.class);

    public final StringPath error = createString("error");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public final StringPath type = createString("type");

    public final BooleanPath valid = createBoolean("valid");

    public QRawMessage(String variable) {
        super(RawMessage.class, forVariable(variable));
    }

    public QRawMessage(Path<? extends RawMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRawMessage(PathMetadata metadata) {
        super(RawMessage.class, metadata);
    }

}

