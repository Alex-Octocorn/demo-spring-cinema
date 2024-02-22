package fr.octorn.cinemacda4.seance;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("seances")
public class SeanceController {

    private SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
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
    public Seance save(@RequestBody Seance seance) {
        return seanceService.save(seance);
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
