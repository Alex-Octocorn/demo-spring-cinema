package fr.octorn.cinemacda4.film;

import fr.octorn.cinemacda4.acteur.Acteur;
import fr.octorn.cinemacda4.acteur.dto.ActeurSansFilmDto;
import fr.octorn.cinemacda4.acteur.mapper.ActeurMapper;
import fr.octorn.cinemacda4.film.dto.FilmCompletDto;
import fr.octorn.cinemacda4.film.dto.FilmReduitDto;
import fr.octorn.cinemacda4.film.mapper.FilmMapper;
import fr.octorn.cinemacda4.realisateur.Realisateur;
import fr.octorn.cinemacda4.seance.Seance;
import fr.octorn.cinemacda4.seance.dto.SeanceSansFilm;
import fr.octorn.cinemacda4.seance.mapper.SeanceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    private final FilmMapper filmMapper;

    private final ActeurMapper acteurMapper;
    private final SeanceMapper seanceMapper;

    public FilmController(
            FilmService filmService,
            FilmMapper filmMapper,
            ActeurMapper acteurMapper,
            SeanceMapper seanceMapper
    ) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
        this.acteurMapper = acteurMapper;
        this.seanceMapper = seanceMapper;
    }

    @GetMapping
    public List<FilmReduitDto> findAll() {
        return filmMapper.toFilmsReduits(filmService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film save(@RequestBody Film film) {

        return filmService.save(film);
    }

    @GetMapping("/{id}") // /films/1
    public FilmCompletDto findById(@PathVariable Integer id) {
        Film film = filmService.findById(id);

        return filmMapper.toFilmComplet(film);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        filmService.deleteById(id);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping("/search") // /film/search?titre=toto
    public Film findByTitre(@RequestParam String titre) {
        return filmService.findByTitre(titre);
    }

    @GetMapping("/{id}/acteurs")
    public List<ActeurSansFilmDto> findActeursByFilm(@PathVariable Integer id) {
        List<Acteur> acteurs = filmService.findActeursByFilm(id);
        return acteurMapper.toActeursSansFilm(acteurs);
    }

    @GetMapping("/{id}/realisateur")
    public Realisateur findRealisateursByFilm(@PathVariable Integer id) {
        return filmService.findById(id).getRealisateur();
    }

    @PostMapping("/{id}/acteurs")
    public FilmCompletDto addActorToFilm(@PathVariable Integer id, @RequestBody Acteur acteur) {
        Film film = filmService.addActorToFilm(id, acteur);

        return filmMapper.toFilmComplet(film);
    }

    @GetMapping("/{id}/seances")
    public List<SeanceSansFilm> findSeancesByFilm(@PathVariable Integer id, @RequestParam(required = false) String date) {
        if (date != null) {
            return seanceMapper.toSeancesSansFilm(filmService.findSeancesByFilmAndDate(id, date));
        }
        return seanceMapper.toSeancesSansFilm(filmService.findSeancesByFilm(id));
    }
}
