package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdministrator is a Querydsl query type for Administrator
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdministrator extends EntityPathBase<Administrator> {

    private static final long serialVersionUID = -482653248L;

    public static final QAdministrator administrator = new QAdministrator("administrator");

    public final StringPath id = createString("id");

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final DateTimePath<java.time.LocalDateTime> lastExpireTime = createDateTime("lastExpireTime", java.time.LocalDateTime.class);

    public final StringPath lastIpAddress = createString("lastIpAddress");

    public final DateTimePath<java.time.LocalDateTime> lastLoginTime = createDateTime("lastLoginTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastLogoutTime = createDateTime("lastLogoutTime", java.time.LocalDateTime.class);

    public final StringPath lastToken = createString("lastToken");

    public final StringPath lastUserAgent = createString("lastUserAgent");

    public final StringPath name = createString("name");

    public final StringPath passphrase = createString("passphrase");

    public QAdministrator(String variable) {
        super(Administrator.class, forVariable(variable));
    }

    public QAdministrator(Path<? extends Administrator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdministrator(PathMetadata metadata) {
        super(Administrator.class, metadata);
    }

}

