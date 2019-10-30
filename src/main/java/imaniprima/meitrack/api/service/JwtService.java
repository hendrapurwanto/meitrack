package imaniprima.meitrack.api.service;


import imaniprima.meitrack.api.domain.Users;
import imaniprima.meitrack.api.dto.UsersAuthDTO;
import imaniprima.meitrack.api.payload.JwtAuthenticationResponse;
import imaniprima.meitrack.api.repository.UsersRepository;
import imaniprima.meitrack.api.security.JwtAuthenticationFilter;
import imaniprima.meitrack.api.security.JwtTokenProvider;
import imaniprima.meitrack.api.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class JwtService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JwtTokenProvider tokenProvider;


    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;



    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsServ;

    public JwtAuthenticationResponse getValueFromToken (String token){
        String userId = jwtTokenUtil.getUsernameFromToken(token);

        final Users user = usersRepository.findMapById(Long.valueOf(userId));
        final UserDetails userDetails = userDetailsServ.loadUserByUsername(user.getUsername());
        final UsersAuthDTO usersAuthDTO = setUsersAuthDTO(user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse(token, userDetails, usersAuthDTO);
        return response;
    }

    public UsersAuthDTO setUsersAuthDTO(Users user){
        UsersAuthDTO usersAuthDTO = new UsersAuthDTO();
        usersAuthDTO.setId(user.getId());
        usersAuthDTO.setUsername(user.getUsername());
        usersAuthDTO.setEmail(user.getEmail());
        usersAuthDTO.setAddress(user.getAddress());
        usersAuthDTO.setPhone(user.getPhone());
        usersAuthDTO.setBranch(user.getBranch());
        return usersAuthDTO;
    }


}
