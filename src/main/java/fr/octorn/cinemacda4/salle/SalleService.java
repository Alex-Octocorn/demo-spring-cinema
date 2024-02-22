package fr.octorn.cinemacda4.salle;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleService {
    private SalleRepository salleRepository;

    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public List<Salle> findAll() {
        return salleRepository.findAll();
    }

    public Salle findById(Integer id) {
        return salleRepository.findById(id).orElseThrow( () -> new RuntimeException("Salle not found"));
    }

    public Salle save(Salle salle) {
        return salleRepository.save(salle);
    }

    public void deleteById(Integer id) {
        this.findById(id);
        salleRepository.deleteById(id);
    }

    public Salle update(Salle salle, Integer id) {
        salle.setId(id);
        this.findById(id);
        return salleRepository.save(salle);
    }
}
