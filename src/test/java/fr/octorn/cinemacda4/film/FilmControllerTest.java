package fr.octorn.cinemacda4.film;

import fr.octorn.cinemacda4.acteur.Acteur;
import fr.octorn.cinemacda4.acteur.mapper.ActeurMapper;
import fr.octorn.cinemacda4.film.mapper.FilmMapper;
import fr.octorn.cinemacda4.realisateur.Realisateur;
import fr.octorn.cinemacda4.seance.mapper.SeanceMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @MockBean
    private FilmService filmService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmMapper filmMapper;

    @MockBean
    private ActeurMapper acteurMapper;

    @MockBean
    private SeanceMapper seanceMapper;

    private Film filmMock;

    private Acteur acteurMock;

    private Realisateur realisateurMock;

    @BeforeEach
    void setUp() {
        realisateurMock = Realisateur.builder()
                .id(1)
                .nom("test" )
                .prenom("test" )
                .build();

        acteurMock = Acteur.builder()
                .id(1)
                .nom("test" )
                .prenom("test" )
                .build();

        filmMock = Film.builder()
                .id(1)
                .titre("test" )
                .dateSortie(LocalDate.parse("2021-10-10" ))
                .realisateur(realisateurMock)
                .acteurs(new ArrayList<>(List.of(acteurMock)))
                .build();

        acteurMock.setFilms(new ArrayList<>(List.of(filmMock)));
    }

    @Test
    @DisplayName("Récupérer tous les films" )
    void testFindAll() throws Exception {
        // Arrange
        when(filmService.findAll()).thenReturn(List.of(filmMock));

        // Act

        mockMvc.perform(get("/films" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).findAll();
    }

    @Test
    @DisplayName("Récupérer un film par son id" )
    void testFindById() throws Exception {
        // Arrange
        when(filmService.findById(1)).thenReturn(filmMock);

        // Act
        mockMvc.perform(get("/films/1" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).findById(1);
    }

    @Test
    @DisplayName("Supprimer un film" )
    void testDelete() throws Exception {
        // Arrange
        filmMock.setId(1);
        when(filmService.findById(1)).thenReturn(filmMock);

        // Act
        mockMvc.perform(delete("/films/1" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).deleteById(1);
    }

    @Test
    @DisplayName("Mettre à jour un film" )
    void testUpdate() throws Exception {
        // Arrange
        when(filmService.update(any(Film.class), eq(1))).thenReturn(filmMock);

        // Act
        mockMvc.perform(put("/films/1" )
                        .contentType("application/json" )
                        .content("""
                                {
                                  "titre": "test",
                                  "dateSortie": "2021-10-10",
                                  "realisateur": {
                                    "id": 1,
                                    "nom": "test",
                                    "prenom": "test"
                                  },
                                  "acteurs": [
                                    {
                                      "id": 1,
                                      "nom": "test",
                                      "prenom": "test"
                                    }
                                  ]
                                }"""))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).update(any(Film.class), eq(1));
    }

    @Test
    @DisplayName("Sauvegarder un film" )
    void testSave() throws Exception {
        // Arrange
        when(filmService.save(any(Film.class))).thenReturn(filmMock);

        // Act
        mockMvc.perform(post("/films" )
                        .contentType("application/json" )
                        .content("""
                                {
                                  "titre": "test",
                                  "dateSortie": "2021-10-10",
                                  "realisateur": {
                                    "id": 1,
                                    "nom": "test",
                                    "prenom": "test"
                                  },
                                  "acteurs": [
                                    {
                                      "id": 1,
                                      "nom": "test",
                                      "prenom": "test"
                                    }
                                  ]
                                }"""))
                .andExpect(status().isCreated());

        // Assert
        verify(filmService).save(any(Film.class));
    }

    @Test
    @DisplayName("Récupérer les acteurs d'un film" )
    void testFindActeursByFilmId() throws Exception {
        // Arrange
        when(filmService.findActeursByFilm(1)).thenReturn(new ArrayList<>(List.of(acteurMock)));

        // Act
        mockMvc.perform(get("/films/1/acteurs" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).findActeursByFilm(1);
    }

    @Test
    @DisplayName("Récupérer le réalisateur d'un film" )
    void testFindRealisateurByFilmId() throws Exception {
        // Arrange
        when(filmService.findById(1)).thenReturn(filmMock);

        // Act
        mockMvc.perform(get("/films/1/realisateur" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).findById(1);
    }

    @Test
    @DisplayName("Récupérer les acteurs d'un film" )
    void testFindActeursByFilm() throws Exception {
        // Arrange
        when(filmService.findById(1)).thenReturn(filmMock);

        // Act
        mockMvc.perform(get("/films/1/acteurs" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).findActeursByFilm(1);
    }

    @Test
    @DisplayName("Récupérer un film par son titre" )
    void testFindByTitre() throws Exception {
        // Arrange
        when(filmService.findByTitre("test")).thenReturn(filmMock);

        // Act
        mockMvc.perform(get("/films/search?titre=test" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).findByTitre("test");
    }

    @Test
    @DisplayName("Ajouter un acteur à un film" )
    void testAddActorToFilm() throws Exception {
        // Arrange
        when(filmService.addActorToFilm(1, acteurMock)).thenReturn(filmMock);

        // Act
        mockMvc.perform(post("/films/1/acteurs" )
                        .contentType("application/json" )
                        .content("""
                                {
                                  "id": 1,
                                  "nom": "test",
                                  "prenom": "test"
                                }"""))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).addActorToFilm(eq(1), any(Acteur.class));
    }

    @Test
    @DisplayName("Rechercher un film par son titre" )
    void testSearchByTitre() throws Exception {
        // Arrange
        when(filmService.findByTitre("test")).thenReturn(filmMock);

        // Act
        mockMvc.perform(get("/films/search?titre=test" ))
                .andExpect(status().isOk());

        // Assert
        verify(filmService).findByTitre("test");
    }

}