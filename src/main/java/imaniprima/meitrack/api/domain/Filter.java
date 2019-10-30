package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "filter")
@Setter
@Getter
@NoArgsConstructor
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_id_seq")
    @SequenceGenerator(name = "filter_id_seq", sequenceName = "filter_id_seq", allocationSize = 1)

    @Column
    private Long id;
    private String code;
    private String description;
}
