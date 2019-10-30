package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "events_log")
@Setter
@Getter
@NoArgsConstructor
public class EventsLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_log_id_seq")
    @SequenceGenerator(name = "events_log_id_seq", sequenceName = "events_log_id_seq", allocationSize = 1)

    @Column
    private Long id;
    private LocalDate started;
    private LocalTime times;
    private Long vehicle;
    private Long eventId;
    private Long eventCode;
    private LocalDate ended;
    private LocalTime endTimes;
    private Long logAutoEventId;
    private Integer outputParameter;
    private String outputValue;

    @Transient
    private String vehicleName;

    @Transient
    private String vehiclePlate;

    @Transient
    private String vehicleBranch;

    @Transient
    private String vehicleType;

    @Transient
    private String driverName;

    @Transient
    private String eventName;

    @Transient
    private String duration;

    @Transient
    private String outputValueText;

}
