package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.Groupe;
import com.bezkoder.spring.security.jwt.models.SessionPedagogique;
import com.bezkoder.spring.security.jwt.repository.GroupeRepository;
import com.bezkoder.spring.security.jwt.repository.SessionPedagogiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeService {

    private final GroupeRepository groupeRepository;
    private final SessionPedagogiqueRepository sessionRepository;

    public GroupeService(
            GroupeRepository groupeRepository,
            SessionPedagogiqueRepository sessionRepository
    ) {
        this.groupeRepository = groupeRepository;
        this.sessionRepository = sessionRepository;
    }

    public Groupe creerGroupe(String nom, Long sessionId) {

        if (groupeRepository.existsByNom(nom)) {
            throw new RuntimeException("Un groupe avec ce nom existe déjà");
        }

        SessionPedagogique session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session pédagogique introuvable"));

        Groupe groupe = new Groupe();
        groupe.setNom(nom);
        groupe.setSessionPedagogique(session);

        return groupeRepository.save(groupe);
    }

    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }

    public Groupe getGroupeById(Long id) {
        return groupeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Groupe introuvable"));
    }
}
