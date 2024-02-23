package fr.octorn.cinemacda4.seance;

import fr.octorn.cinemacda4.exceptions.NotFoundException;
import fr.octorn.cinemacda4.film.FilmService;
import fr.octorn.cinemacda4.exceptions.BadRequestException;
import fr.octorn.cinemacda4.salle.SalleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

        verifySeance(seance);

        seance.setSalle(salleService.findById(seance.getSalle().getId()));
        seance.setFilm(filmService.findById(seance.getFilm().getId()));


        seance.setPlacesDisponibles(seance.getSalle().getCapacite());

        return seanceRepository.save(seance);
    }

    private static void verifySeance(Seance seance) {
        List<String> errors = new ArrayList<>();

        if (seance.getDate().isBefore(LocalDateTime.now())) {
            errors.add("La date de la séance ne peut pas être dans le passé");
        }

        if (seance.getPrix() <= 0) {
            errors.add("Le prix ne peut pas être négatif");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }

    public Seance findById(Integer id) {
        return seanceRepository.findById(id).orElseThrow(() -> new NotFoundException("Aucune séance avec l'ID " + id));
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

    public List<Seance> findAllByDate(String date) {
        LocalDateTime dateRecherche = LocalDateTime.of(LocalDate.parse(date), LocalDateTime.MIN.toLocalTime());
        LocalDateTime dateRechercheMax = LocalDateTime.of(LocalDate.parse(date), LocalDateTime.MAX.toLocalTime());
        return seanceRepository.findAllByDateBetween(dateRecherche, dateRechercheMax);
    }

    public List<Seance> findSeancesByFilmId(Integer id) {
        return seanceRepository.findAllByFilmIdAndDateAfter(id, LocalDateTime.now());
    }
}
