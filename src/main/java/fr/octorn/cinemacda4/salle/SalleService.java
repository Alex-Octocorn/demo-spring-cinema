package fr.octorn.cinemacda4.salle;

import fr.octorn.cinemacda4.seance.Seance;
import fr.octorn.cinemacda4.seance.SeanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalleService {
    private SalleRepository salleRepository;

    private final SeanceRepository seanceRepository;

    public SalleService(
            SalleRepository salleRepository,
            SeanceRepository seanceRepository
    ) {
        this.salleRepository = salleRepository;
        this.seanceRepository = seanceRepository;
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

    public List<Salle> findAllAvailableByDate(String date) {

        LocalDateTime dateRecherche = LocalDateTime.of(LocalDate.parse(date), LocalDateTime.MIN.toLocalTime());
        List<Salle> salles = salleRepository.findAll();
        List<Salle> sallesDispo = new ArrayList<>();

        for (Salle salle : salles) {
            List<Seance> seances = seanceRepository.findAllBySalleIdAndDateBetween(
                    salle.getId(),
                    dateRecherche,
                    LocalDateTime.of(LocalDate.parse(date), LocalDateTime.MAX.toLocalTime())
            );
            if (seances.isEmpty()) {
                sallesDispo.add(salle);
            }
        }
        return sallesDispo;
    }
}
