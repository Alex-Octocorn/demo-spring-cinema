package fr.octorn.cinemacda4.realisateur;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.octorn.cinemacda4.film.Film;
import fr.octorn.cinemacda4.film.FilmService;
import fr.octorn.cinemacda4.film.dto.FilmMiniDto;
import fr.octorn.cinemacda4.realisateur.dto.RealisateurAvecFilmsDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RealisateurService {
    private final RealisateurRepository realisateurRepository;
    private final ObjectMapper objectMapper;

    private final FilmService filmService;

    public RealisateurService(
            RealisateurRepository realisateurRepository,
            ObjectMapper objectMapper,
            FilmService filmService
    ) {
        this.realisateurRepository = realisateurRepository;
        this.objectMapper = objectMapper;
        this.filmService = filmService;
    }

    public List<Realisateur> findAll() {
        return realisateurRepository.findAll();
    }

    public Realisateur save(Realisateur realisateur) {
        return realisateurRepository.save(realisateur);
    }

    public Realisateur findById(int integer) {
        return realisateurRepository
                .findById(integer)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Realisateur not found"
                        )
                );
    }

    public void deleteById(Integer id) {
        this.findById(id);

        List<Film> filmsAvecCeRealisateur = filmService.findAllByRealisateurId(id);

        filmsAvecCeRealisateur.forEach(
                film -> {
                    film.setRealisateur(null);
                    filmService.save(film);
                }
        );

        realisateurRepository.deleteById(id);
    }

    public RealisateurAvecFilmsDto findRealisateurWithFilm(Integer id) {
        // On récupère le réalisateur demandé
        Realisateur realisateur = this.findById(id);
        // On récupère la liste des films de ce réal en faisant appel au serivce Films
        List<Film> filmsDuRealisateur = filmService.findAllByRealisateurId(id);

        // On créé une instance à partir de notre DTO
        RealisateurAvecFilmsDto realisateurAvecFilmsDto = new RealisateurAvecFilmsDto();

        // On récupère les valeurs du réalisateurs et on les affecte
        // à notre objet
        realisateurAvecFilmsDto.setId(realisateur.getId());
        realisateurAvecFilmsDto.setNom(realisateur.getNom());
        realisateurAvecFilmsDto.setPrenom(realisateur.getPrenom());

        realisateurAvecFilmsDto.setFilms(
                // On convertir la liste de film en notre DTO FilmMini
                // pour ne pas avoir d'erreur de type
                filmsDuRealisateur.stream().map(
                        film -> objectMapper.convertValue(film, FilmMiniDto.class)
                ).toList()
        );

        // Puis on retourne l'objet qu'on a fabriqué
        return realisateurAvecFilmsDto;
    }

    public List<Film> findFilmsByRealisateurId(Integer id) {
        return filmService.findAllByRealisateurId(id);
    }
}
