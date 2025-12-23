package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.SessionPedagogique;
import com.bezkoder.spring.security.jwt.repository.SessionPedagogiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionPedagogiqueService {

    private final SessionPedagogiqueRepository repository;

    public SessionPedagogiqueService(SessionPedagogiqueRepository repository) {
        this.repository = repository;
    }

    public SessionPedagogique creerSession(String annee, String semestre) {
        SessionPedagogique session = new SessionPedagogique();
        session.setAnnee(annee);
        session.setSemestre(semestre);
        return repository.save(session);
    }

    public List<SessionPedagogique> getAll() {
        return repository.findAll();
    }

    public SessionPedagogique getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session pédagogique introuvable"));
    }
}
