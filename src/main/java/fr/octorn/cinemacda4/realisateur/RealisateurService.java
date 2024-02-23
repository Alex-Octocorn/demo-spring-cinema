package fr.octorn.cinemacda4.realisateur;

import fr.octorn.cinemacda4.exceptions.NotFoundException;
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
    private final FilmService filmService;

    public RealisateurService(
            RealisateurRepository realisateurRepository,
            FilmService filmService
    ) {
        this.realisateurRepository = realisateurRepository;
        this.filmService = filmService;
    }

    public List<Realisateur> findAll() {
        return realisateurRepository.findAll();
    }

    public Realisateur save(Realisateur realisateur) {
        return realisateurRepository.save(realisateur);
    }

    public Realisateur findById(int id) {
        return realisateurRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Aucun réalisateur avec l'ID " + id
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
                filmsDuRealisateur.stream()
                        .map(
                                film -> {
                                    FilmMiniDto filmMiniDto = new FilmMiniDto();
                                    filmMiniDto.setId(film.getId());
                                    filmMiniDto.setTitre(film.getTitre());
                                    return filmMiniDto;
                                }
                        )
                        .toList()
        );

        // Puis on retourne l'objet qu'on a fabriqué
        return realisateurAvecFilmsDto;
    }

    public List<Film> findFilmsByRealisateurId(Integer id) {
        return filmService.findAllByRealisateurId(id);
    }
}
