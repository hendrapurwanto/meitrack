package imaniprima.meitrack.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LogAutomaticEventReportTemporaryDTO {
    private Long id;
    private Double longitude;
    private Double latitude;

}
