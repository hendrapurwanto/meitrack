package imaniprima.meitrack.api.security;

import imaniprima.meitrack.api.domain.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JwtUserFactory {
    private JwtUserFactory(){ }

    public static JwtUser create(Users user){
        return new JwtUser(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            mapToGrantedAuthorities(user.getAutority())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(String authority) {
        return new ArrayList<>(Collections.singletonList( new SimpleGrantedAuthority(authority)));
    }

}
