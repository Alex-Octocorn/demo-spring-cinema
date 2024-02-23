package fr.octorn.cinemacda4.realisateur;

import fr.octorn.cinemacda4.film.Film;
import fr.octorn.cinemacda4.film.dto.FilmSansActeursNiRealisateurDto;
import fr.octorn.cinemacda4.film.mapper.FilmMapper;
import fr.octorn.cinemacda4.realisateur.dto.RealisateurAvecFilmsDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realisateurs")
public class RealisateurController {
    private final RealisateurService realisateurService;

    private final FilmMapper filmMapper;


    public RealisateurController(
            RealisateurService realisateurService,
            FilmMapper filmMapper
    ) {
        this.realisateurService = realisateurService;
        this.filmMapper = filmMapper;
    }

    @GetMapping
    public List<Realisateur> findAll() {
        return realisateurService.findAll();
    }

    @GetMapping("/{id}")
    public RealisateurAvecFilmsDto findById(@PathVariable int id) {
        return realisateurService.findRealisateurWithFilm(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        realisateurService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Realisateur save(@RequestBody Realisateur realisateur) {
        return realisateurService.save(realisateur);
    }

    @PutMapping
    public Realisateur update(@RequestBody Realisateur realisateur) {
        return realisateurService.save(realisateur);
    }

    @GetMapping("/{id}/films")
    public List<FilmSansActeursNiRealisateurDto> findFilmsByRealisateurId(@PathVariable Integer id) {
        List<Film> filmsDuRealisateur = realisateurService.findFilmsByRealisateurId(id);

        return filmMapper.toFilmsSansActeurNiRealisateur(filmsDuRealisateur);


    }

}
