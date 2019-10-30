package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@Setter
@Getter
@NoArgsConstructor
public class Vehicles {

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
    private String operation;
    private Boolean ckbFleet;
    private Float tonCan;
    @Column(name="cargo_w")
    private Float cargoW;
    @Column(name="cargo_h")
    private Float cargoH;
    @Column(name="cargo_L")
    private Float cargoL;
    private Boolean gps;
    private Long imei;
    private String driver;

    @Transient
    private LatestAutomaticEventReport dtlLatestEvent;



}
