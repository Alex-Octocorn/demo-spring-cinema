package fr.octorn.cinemacda4.film.mapper;

import fr.octorn.cinemacda4.film.Film;
import fr.octorn.cinemacda4.film.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    public FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    public FilmCompletDto toFilmComplet(Film film);

    public FilmMiniDto toFilmMini(Film film);

    public FilmReduitDto toFilmReduit(Film film);

    public FilmSansActeurDto toFilmSansActeur(Film film);

    public FilmSansActeursNiRealisateurDto toFilmSansActeurNiRealisateur(Film film);
}
