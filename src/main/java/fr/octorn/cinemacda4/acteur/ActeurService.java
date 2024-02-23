package fr.octorn.cinemacda4.acteur;

import fr.octorn.cinemacda4.exceptions.BadRequestException;
import fr.octorn.cinemacda4.exceptions.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActeurService {
    private final ActeurRepository acteurRepository;

    public ActeurService(ActeurRepository acteurRepository) {
        this.acteurRepository = acteurRepository;
    }

    public Acteur save(Acteur entity) {

        List<String> errors = new ArrayList<>();

        if (entity.getNom() == null ) {
            errors.add("Le nom de l'acteur est obligatoire");
        }

        if (entity.getPrenom() == null) {
            errors.add("Le prénom de l'acteur est obligatoire");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }


        return acteurRepository.save(entity);
    }

    public Acteur findById(Integer integer) {
        return acteurRepository.findById(integer).orElseThrow(
                () -> new NotFoundException("L'acteur avec l'id " + integer + " n'existe pas")
        );
    }

    public void delete(int id) {
        Acteur acteur = this.findById(id);
        acteurRepository.delete(acteur);
    }

    public List<Acteur> findAll() {
        return acteurRepository.findAll();
    }
}
