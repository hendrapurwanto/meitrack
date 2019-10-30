package imaniprima.meitrack.api.security;

import imaniprima.meitrack.api.domain.Users;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Users users = userRepository.findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found with username : " + username)
        );

        return JwtUserFactory.create(users);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Users users = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return JwtUserFactory.create(users);
    }
}