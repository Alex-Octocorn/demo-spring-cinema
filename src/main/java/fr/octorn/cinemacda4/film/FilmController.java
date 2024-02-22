package fr.octorn.cinemacda4.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.octorn.cinemacda4.acteur.Acteur;
import fr.octorn.cinemacda4.acteur.dto.ActeurReduitDto;
import fr.octorn.cinemacda4.acteur.dto.ActeurSansFilmDto;
import fr.octorn.cinemacda4.acteur.mapper.ActeurMapper;
import fr.octorn.cinemacda4.film.dto.FilmCompletDto;
import fr.octorn.cinemacda4.film.dto.FilmReduitDto;
import fr.octorn.cinemacda4.film.exceptions.BadRequestException;
import fr.octorn.cinemacda4.film.mapper.FilmMapper;
import fr.octorn.cinemacda4.realisateur.Realisateur;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    private final ObjectMapper objectMapper;

    private final FilmMapper filmMapper;

    private final ActeurMapper acteurMapper;

    public FilmController(
            FilmService filmService,
            ObjectMapper objectMapper,
            FilmMapper filmMapper, ActeurMapper acteurMapper
    ) {
        this.filmService = filmService;
        this.objectMapper = objectMapper;
        this.filmMapper = filmMapper;
        this.acteurMapper = acteurMapper;
    }

    @GetMapping
    public List<FilmReduitDto> findAll() {
        return filmService.findAll().stream().map(
                film -> objectMapper.convertValue(film, FilmReduitDto.class)
        ).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film save(@RequestBody Film film) {

        return filmService.save(film);
    }

    @GetMapping("/{id}") // /films/1
    public FilmCompletDto findById(@PathVariable Integer id) {
        Film film = filmService.findById(id);

        return filmMapper.INSTANCE.toFilmComplet(film);
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

    @GetMapping("/{id}/realisateurs")
    public Realisateur findRealisateursByFilm(@PathVariable Integer id) {
        return filmService.findById(id).getRealisateur();
    }

    @PostMapping("/{id}/acteurs")
    public FilmCompletDto addActorToFilm(@PathVariable Integer id, @RequestBody Acteur acteur) {
        Film film = filmService.addActorToFilm(id, acteur);

        return filmMapper.INSTANCE.toFilmComplet(film);
    }
}
