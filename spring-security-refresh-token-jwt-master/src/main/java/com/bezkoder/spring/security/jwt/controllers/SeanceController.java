package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Seance;
import com.bezkoder.spring.security.jwt.models.TypeSeance;
import com.bezkoder.spring.security.jwt.payload.request.SeanceRequest;
import com.bezkoder.spring.security.jwt.service.SeanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seances")
@CrossOrigin(origins = "*")
public class SeanceController {

    private final SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    // =========================
    // CREATE (ADMIN)
    // =========================
    @PostMapping
    public ResponseEntity<Seance> creer(
            @RequestBody SeanceRequest request
    ) {
        return ResponseEntity.ok(
                seanceService.creerSeance(
                        request.getGroupeId(),
                        request.getCoursId(),
                        request.getHeureDebut(),
                        request.getHeureFin(),
                        request.getSalle(),
                        request.getTypeSeance()
                )
        );
    }

    // =========================
    // READ ALL
    // =========================
    @GetMapping
    public ResponseEntity<List<Seance>> getAll() {
        return ResponseEntity.ok(seanceService.getAll());
    }

    // =========================
    // READ ONE
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Seance> getById(@PathVariable Long id) {
        return ResponseEntity.ok(seanceService.getById(id));
    }

    // =========================
    // EMPLOI DU TEMPS GROUPE
    // =========================
    @GetMapping("/groupe/{groupeId}")
    public ResponseEntity<List<Seance>> getByGroupe(
            @PathVariable Long groupeId
    ) {
        return ResponseEntity.ok(
                seanceService.getByGroupe(groupeId)
        );
    }

    // =========================
    // EMPLOI DU TEMPS FORMATEUR
    // =========================
    @GetMapping("/formateur/{formateurId}")
    public ResponseEntity<List<Seance>> getByFormateur(
            @PathVariable Long formateurId
    ) {
        return ResponseEntity.ok(
                seanceService.getByFormateur(formateurId)
        );
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
