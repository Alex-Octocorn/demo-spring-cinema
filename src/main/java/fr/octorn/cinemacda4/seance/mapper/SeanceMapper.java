package fr.octorn.cinemacda4.seance.mapper;

import fr.octorn.cinemacda4.seance.Seance;
import fr.octorn.cinemacda4.seance.dto.SeanceReduit;
import fr.octorn.cinemacda4.seance.dto.SeanceSansFilm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeanceMapper {

    SeanceMapper INSTANCE = Mappers.getMapper(SeanceMapper.class);

    SeanceReduit toSeanceReduit(Seance seance);

    List<SeanceReduit> toSeancesReduit(List<Seance> seances);

    List<SeanceSansFilm> toSeancesSansFilm(List<Seance> seances);
}
