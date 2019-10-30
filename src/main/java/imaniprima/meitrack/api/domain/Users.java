package imaniprima.meitrack.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)

    @Column
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String address;
    private String branch;
    private String email;

    public Users(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getAutority(){
        Long auth = Long.valueOf(0);
        return Authorities.getById(auth);
    }

}
