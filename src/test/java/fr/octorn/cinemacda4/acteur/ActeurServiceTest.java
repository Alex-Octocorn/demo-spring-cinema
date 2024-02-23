package fr.octorn.cinemacda4.acteur;

import fr.octorn.cinemacda4.exceptions.BadRequestException;
import fr.octorn.cinemacda4.exceptions.NotFoundException;
import fr.octorn.cinemacda4.film.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ActeurService.class)
class ActeurServiceTest {

    private ActeurService acteurService;

    @MockBean
    private ActeurRepository acteurRepository;

    Acteur acteurMock;

    Film filmMock;

    @BeforeEach
    void setUp() {
        acteurService = new ActeurService(acteurRepository);

        filmMock = Film.builder()
                .id(1)
                .titre("test")
                .build();

        acteurMock = Acteur.builder()
                .nom("test")
                .prenom("test")
                .films(List.of(filmMock))
                .build();
    }

    @Test
    @DisplayName("Sauvegarder un acteur")
    void testSave() {
        // Act
        when(acteurRepository.save(acteurMock)).then(
                invocation -> {
                    Acteur acteur = invocation.getArgument(0);
                    acteur.setId(1);
                    return acteur;
                }
        );
        Acteur savedActor = acteurService.save(acteurMock);

        // Assert
        verify(acteurRepository).save(acteurMock);
        assertEquals(acteurMock.getNom(), savedActor.getNom());
        assertEquals(acteurMock.getPrenom(), savedActor.getPrenom());
        assertEquals(acteurMock.getFilms(), savedActor.getFilms());
    }

    @ParameterizedTest
    @DisplayName("Ne pas sauvegarder un acteur invalide")
    @MethodSource("provideInvalidActeurs")
    void shouldNotSaveInvalidActeur(Acteur acteur) {

        // Act
        assertThrows(BadRequestException.class, () -> acteurService.save(acteur));
    }

    private static Stream<Arguments> provideInvalidActeurs() {
        return Stream.of(
                // Acteur sans attributs, en utilisant le constructeur par défaut (vide)
                Arguments.of(new Acteur()),
                // Acteur sans prénom
                Arguments.of(Acteur.builder().nom("Test").build()),
                // Acteur sans nom
                Arguments.of(Acteur.builder().prenom("Test").build())
        );
    }

    @Test
    @DisplayName("Trouver un acteur par son id")
    void shouldReturnActorById() {
        // Arrange
        acteurMock.setId(1);
        when(acteurRepository.findById(1)).thenReturn(java.util.Optional.of(acteurMock));
        // Act
        Acteur acteur = acteurService.findById(1);
        // Assert
        verify(acteurRepository).findById(1);
        assertEquals(acteurMock, acteur);
    }

    @Test
    @DisplayName("Supprimer un acteur")
    void shouldDeleteActor() {
        // Arrange
        acteurMock.setId(1);
        when(acteurRepository.findById(1)).thenReturn(java.util.Optional.of(acteurMock));

        // Act
        acteurService.delete(acteurMock);
        // Assert
        verify(acteurRepository).delete(acteurMock);
    }

    @Test
    @DisplayName("Trouver tous les acteurs")
    void shouldReturnAllActors() {
        // Arrange
        acteurMock.setId(1);
        when(acteurRepository.findAll()).thenReturn(java.util.List.of(acteurMock));
        // Act
        List<Acteur> acteurs = acteurService.findAll();
        // Assert
        verify(acteurRepository).findAll();
        assertEquals(java.util.List.of(acteurMock), acteurs);
    }

    @Test
    @DisplayName("Trouver un acteur par son id inexistant")
    void shouldThrowExceptionWhenActorNotFound() {
        // Arrange
        when(acteurRepository.findById(1)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(NotFoundException.class, () -> acteurService.findById(1));
    }

}