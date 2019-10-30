package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_automatic_event_report")
@Setter
@Getter
@NoArgsConstructor
public class LogAutomaticEventReport {
    @Id
    @Column

    private Long id;
    private Long imeiNumber;
    private Integer eventCode;
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
    private String fuelPercentage;
    private String geom;
    private LocalDateTime endTrip;

    @Column(name="raw")
    private Long raw;

    @Column(name="timestamp")
    private LocalDateTime timestamp;
}
