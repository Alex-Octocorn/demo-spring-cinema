package fr.octorn.cinemacda4.seance;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeanceService {

    private final SeanceRepository seanceRepository;

    public SeanceService(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    public Seance save(Seance seance) {
        return seanceRepository.save(seance);
    }

    public Seance findById(Integer id) {
        return seanceRepository.findById(id).orElseThrow( () -> new RuntimeException("Seance not found"));
    }

    public void deleteById(Integer id) {
        this.findById(id);
        seanceRepository.deleteById(id);
    }

    public Seance update(Seance seance, Integer id) {
        seance.setId(id);
        this.findById(id);
        return seanceRepository.save(seance);
    }

    public List<Seance> findAll() {
        return seanceRepository.findAll();
    }
}
