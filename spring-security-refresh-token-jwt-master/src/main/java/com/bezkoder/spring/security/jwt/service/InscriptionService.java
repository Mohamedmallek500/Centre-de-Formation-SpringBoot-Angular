package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.Etudiant;
import com.bezkoder.spring.security.jwt.models.Groupe;
import com.bezkoder.spring.security.jwt.models.Inscription;
import com.bezkoder.spring.security.jwt.models.StatutInscription;
import com.bezkoder.spring.security.jwt.repository.EtudiantRepository;
import com.bezkoder.spring.security.jwt.repository.GroupeRepository;
import com.bezkoder.spring.security.jwt.repository.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final GroupeRepository groupeRepository;

    public InscriptionService(
            InscriptionRepository inscriptionRepository,
            EtudiantRepository etudiantRepository,
            GroupeRepository groupeRepository
    ) {
        this.inscriptionRepository = inscriptionRepository;
        this.etudiantRepository = etudiantRepository;
        this.groupeRepository = groupeRepository;
    }

    public Inscription inscrireEtudiant(Long etudiantId, Long groupeId) {

        if (inscriptionRepository.existsByEtudiantIdAndGroupeId(etudiantId, groupeId)) {
            throw new RuntimeException("Étudiant déjà inscrit à ce groupe");
        }

        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe introuvable"));

        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setGroupe(groupe);
        inscription.setDateInscription(LocalDate.now());
        inscription.setStatut(StatutInscription.EN_ATTENTE);

        return inscriptionRepository.save(inscription);
    }

    // ✅ ADMIN : valider / refuser
    public Inscription changerStatut(Long inscriptionId, StatutInscription statut) {

        Inscription inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new RuntimeException("Inscription introuvable"));

        inscription.setStatut(statut);
        return inscriptionRepository.save(inscription);
    }

    // 📋 inscriptions d’un groupe
    public List<Inscription> getByGroupe(Long groupeId) {
        return inscriptionRepository.findByGroupeId(groupeId);
    }

    // 📋 inscriptions d’un étudiant
    public List<Inscription> getByEtudiant(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }

    // 📋 ADMIN : toutes les inscriptions avec pagination
    public Page<Inscription> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return inscriptionRepository.findAll(pageable);
    }

}
