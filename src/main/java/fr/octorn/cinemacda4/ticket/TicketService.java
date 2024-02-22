package fr.octorn.cinemacda4.ticket;

import fr.octorn.cinemacda4.seance.Seance;
import fr.octorn.cinemacda4.seance.SeanceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final SeanceService seanceService;

    public TicketService(
            TicketRepository ticketRepository,
            SeanceService seanceService
    ) {
        this.ticketRepository = ticketRepository;
        this.seanceService = seanceService;
    }

    public Ticket save(Ticket ticket) {

        Seance seance = seanceService.findById(ticket.getSeance().getId());
        int placesDisponibles = seance.getPlacesDisponibles();

        verifyTicket(ticket, placesDisponibles);

        seance.setPlacesDisponibles(placesDisponibles - ticket.getNombrePlaces());

        seanceService.update(seance, seance.getId());
        ticket.setSeance(seance);
        return ticketRepository.save(ticket);
    }

    private static void verifyTicket(Ticket ticket, int placesDisponibles) {
        List<String> errors = new ArrayList<>();
        if (ticket.getNombrePlaces() < 1) {
            errors.add("Le nombre de places doit être supérieur à 0");
        }

        if (ticket.getNomClient().isEmpty()) {
            errors.add("Le nom du client ne peut pas être vide");
        }

        if (ticket.getNombrePlaces() > placesDisponibles) {
            errors.add("Il n'y a pas assez de places disponibles pour cette séance");
        }

        if (!errors.isEmpty()) {
            throw new RuntimeException(errors.toString());
        }
    }

    public Ticket findById(Integer id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public void deleteById(Integer id) {
        this.findById(id);
        ticketRepository.deleteById(id);
    }

    public Ticket update(Ticket ticket, Integer id) {
        ticket.setId(id);
        this.findById(id);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
