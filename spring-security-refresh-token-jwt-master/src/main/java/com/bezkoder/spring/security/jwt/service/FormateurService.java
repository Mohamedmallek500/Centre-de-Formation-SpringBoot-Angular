package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.Cours;
import com.bezkoder.spring.security.jwt.models.Formateur;
import com.bezkoder.spring.security.jwt.repository.FormateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormateurService {

    private final FormateurRepository formateurRepository;

    public FormateurService(FormateurRepository formateurRepository) {
        this.formateurRepository = formateurRepository;
    }

    // =========================
    // READ ALL
    // =========================
    public List<Formateur> getAll() {
        return formateurRepository.findAll();
    }

    // =========================
    // READ ONE
    // =========================
    public Formateur getById(Long id) {
        return formateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur introuvable"));
    }

    // =========================
    // UPDATE
    // =========================
    public Formateur update(Long id, Formateur updated) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur introuvable"));

        formateur.setNom(updated.getNom());
        formateur.setPrenom(updated.getPrenom());
        formateur.setEmail(updated.getEmail());
        formateur.setTelephone(updated.getTelephone());
        formateur.setCin(updated.getCin());
        formateur.setPhoto(updated.getPhoto());
        formateur.setSpecialite(updated.getSpecialite());

        return formateurRepository.save(formateur);
    }

    // =========================
    // DELETE AVEC VÉRIFICATION
    // =========================
    public void delete(Long id) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur introuvable"));

        // 🔥 Vérification: a-t-il des cours ?
        if (formateur.getCours() != null && !formateur.getCours().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer ce formateur : il a des cours affectés");
        }

        formateurRepository.delete(formateur);
    }

    // =========================
    // COURS D’UN FORMATEUR
    // =========================
    public List<Cours> getCoursByFormateur(Long id) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur introuvable"));

        return formateur.getCours();
    }
}
