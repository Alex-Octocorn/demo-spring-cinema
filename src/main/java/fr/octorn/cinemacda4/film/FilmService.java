package fr.octorn.cinemacda4.film;

import fr.octorn.cinemacda4.acteur.Acteur;
import fr.octorn.cinemacda4.acteur.ActeurService;
import fr.octorn.cinemacda4.exceptions.BadRequestException;
import fr.octorn.cinemacda4.exceptions.NotFoundException;
import fr.octorn.cinemacda4.seance.Seance;
import fr.octorn.cinemacda4.seance.SeanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    private final ActeurService acteurService;

    private final SeanceRepository seanceRepository;

    public FilmService(
            FilmRepository filmRepository,
            ActeurService acteurService,
            SeanceRepository seanceRepository
    ) {
        this.filmRepository = filmRepository;
        this.acteurService = acteurService;
        this.seanceRepository = seanceRepository;
    }

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Film save(Film film) throws BadRequestException {
        verifyFilm(film);

        return filmRepository.save(film);
    }

    private static void verifyFilm(Film film) {
        List<String> erreurs = new ArrayList<>();

        if (film.getTitre() == null) {
            erreurs.add("Le titre est obligatoire");
        }

        if (film.getDateSortie() == null) {
            erreurs.add("La date de sortie est obligatoire");
        }

        if (film.getRealisateur() == null) {
            erreurs.add("Le réalisateur est obligatoire");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }
    }

    public Film findById(Integer id) {
        return filmRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Aucun film avec l'ID " + id)
                );
    }

    public void deleteById(Integer id) {
        Film film = this.findById(id);
        filmRepository.delete(film);
    }

    public Film update(Film film, Integer id) {
        if (!filmRepository.existsById(id)) {
            throw new NotFoundException("Aucun film avec l'ID " + id);
        }
        film.setId(id);
        return filmRepository.save(film);
    }

    public Film findByTitre(String titre) {
        return filmRepository.findByTitre(titre)
                .orElseThrow(
                        () -> new NotFoundException("Aucun film avec le titre " + titre)
                );
    }

    public List<Film> findAllByRealisateurId(Integer id) {
        return filmRepository.findAllByRealisateurId(id)
                .orElseThrow(() -> new NotFoundException("Aucun film avec le réalisateur ID " + id));
    }

    public List<Acteur> findActeursByFilm(Integer id) {
        Film film = this.findById(id);
        return film.getActeurs();
    }


    public Film addActorToFilm(Integer id, Acteur acteur) {

        Film film = this.findById(id);
        acteur = acteurService.findById(acteur.getId());

        film.getActeurs().add(acteur);

        return this.save(film);
    }

    public List<Seance> findSeancesByFilm(Integer id) {
        return seanceRepository.findAllByFilmIdAndDateAfter(id, LocalDateTime.now());
    }

    public List<Seance> findSeancesByFilmAndDate(Integer id, String date) {
        LocalDateTime dateRecherche = LocalDateTime.of(LocalDate.parse(date), LocalDateTime.MIN.toLocalTime());
        LocalDateTime dateRechercheMax = LocalDateTime.of(LocalDate.parse(date), LocalDateTime.MAX.toLocalTime());
        return seanceRepository.findAllByFilmIdAndDateBetween(id, dateRecherche, dateRechercheMax);
    }
}
