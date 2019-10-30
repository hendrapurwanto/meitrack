package imaniprima.meitrack.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UsersAuthDTO {
    private Long id;
    private String username;
    private String phone;
    private String address;
    private String branch;
    private String email;
}
