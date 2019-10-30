package imaniprima.meitrack.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LogAutomaticEventReportDetailDTO {
    private Long id;
    private Long imeiNumber;
    private Integer eventCode;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
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
    private Long raw;
    private String fuelPercentage;
    private String geom;
    private String plate;
    private String ignition;
    private Integer status;
    private String addressLocation;
}
