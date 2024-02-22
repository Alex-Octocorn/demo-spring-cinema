package fr.octorn.cinemacda4.ticket.dto;

import lombok.Data;

@Data
public class TicketSansFilmNiSeanceDto {
    private Integer id;
    private String nomClient;
    private int nombrePlaces;
}
