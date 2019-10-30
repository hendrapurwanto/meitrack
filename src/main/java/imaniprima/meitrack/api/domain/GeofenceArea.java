package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "geofence_area")
@Setter
@Getter
@NoArgsConstructor
public class GeofenceArea {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geofence_area_id_seq")
    @SequenceGenerator(name = "geofence_area_id_seq", sequenceName = "geofence_area_id_seq", allocationSize = 1)

    @Column
    private Long id;
    private String name;
    private String description;
    private Long groupId;
    private String type;
    private String geojson;
    private String geom;

}
