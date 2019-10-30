package imaniprima.meitrack.api.dto;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChangePasswordDTO {

    private String oldPassword;
    private String newPassword;
    private String message;
}
