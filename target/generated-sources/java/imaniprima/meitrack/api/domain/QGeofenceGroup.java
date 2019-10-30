package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGeofenceGroup is a Querydsl query type for GeofenceGroup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGeofenceGroup extends EntityPathBase<GeofenceGroup> {

    private static final long serialVersionUID = 899118034L;

    public static final QGeofenceGroup geofenceGroup = new QGeofenceGroup("geofenceGroup");

    public final StringPath color = createString("color");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QGeofenceGroup(String variable) {
        super(GeofenceGroup.class, forVariable(variable));
    }

    public QGeofenceGroup(Path<? extends GeofenceGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGeofenceGroup(PathMetadata metadata) {
        super(GeofenceGroup.class, metadata);
    }

}

