package fr.octorn.cinemacda4.seance.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeanceSansFilm {
    private Integer id;
    private float prix;
    private LocalDateTime date;
    private int placesDisponibles;
}
