package fr.octorn.cinemacda4.ticket.mapper;

import fr.octorn.cinemacda4.ticket.Ticket;
import fr.octorn.cinemacda4.ticket.dto.TicketReduitDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    public TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    public TicketReduitDto toTicketReduitDto(Ticket ticket);
}
