package fr.octorn.cinemacda4.ticket;

import fr.octorn.cinemacda4.ticket.dto.TicketReduitDto;
import fr.octorn.cinemacda4.ticket.mapper.TicketMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {

    private final TicketService ticketService;

    private final TicketMapper ticketMapper;

    public TicketController(
            TicketService ticketService,
            TicketMapper ticketMapper
    ) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @GetMapping
    public List<Ticket> findAll() {
        return ticketService.findAll();
    }

    @GetMapping("{id}")
    public Ticket findById(@PathVariable Integer id) {
        return ticketService.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        ticketService.deleteById(id);
    }

    @PostMapping
    public TicketReduitDto save(@RequestBody Ticket ticket) {
        return ticketMapper.toTicketReduitDto(ticketService.save(ticket));
    }
}
