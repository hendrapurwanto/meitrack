package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "geofence_group")
@Setter
@Getter
@NoArgsConstructor
public class GeofenceGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geofence_group_id_seq")
    @SequenceGenerator(name = "geofence_group_id_seq", sequenceName = "geofence_group_id_seq", allocationSize = 1)

    @Column
    private Long id;
    private String name;
    private String description;
    private String color;

    @Transient
    private List<GeofenceArea> geofenceArea;





}
