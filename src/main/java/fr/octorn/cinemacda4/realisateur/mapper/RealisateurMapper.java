package fr.octorn.cinemacda4.realisateur.mapper;

import fr.octorn.cinemacda4.realisateur.Realisateur;
import fr.octorn.cinemacda4.realisateur.dto.RealisateurAvecFilmsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RealisateurMapper {
    public RealisateurMapper INSTANCE = Mappers.getMapper(RealisateurMapper.class);

    public RealisateurAvecFilmsDto toRealisateurAvecFilms(Realisateur realisateur);
}
