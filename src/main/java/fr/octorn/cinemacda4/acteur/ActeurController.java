package fr.octorn.cinemacda4.acteur;

import fr.octorn.cinemacda4.acteur.dto.ActeurReduitDto;
import fr.octorn.cinemacda4.acteur.dto.ActeurSansFilmDto;
import fr.octorn.cinemacda4.acteur.mapper.ActeurMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acteurs")
public class ActeurController {
    private final ActeurService acteurService;

    private final ActeurMapper acteurMapper;


    public ActeurController(
            ActeurService acteurService,
            ActeurMapper acteurMapper
    ) {
        this.acteurService = acteurService;
        this.acteurMapper = acteurMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Acteur save(@RequestBody Acteur entity) {
        return acteurService.save(entity);
    }

    @GetMapping("/{id}")
    public ActeurReduitDto findById(@PathVariable Integer id) {

        Acteur acteur = acteurService.findById(id);

        return acteurMapper.toActeurReduit(acteur);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestBody Acteur acteur) {
        acteurService.delete(acteur);
    }

    @GetMapping
    public List<ActeurSansFilmDto> findAll() {

        List<Acteur> acteurs = acteurService.findAll();

        return acteurMapper.toActeursSansFilm(acteurs);
    }
}
