package fr.octorn.cinemacda4.seance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Integer>{
    List<Seance> findAllByDateBetween(LocalDateTime debut, LocalDateTime fin);

    List<Seance> findAllByFilmIdAndDateAfter(Integer filmId, LocalDateTime date);

    List<Seance> findAllByFilmIdAndDateBetween(Integer id, LocalDateTime start, LocalDateTime end);

    List<Seance> findAllBySalleIdAndDateBetween(Integer salleId, LocalDateTime start, LocalDateTime end);
}
