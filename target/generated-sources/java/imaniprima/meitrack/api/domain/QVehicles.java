package imaniprima.meitrack.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVehicles is a Querydsl query type for Vehicles
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVehicles extends EntityPathBase<Vehicles> {

    private static final long serialVersionUID = -253246988L;

    public static final QVehicles vehicles = new QVehicles("vehicles");

    public final StringPath branch = createString("branch");

    public final NumberPath<Float> cargoH = createNumber("cargoH", Float.class);

    public final NumberPath<Float> cargoL = createNumber("cargoL", Float.class);

    public final NumberPath<Float> cargoW = createNumber("cargoW", Float.class);

    public final BooleanPath ckbFleet = createBoolean("ckbFleet");

    public final StringPath driver = createString("driver");

    public final BooleanPath gps = createBoolean("gps");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> imei = createNumber("imei", Long.class);

    public final StringPath model = createString("model");

    public final StringPath operation = createString("operation");

    public final StringPath plate = createString("plate");

    public final NumberPath<Float> tonCan = createNumber("tonCan", Float.class);

    public final StringPath type = createString("type");

    public final NumberPath<Integer> yearMade = createNumber("yearMade", Integer.class);

    public QVehicles(String variable) {
        super(Vehicles.class, forVariable(variable));
    }

    public QVehicles(Path<? extends Vehicles> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVehicles(PathMetadata metadata) {
        super(Vehicles.class, metadata);
    }

}

