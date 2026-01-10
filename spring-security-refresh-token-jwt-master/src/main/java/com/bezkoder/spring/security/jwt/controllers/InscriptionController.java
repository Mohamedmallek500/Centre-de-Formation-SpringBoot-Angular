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

    // 🔹 Inscrire étudiant à un groupe
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

    // 🔹 ADMIN : valider / refuser
    @PutMapping("/{id}/statut")
    public ResponseEntity<Inscription> changerStatut(
            @PathVariable Long id,
            @RequestParam StatutInscription statut
    ) {
        return ResponseEntity.ok(
                inscriptionService.changerStatut(id, statut)
        );
    }

    // 🔹 Lister inscriptions d’un groupe
    @GetMapping("/groupe/{groupeId}")
    public ResponseEntity<List<Inscription>> getByGroupe(
            @PathVariable Long groupeId
    ) {
        return ResponseEntity.ok(
                inscriptionService.getByGroupe(groupeId)
        );
    }

    // 🔹 Lister inscriptions d’un étudiant
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Inscription>> getByEtudiant(
            @PathVariable Long etudiantId
    ) {
        return ResponseEntity.ok(
                inscriptionService.getByEtudiant(etudiantId)
        );
    }

    // 🔹 ADMIN : Lister toutes les inscriptions avec pagination (12 par page)
    @GetMapping
    public ResponseEntity<Page<Inscription>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(
                inscriptionService.getAllPaginated(page, size)
        );
    }

}
