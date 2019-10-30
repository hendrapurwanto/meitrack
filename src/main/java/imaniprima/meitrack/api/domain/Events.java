package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "events")
@Setter
@Getter
@NoArgsConstructor
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_id_seq")
    @SequenceGenerator(name = "events_id_seq", sequenceName = "events_id_seq", allocationSize = 1)

    @Column
    private Long id;
    private String name;
    private String description;
    private Boolean liveRule;
    private Long eventCode;
    private String rules;

    @Transient
    private Long totalEvents;
}
