package fr.octorn.cinemacda4.seance;

import fr.octorn.cinemacda4.film.FilmService;
import fr.octorn.cinemacda4.salle.Salle;
import fr.octorn.cinemacda4.salle.SalleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeanceService {

    private final SeanceRepository seanceRepository;

    private final SalleService salleService;

    private final FilmService filmService;

    public SeanceService(
            SeanceRepository seanceRepository,
            SalleService salleService,
            FilmService filmService
    ) {
        this.seanceRepository = seanceRepository;
        this.salleService = salleService;
        this.filmService = filmService;
    }

    public Seance save(Seance seance) {
        seance.setSalle(salleService.findById(seance.getSalle().getId()));
        seance.setFilm(filmService.findById(seance.getFilm().getId()));
        seance.setPlacesDisponibles(seance.getSalle().getCapacite());

        return seanceRepository.save(seance);
    }

    public Seance findById(Integer id) {
        return seanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Seance not found"));
    }

    public void deleteById(Integer id) {
        this.findById(id);
        seanceRepository.deleteById(id);
    }

    public Seance update(Seance seance, Integer id) {
        seance.setId(id);
        this.findById(id);
        return seanceRepository.save(seance);
    }

    public List<Seance> findAll() {
        return seanceRepository.findAll();
    }
}