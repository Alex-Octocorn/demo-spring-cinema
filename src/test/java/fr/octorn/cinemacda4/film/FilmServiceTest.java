package fr.octorn.cinemacda4.film;

import static org.junit.jupiter.api.Assertions.*;

import fr.octorn.cinemacda4.acteur.Acteur;
import fr.octorn.cinemacda4.acteur.ActeurService;
import fr.octorn.cinemacda4.exceptions.BadRequestException;
import fr.octorn.cinemacda4.exceptions.NotFoundException;
import fr.octorn.cinemacda4.realisateur.Realisateur;
import fr.octorn.cinemacda4.seance.SeanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FilmService.class)
class FilmServiceTest {

    private FilmService filmService;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private ActeurService acteurService;

    @MockBean
    private SeanceRepository seanceRepository;

    private Film filmMock;

    private Acteur acteurMock;

    private static Realisateur realisateurMock;

    @BeforeEach
    void setUp() {
        filmService = new FilmService(filmRepository, acteurService, seanceRepository);

        realisateurMock = Realisateur.builder()
                .id(1)
                .nom("test")
                .prenom("test")
                .build();

        acteurMock = Acteur.builder()
                .id(1)
                .nom("test")
                .prenom("test")
                .build();

        filmMock = Film.builder()
                .id(1)
                .titre("test")
                .dateSortie(LocalDate.parse("2021-10-10"))
                .realisateur(realisateurMock)
                .acteurs(new ArrayList<>(List.of(acteurMock)))
                .build();

        acteurMock.setFilms(new ArrayList<>(List.of(filmMock)));
    }

    @Test
    @DisplayName("Sauvegarder un film")
    void testSave() {
        // Act
        when(filmRepository.save(filmMock)).then(
                invocation -> {
                    Film film = invocation.getArgument(0);
                    film.setId(1);
                    return film;
                }
        );
        Film savedFilm = filmService.save(filmMock);

        // Assert
        verify(filmRepository).save(filmMock);
        assertEquals(1, savedFilm.getId());
    }

    @ParameterizedTest
    @DisplayName("Ne pas sauvegarder un film invalide")
    @MethodSource("provideInvalidFilms")
    void shouldNotSaveInvalidFilm(Film film) {

        // Act
        assertThrows(BadRequestException.class, () -> filmService.save(film));
    }

    private static Stream<Arguments> provideInvalidFilms() {
        return Stream.of(
                Arguments.of(new Film()),
                Arguments.of(Film.builder().titre("test").build()),
                Arguments.of(Film.builder().titre("test").dateSortie(LocalDate.parse("2021-10-10")).build())
        );
    }

    @Test
    @DisplayName("Récupérer un film par son id")
    void testFindById() {
        // Arrange
        when(filmRepository.findById(1)).thenReturn(java.util.Optional.of(filmMock));

        // Act
        Film film = filmService.findById(1);

        // Assert
        verify(filmRepository).findById(1);
        assertEquals(filmMock.getActeurs(), film.getActeurs());
        assertEquals(filmMock.getDateSortie(), film.getDateSortie());
        assertEquals(filmMock.getRealisateur(), film.getRealisateur());
        assertEquals(filmMock.getTitre(), film.getTitre());
        assertEquals(filmMock.getSynopsis(), film.getSynopsis());
        assertEquals(filmMock.getDuree(), film.getDuree());

    }

    @Test
    @DisplayName("Supprimer un film")
    void testDelete() {
        when(filmRepository.findById(1)).thenReturn(java.util.Optional.of(filmMock));
        // Act
        filmService.deleteById(1);

        // Assert
        verify(filmRepository).delete(filmMock);
    }

    @Test
    @DisplayName("Mettre à jour un film")
    void testUpdate() {
        // Act
        when(filmRepository.save(filmMock)).thenReturn(filmMock);
        when(filmRepository.existsById(filmMock.getId())).thenReturn(true);
        when(filmRepository.findById(filmMock.getId())).thenReturn(java.util.Optional.of(filmMock));
        Film updatedFilm = filmService.update(filmMock, filmMock.getId());

        // Assert
        verify(filmRepository).save(filmMock);
        assertEquals(filmMock, updatedFilm);
    }

    @Test
    @DisplayName("Mettre à jour un film inexistant")
    void shouldThrowExceptionWhenFilmNotFoundOnUpdate() {
        // Arrange
        when(filmRepository.existsById(filmMock.getId())).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> filmService.update(filmMock, filmMock.getId()));
    }

    @Test
    @DisplayName("Récupérer tous les films")
    void testFindAll() {
        // Arrange
        when(filmRepository.findAll()).thenReturn(List.of(filmMock));

        // Act
        List<Film> films = filmService.findAll();

        // Assert
        verify(filmRepository).findAll();
        assertEquals(List.of(filmMock), films);
    }

    @Test
    @DisplayName("Récupérer un film par son id inexistant")
    void shouldThrowExceptionWhenFilmNotFound() {
        // Arrange
        when(filmRepository.findById(1)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> filmService.findById(1));
    }

    @Test
    @DisplayName("Ajouter un acteur à un film")
    void testAddActor() {
        // Arrange
        when(filmRepository.findById(1)).thenReturn(java.util.Optional.of(filmMock));
        when(acteurService.findById(1)).thenReturn(acteurMock);

        // Act
        filmService.addActorToFilm(1, acteurMock);

        // Assert
        verify(filmRepository).findById(1);
        verify(acteurService).findById(1);
        assertTrue(filmMock.getActeurs().contains(acteurMock));
        assertTrue(acteurMock.getFilms().contains(filmMock));
    }

    @Test
    @DisplayName("Récupérer les acteurs d'un film")
    void testFindActeursByFilm() {
        // Arrange
        when(filmRepository.findById(1)).thenReturn(java.util.Optional.of(filmMock));

        // Act
        List<Acteur> acteurs = filmService.findActeursByFilm(1);

        // Assert
        verify(filmRepository).findById(1);
        assertEquals(filmMock.getActeurs(), acteurs);
    }

    @Test
    @DisplayName("Récupérer un film par son titre")
    void testFindByTitre() {
        // Arrange
        when(filmRepository.findByTitre("test")).thenReturn(java.util.Optional.of(filmMock));

        // Act
        Film film = filmService.findByTitre("test");

        // Assert
        verify(filmRepository).findByTitre("test");
        assertEquals(filmMock, film);
    }

    @Test
    @DisplayName("Récupérer un film par son titre inexistant")
    void shouldThrowExceptionWhenFilmByTitreNotFound() {
        // Arrange
        when(filmRepository.findByTitre("test")).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> filmService.findByTitre("test"));
    }

    @Test
    @DisplayName("Récupérer tous les films d'un réalisateur")
    void testFindAllByRealisateurId() {
        // Arrange
        when(filmRepository.findAllByRealisateurId(1)).thenReturn(java.util.Optional.of(List.of(filmMock)));

        // Act
        List<Film> films = filmService.findAllByRealisateurId(1);

        // Assert
        verify(filmRepository).findAllByRealisateurId(1);
        assertEquals(List.of(filmMock), films);
    }

}