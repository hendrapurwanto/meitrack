package imaniprima.meitrack.api.controller;

import imaniprima.meitrack.api.domain.Users;
import imaniprima.meitrack.api.dto.UsersAuthDTO;
import imaniprima.meitrack.api.payload.JwtAuthenticationResponse;
import imaniprima.meitrack.api.payload.LoginRequest;
import imaniprima.meitrack.api.repository.UsersRepository;
import imaniprima.meitrack.api.security.JwtAuthenticationFilter;
import imaniprima.meitrack.api.security.JwtTokenProvider;
import imaniprima.meitrack.api.security.JwtTokenUtil;
import imaniprima.meitrack.api.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("auth")
@Api(tags = "Auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;


    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtService jwtService;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsServ;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateLogin (@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsServ.loadUserByUsername(loginRequest.getUsername());
        final Users user = usersRepository.findMapByUsername(userDetails.getUsername());
        final UsersAuthDTO usersAuthDTO = jwtService.setUsersAuthDTO(user);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userDetails, usersAuthDTO));
    }


    @GetMapping(path = "/value")
    public JwtAuthenticationResponse getItemToken (
        @RequestHeader("Authorization") String Authorization,
        HttpServletRequest request) {
        return jwtService.getValueFromToken(Authorization);
    }

    @GetMapping(path = "/logout")
    public String logout (
        @RequestHeader("Authorization") String Authorization,
        HttpServletRequest request) {

        Date now = new Date();
        now.setTime(0);
        Date expiryDate = new Date(now.getTime() - jwtExpirationInMs);
        System.err.println(expiryDate);
        return Jwts.builder()
                .setSubject(Long.toString(111))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();

//        return jwtService.getValueFromToken(Authorization);
    }

}