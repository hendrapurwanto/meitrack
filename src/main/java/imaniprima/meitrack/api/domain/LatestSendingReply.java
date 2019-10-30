package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "latest_sending_reply")
@Setter
@Getter
@NoArgsConstructor
public class LatestSendingReply {
    @Id
    @Column

    private Long imeiNumber;
    @Column(name="type")
    private String type;
    @Column(name="content")
    private String content;
    @Column(name="timestamp")
    private LocalDateTime timestamp;
    @Column(name="log")
    private Long log;
}
