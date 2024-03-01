package fr.octorn.cinemacda4.security.mapper;

import fr.octorn.cinemacda4.realisateur.mapper.RealisateurMapper;
import fr.octorn.cinemacda4.security.dto.CinemaUserDto;
import fr.octorn.cinemacda4.security.entities.CinemaUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CinemaUserMapper {
    public CinemaUserMapper INSTANCE = Mappers.getMapper(CinemaUserMapper.class);

    CinemaUserDto toDto(CinemaUser entity);

    CinemaUser toEntity(CinemaUserDto dto);
}
