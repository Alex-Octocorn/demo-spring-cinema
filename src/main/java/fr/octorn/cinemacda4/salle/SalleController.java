package fr.octorn.cinemacda4.salle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("salles")
public class SalleController {
    private final SalleService salleService;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Salle save(@RequestBody Salle salle) {
        return salleService.save(salle);
    }

    @PutMapping("{id}")
    public Salle update(@RequestBody Salle salle, @PathVariable Integer id) {
        salle.setId(id);
        return salleService.update(salle, id);
    }

    @GetMapping("/disponible")
    public List<Salle> findAllAvailableByDate(@RequestParam String date) {
        return salleService.findAllAvailableByDate(date);
    }

}
