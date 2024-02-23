package fr.octorn.cinemacda4.seance;

import fr.octorn.cinemacda4.seance.dto.SeanceReduit;
import fr.octorn.cinemacda4.seance.mapper.SeanceMapper;
import org.springframework.http.HttpStatus;
import fr.octorn.cinemacda4.ticket.Ticket;
import fr.octorn.cinemacda4.ticket.TicketService;
import fr.octorn.cinemacda4.ticket.dto.TicketReduitDto;
import fr.octorn.cinemacda4.ticket.dto.TicketSansFilmNiSeanceDto;
import fr.octorn.cinemacda4.ticket.mapper.TicketMapper;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("seances")
public class SeanceController {

    private final SeanceService seanceService;

    private final TicketService ticketService;

    private final SeanceMapper seanceMapper;

    private final TicketMapper ticketMapper;

    public SeanceController(
            SeanceService seanceService,
            TicketService ticketService,
            SeanceMapper seanceMapper,
            TicketMapper ticketMapper
    ) {
        this.seanceService = seanceService;
        this.ticketService = ticketService;
        this.seanceMapper = seanceMapper;
        this.ticketMapper = ticketMapper;
    }

    @GetMapping
    public List<Seance> findAll() {
        return seanceService.findAll();
    }

    @GetMapping("{id}")
    public Seance findById(@PathVariable Integer id) {
        return seanceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeanceReduit save(@RequestBody Seance seance) {
        return seanceMapper.toSeanceReduit(seanceService.save(seance));
    }

    @PutMapping("{id}")
    public Seance update(@RequestBody Seance seance, @PathVariable Integer id) {
        seance.setId(id);
        return seanceService.update(seance, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        seanceService.deleteById(id);
    }

    @PostMapping("{id}/reserver")
    public TicketReduitDto reserver(@PathVariable Integer id, @RequestBody Ticket ticket) {
        ticket.setSeance(seanceService.findById(id));
        return ticketMapper.toTicketReduitDto(ticketService.save(ticket));
    }

    @GetMapping("{id}/tickets")
    public List<TicketSansFilmNiSeanceDto> findTicketsBySeanceId(@PathVariable Integer id) {
        return ticketMapper.toTicketsSansFilmDto(ticketService.findTicketsBySeanceId(id));
    }

    @GetMapping("disponible")
    public List<SeanceReduit> findSeancesByDate(@RequestParam String date) {
        return seanceMapper.toSeancesReduit(seanceService.findAllByDate(date));
    }
}
