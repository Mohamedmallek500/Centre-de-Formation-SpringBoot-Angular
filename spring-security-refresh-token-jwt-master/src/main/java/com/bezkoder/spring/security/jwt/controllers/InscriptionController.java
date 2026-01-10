package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Inscription;
import com.bezkoder.spring.security.jwt.models.StatutInscription;
import com.bezkoder.spring.security.jwt.payload.request.InscriptionRequest;
import com.bezkoder.spring.security.jwt.service.InscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/inscriptions")
@CrossOrigin(origins = "*")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public ResponseEntity<Inscription> inscrire(
            @RequestBody InscriptionRequest request
    ) {
        return ResponseEntity.ok(
                inscriptionService.inscrireEtudiant(
                        request.getEtudiantId(),
                        request.getGroupeId()
                )
        );
    }

    // =========================
    // UPDATE : STATUT
    // =========================
    @PutMapping("/{id}/statut")
    public ResponseEntity<Inscription> changerStatut(
            @PathVariable Long id,
            @RequestParam StatutInscription statut
    ) {
        return ResponseEntity.ok(
                inscriptionService.changerStatut(id, statut)
        );
    }

    // =========================
    // READ : PAR GROUPE
    // =========================
    @GetMapping("/groupe/{groupeId}")
    public ResponseEntity<List<Inscription>> getByGroupe(
            @PathVariable Long groupeId
    ) {
        return ResponseEntity.ok(
                inscriptionService.getByGroupe(groupeId)
        );
    }

    // =========================
    // READ : PAR ÉTUDIANT
    // =========================
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Inscription>> getByEtudiant(
            @PathVariable Long etudiantId
    ) {
        return ResponseEntity.ok(
                inscriptionService.getByEtudiant(etudiantId)
        );
    }

    // =========================
    // READ : FILTRÉ + PAGINÉ (ADMIN)
    // =========================
    @GetMapping
    public ResponseEntity<Page<Inscription>> getAllPaginated(
            @RequestParam(required = false) String etudiant,
            @RequestParam(required = false) StatutInscription statut,
            @RequestParam(required = false) String groupe,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(
                inscriptionService.getAllFiltered(
                        etudiant,
                        statut,
                        groupe,
                        page,
                        size
                )
        );
    }
}
