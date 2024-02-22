package fr.octorn.cinemacda4.ticket;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket findById(Integer id) {
        return ticketRepository.findById(id).orElseThrow( () -> new RuntimeException("Ticket not found"));
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
