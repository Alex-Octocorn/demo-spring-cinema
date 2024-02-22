package fr.octorn.cinemacda4.ticket.dto;

import fr.octorn.cinemacda4.film.dto.FilmMiniDto;
import fr.octorn.cinemacda4.seance.dto.SeanceReduit;
import lombok.Data;

@Data
public class TicketReduitDto {
    private Integer id;
    private SeanceReduit seance;
    private String nomClient;
    private int nombrePlaces;
}
