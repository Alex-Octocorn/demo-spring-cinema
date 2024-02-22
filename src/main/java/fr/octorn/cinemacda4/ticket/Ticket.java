package fr.octorn.cinemacda4.ticket;

import fr.octorn.cinemacda4.seance.Seance;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Seance seance;

    private String nomClient;

    private int nombrePlaces;
}
