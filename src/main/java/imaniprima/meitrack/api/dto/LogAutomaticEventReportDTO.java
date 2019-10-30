package imaniprima.meitrack.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LogAutomaticEventReportDTO {
    private Integer id;
    private LocalDate logTanggal;
    private LocalTime start;
    private LocalTime end;
    private Long imeiNumber;
    private String plate;
    private Long mileage;

}
