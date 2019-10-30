package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "latest_vehicle_info")
@Setter
@Getter
@NoArgsConstructor
public class LatestVehicleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicles_id_seq")
    @SequenceGenerator(name = "vehicles_id_seq", sequenceName = "vehicles_id_seq", allocationSize = 1)

    @Column
    private Long id;
    private String plate;
    private String branch;
    @Column(name="type")
    private String type;
    private String model;
    private Integer yearMade;
    private Boolean gps;
    private Long imei;
    private Double latitude;
    private Double longitude;
    @Column(name="timestamp")
    private LocalDateTime timestamp;
    private Integer speed;
    private Integer direction;
    private Integer altitude;
    private String geom;
    private String driver;
    private String phone;
    private String ignition;
    private Integer status;

    @Transient
    private LocalDateTime started;

    @Transient
    private LocalDateTime ended;
}
