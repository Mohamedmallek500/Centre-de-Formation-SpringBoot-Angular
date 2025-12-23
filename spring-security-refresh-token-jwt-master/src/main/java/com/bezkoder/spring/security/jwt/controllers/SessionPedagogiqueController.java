package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.SessionPedagogique;
import com.bezkoder.spring.security.jwt.service.SessionPedagogiqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*")
public class SessionPedagogiqueController {

    private final SessionPedagogiqueService service;

    public SessionPedagogiqueController(SessionPedagogiqueService service) {
        this.service = service;
    }

    // Créer une session pédagogique via BODY
    @PostMapping
    public ResponseEntity<SessionPedagogique> creerSession(
            @RequestBody SessionPedagogique session
    ) {
        return ResponseEntity.ok(
                service.creerSession(session.getAnnee(), session.getSemestre())
        );
    }

    // Lister toutes les sessions
    @GetMapping
    public ResponseEntity<List<SessionPedagogique>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Détail d'une session
    @GetMapping("/{id}")
    public ResponseEntity<SessionPedagogique> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
