package fr.octorn.cinemacda4.security;

import fr.octorn.cinemacda4.security.entities.CinemaUser;
import fr.octorn.cinemacda4.security.entities.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CinemaUserService implements UserDetailsService {

    private final CinemaUserRepository cinemaUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CinemaUser save(CinemaUser user) {

        if (cinemaUserRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IL EXISTE DEJA LOL XD PTDR");
        }

        Role role = new Role();
        role.setAuthority("USER");

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRole(role);

        return cinemaUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CinemaUser user = cinemaUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("T KI LOL"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getAuthority())
                .build();
    }
}
