package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "latest_automatic_event_report")
@Setter
@Getter
@NoArgsConstructor
public class LatestAutomaticEventReport {
    @Id
    @Column
    private Long imeiNumber;
    private Long eventCode;
    private Double latitude;
    private Double longitude;
    private String positioningStatus;
    private Integer numberOfSatellites;
    private Integer gsmSignalNumber;
    private Integer speed;
    private Integer direction;
    private Double hdop;
    private Integer altitude;
    private Long mileage;
    private Long runtime;
    private String baseStationInfo;
    private String ioPortStatus;
    private String analogInputValue;
    private String geom;
    private String fuelPercentage;
    private String ignition;
    private Integer status;
    private LocalDateTime endTrip;

    @Column(name="timestamp")
    private LocalDateTime timestamp;

    @Transient
    private String eventName;

    @Transient
    private String eventDescription;


}
