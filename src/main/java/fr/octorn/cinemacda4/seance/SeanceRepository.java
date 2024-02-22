package fr.octorn.cinemacda4.seance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Integer>{
    List<Seance> findAllByDateBetween(LocalDateTime debut, LocalDateTime fin);

    List<Seance> findAllByFilmId(Integer filmId);
}
