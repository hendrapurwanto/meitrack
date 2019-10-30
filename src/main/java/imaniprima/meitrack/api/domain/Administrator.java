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
@Table(name = "administrator")
@Setter
@Getter
@NoArgsConstructor
public class Administrator {
    @Id
    @Column

    private String id;
    private String passphrase;
    private Boolean isRoot;
    private String lastToken;
    private String lastIpAddress;
    private String lastUserAgent;
    private LocalDateTime lastLoginTime;
    private LocalDateTime lastExpireTime;
    private LocalDateTime lastLogoutTime;
    @Column(name="name")
    private String name;

}
