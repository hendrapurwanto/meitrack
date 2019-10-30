package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLatestSendingReply is a Querydsl query type for LatestSendingReply
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLatestSendingReply extends EntityPathBase<LatestSendingReply> {

    private static final long serialVersionUID = -1678649404L;

    public static final QLatestSendingReply latestSendingReply = new QLatestSendingReply("latestSendingReply");

    public final StringPath content = createString("content");

    public final NumberPath<Long> imeiNumber = createNumber("imeiNumber", Long.class);

    public final NumberPath<Long> log = createNumber("log", Long.class);

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public final StringPath type = createString("type");

    public QLatestSendingReply(String variable) {
        super(LatestSendingReply.class, forVariable(variable));
    }

    public QLatestSendingReply(Path<? extends LatestSendingReply> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLatestSendingReply(PathMetadata metadata) {
        super(LatestSendingReply.class, metadata);
    }

}

