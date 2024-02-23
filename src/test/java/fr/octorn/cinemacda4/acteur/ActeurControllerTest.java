package fr.octorn.cinemacda4.acteur;

import fr.octorn.cinemacda4.acteur.mapper.ActeurMapper;
import fr.octorn.cinemacda4.exceptions.NotFoundException;
import fr.octorn.cinemacda4.film.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActeurController.class)
class ActeurControllerTest {

    @MockBean
    private ActeurService acteurService;

    @MockBean
    private ActeurMapper acteurMapper;


    @Autowired
    private MockMvc mockMvc;

    private Acteur acteur;

    private Film film;

    @BeforeEach
    void setUp() {
        film = Film.builder()
                .titre("test")
                .build();

        acteur = Acteur.builder()
                .nom("test")
                .prenom("test")
                .films(List.of(film))
                .build();
    }

    @Test
    @DisplayName("Récupérer tous les acteurs")
    void testFindAll() throws Exception {
        // Arrange
        when(acteurService.findAll()).thenReturn(List.of(acteur));

        // Act

        mockMvc.perform(get("/acteurs"))
                .andExpect(status().isOk());

        // Assert
        verify(acteurService).findAll();
    }

    @Test
    @DisplayName("Récupérer un acteur par son id")
    void testFindById() throws Exception {
        // Arrange
        when(acteurService.findById(1)).thenReturn(acteur);

        // Act
        mockMvc.perform(get("/acteurs/1"))
                .andExpect(status().isOk());

        // Assert
        verify(acteurService).findById(1);
    }

    @Test
    @DisplayName("Supprimer un acteur")
    void testDelete() throws Exception {
        // Arrange
        acteur.setId(1);
        when(acteurService.findById(1)).thenReturn(acteur);

        // Act
        mockMvc.perform(delete("/acteurs/1"))
                .andExpect(status().isOk());

        // Assert
        verify(acteurService).delete(1);
    }

    @Test
    @DisplayName("Sauvegarder un acteur")
    void testSave() throws Exception {
        // Arrange
        when(acteurService.save(acteur)).thenReturn(acteur);

        // Act
        mockMvc.perform(post("/acteurs")
                .contentType("application/json")
                .content("{\n" +
                        "  \"nom\": \"test\",\n" +
                        "  \"prenom\": \"test\",\n" +
                        "  \"films\": [\n" +
                        "    {\n" +
                        "      \"titre\": \"test\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
        ).andExpect(status().isCreated());

        // Assert
        verify(acteurService).save(any(Acteur.class));
    }

    @Test
    @DisplayName("Récupérer un acteur par son id inexistant")
    void testFindByIdNotFound() throws Exception {
        // Arrange
        when(acteurService.findById(1)).thenThrow(new NotFoundException("L'acteur avec l'id 1 n'existe pas"));

        // Act
        mockMvc.perform(get("/acteurs/1"))
                .andExpect(status().isNotFound());

        // Assert
        verify(acteurService).findById(1);
    }

}