package fr.octorn.cinemacda4.salle;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("salles")
public class SalleController {
    private SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping
    public List<Salle> findAll() {
        return salleService.findAll();
    }

    @GetMapping("{id}")
    public Salle findById(@PathVariable Integer id) {
        return salleService.findById(id);
    }

    @PostMapping
    public Salle save(@RequestBody Salle salle) {
        return salleService.save(salle);
    }

    @PutMapping("{id}")
    public Salle update(@RequestBody Salle salle, @PathVariable Integer id) {
        salle.setId(id);
        return salleService.update(salle, id);
    }

}
