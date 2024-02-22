package fr.octorn.cinemacda4.seance;

import fr.octorn.cinemacda4.film.Film;
import fr.octorn.cinemacda4.salle.Salle;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Seance {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Film film;

    @ManyToOne
    private Salle salle;

    private LocalDateTime date;

    private int placesDisponibles;

    private float prix;
}
