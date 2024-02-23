package fr.octorn.cinemacda4.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
    List<Ticket> findAllBySeanceId(Integer seanceId);
}
