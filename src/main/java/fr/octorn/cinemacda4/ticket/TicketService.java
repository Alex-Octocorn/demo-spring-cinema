package fr.octorn.cinemacda4.ticket;

import fr.octorn.cinemacda4.seance.Seance;
import fr.octorn.cinemacda4.seance.SeanceService;
import org.springframework.stereotype.Service;

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
        seance.setPlacesDisponibles(seance.getPlacesDisponibles() - ticket.getNombrePlaces());
        seanceService.update(seance, seance.getId());
        ticket.setSeance(seance);
        return ticketRepository.save(ticket);
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
