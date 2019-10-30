package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "raw_message")
@Setter
@Getter
@NoArgsConstructor
public class RawMessage {
    @Id
    @Column
    private Long id;
    private String message;
    private String type;
    private Boolean valid;
    private String error;
    private Long cluster;

    @Column(name="timestamp")
    private LocalDateTime timestamp;
}
