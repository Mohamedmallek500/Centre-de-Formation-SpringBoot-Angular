package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.Cours;
import com.bezkoder.spring.security.jwt.models.Formateur;
import com.bezkoder.spring.security.jwt.payload.request.CoursRequest;
import com.bezkoder.spring.security.jwt.repository.CoursRepository;
import com.bezkoder.spring.security.jwt.repository.FormateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService {

    private final CoursRepository coursRepository;
    private final FormateurRepository formateurRepository;

    public CoursService(
            CoursRepository coursRepository,
            FormateurRepository formateurRepository
    ) {
        this.coursRepository = coursRepository;
        this.formateurRepository = formateurRepository;
    }

    // CREATE
    public Cours creerCours(CoursRequest request) {

        if (coursRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Un cours avec ce code existe déjà");
        }

        Cours cours = new Cours();
        mapRequestToCours(cours, request);

        return coursRepository.save(cours);
    }

    // READ ALL
    public List<Cours> getAll() {
        return coursRepository.findAll();
    }

    // READ ONE
    public Cours getById(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
    }

    // UPDATE
    public Cours update(Long id, CoursRequest request) {

        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        mapRequestToCours(cours, request);

        return coursRepository.save(cours);
    }

    // DELETE
    public void delete(Long id) {
        if (!coursRepository.existsById(id)) {
            throw new RuntimeException("Cours introuvable");
        }
        coursRepository.deleteById(id);
    }

    // ==========================
    //      MÉTHODE UTILITAIRE
    // ==========================
    private void mapRequestToCours(Cours cours, CoursRequest request) {

        cours.setCode(request.getCode());
        cours.setTitre(request.getTitre());
        cours.setDescription(request.getDescription());
        cours.setNbHeures(request.getNbHeures());
        cours.setCoefficient(request.getCoefficient());

        if (request.getFormateurId() != null) {
            Formateur formateur = formateurRepository.findById(request.getFormateurId())
                    .orElseThrow(() -> new RuntimeException("Formateur introuvable"));
            cours.setFormateur(formateur);
        } else {
            cours.setFormateur(null);
        }
    }
}
