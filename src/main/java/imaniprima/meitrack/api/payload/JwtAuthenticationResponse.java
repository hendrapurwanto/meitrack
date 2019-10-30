package imaniprima.meitrack.api.payload;

import imaniprima.meitrack.api.dto.UsersAuthDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class JwtAuthenticationResponse {
    private final String accessToken;
    private String tokenType = "Bearer";
    private UserDetails user;
    private UsersAuthDTO userInfo;
    private List access;

    public JwtAuthenticationResponse(
            String accessToken,
            UserDetails user,
            UsersAuthDTO usersAuthDTO

    ){
        this.accessToken  = accessToken;
        this.user = user;
        this.userInfo = usersAuthDTO;
    }

    public String getAccessToken(){
        return accessToken;
    }

//    public void setAccessToken(String accessToken){
//        this.accessToken = accessToken;
//    }

    public String getTokenType(){ return tokenType; }

    public UserDetails getUser() { return this.user; }

    public  UsersAuthDTO getUserInfo(){
        return this.userInfo;
    }
    public void setTokenType(String tokenType){
        this.tokenType = tokenType;
    }
}
