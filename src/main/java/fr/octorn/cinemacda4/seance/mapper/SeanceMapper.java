package fr.octorn.cinemacda4.seance.mapper;

import fr.octorn.cinemacda4.seance.Seance;
import fr.octorn.cinemacda4.seance.dto.SeanceReduit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SeanceMapper {

    public SeanceMapper INSTANCE = Mappers.getMapper(SeanceMapper.class);

    public SeanceReduit toSeanceReduit(Seance seance);
}