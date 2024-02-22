package fr.octorn.cinemacda4.acteur.mapper;

import fr.octorn.cinemacda4.acteur.Acteur;
import fr.octorn.cinemacda4.acteur.dto.ActeurReduitDto;
import fr.octorn.cinemacda4.acteur.dto.ActeurSansFilmDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActeurMapper {
    public ActeurMapper INSTANCE = Mappers.getMapper(ActeurMapper.class);

    public ActeurReduitDto toActeurReduit(Acteur acteur);

    public List<ActeurSansFilmDto> toActeursSansFilm(List<Acteur> acteurs);
}
