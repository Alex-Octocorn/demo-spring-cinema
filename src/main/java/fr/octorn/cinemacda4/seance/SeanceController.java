package fr.octorn.cinemacda4.seance;

import fr.octorn.cinemacda4.seance.dto.SeanceReduit;
import fr.octorn.cinemacda4.seance.mapper.SeanceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("seances")
public class SeanceController {

    private final SeanceService seanceService;

    private final SeanceMapper seanceMapper;

    public SeanceController(
            SeanceService seanceService,
            SeanceMapper seanceMapper
    ) {
        this.seanceService = seanceService;
        this.seanceMapper = seanceMapper;
    }

    @GetMapping
    public List<Seance> findAll() {
        return seanceService.findAll();
    }

    @GetMapping("{id}")
    public Seance findById(@PathVariable Integer id) {
        return seanceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeanceReduit save(@RequestBody Seance seance) {
        return seanceMapper.toSeanceReduit(seanceService.save(seance));
    }

    @PutMapping("{id}")
    public Seance update(@RequestBody Seance seance, @PathVariable Integer id) {
        seance.setId(id);
        return seanceService.update(seance, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        seanceService.deleteById(id);
    }
}
