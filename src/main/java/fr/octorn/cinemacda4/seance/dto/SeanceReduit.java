package fr.octorn.cinemacda4.seance.dto;

import fr.octorn.cinemacda4.film.dto.FilmMiniDto;
import fr.octorn.cinemacda4.salle.Salle;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeanceReduit {
    private Integer id;
    private FilmMiniDto film;
    private Salle salle;
    private LocalDateTime date;
    private int placesDisponibles;
    private float prix;
}
