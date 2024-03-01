package fr.octorn.cinemacda4.security;

import fr.octorn.cinemacda4.security.entities.CinemaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CinemaUserRepository extends JpaRepository<CinemaUser, Integer> {
    Optional<CinemaUser> findByUsername(String username);
}
