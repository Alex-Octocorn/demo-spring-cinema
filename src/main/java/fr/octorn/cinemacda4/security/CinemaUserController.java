package fr.octorn.cinemacda4.security;

import fr.octorn.cinemacda4.security.dto.CinemaUserDto;
import fr.octorn.cinemacda4.security.entities.CinemaUser;
import fr.octorn.cinemacda4.security.mapper.CinemaUserMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CinemaUserController {

    private final CinemaUserService service;

    private final CinemaUserMapper mapper;

    @PostMapping("/register")
    public  CinemaUser save(@RequestBody CinemaUserDto user) {
        return service.save(
                mapper.toEntity(user)
        );
    }
}
