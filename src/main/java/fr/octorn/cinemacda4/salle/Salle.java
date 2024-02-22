package fr.octorn.cinemacda4.salle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Salle {
    @Id
    @GeneratedValue
    private Integer id;

    private int numero;
    private int capacite;
}
