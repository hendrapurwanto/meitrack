package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGeofenceArea is a Querydsl query type for GeofenceArea
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGeofenceArea extends EntityPathBase<GeofenceArea> {

    private static final long serialVersionUID = -2049385254L;

    public static final QGeofenceArea geofenceArea = new QGeofenceArea("geofenceArea");

    public final StringPath description = createString("description");

    public final StringPath geojson = createString("geojson");

    public final StringPath geom = createString("geom");

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath type = createString("type");

    public QGeofenceArea(String variable) {
        super(GeofenceArea.class, forVariable(variable));
    }

    public QGeofenceArea(Path<? extends GeofenceArea> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGeofenceArea(PathMetadata metadata) {
        super(GeofenceArea.class, metadata);
    }

}

