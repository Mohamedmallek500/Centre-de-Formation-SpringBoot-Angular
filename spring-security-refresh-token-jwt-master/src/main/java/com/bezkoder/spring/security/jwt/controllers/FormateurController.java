package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Cours;
import com.bezkoder.spring.security.jwt.models.Formateur;
import com.bezkoder.spring.security.jwt.service.FormateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formateurs")
@CrossOrigin(origins = "*")
public class FormateurController {

    private final FormateurService formateurService;

    public FormateurController(FormateurService formateurService) {
        this.formateurService = formateurService;
    }

    // =========================
    // READ ALL
    // =========================
    @GetMapping
    public ResponseEntity<List<Formateur>> getAll() {
        return ResponseEntity.ok(formateurService.getAll());
    }

    // =========================
    // READ ONE
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Formateur> getById(@PathVariable Long id) {
        return ResponseEntity.ok(formateurService.getById(id));
    }

    // =========================
    // UPDATE
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Formateur> update(
            @PathVariable Long id,
            @RequestBody Formateur formateur
    ) {
        return ResponseEntity.ok(
                formateurService.update(id, formateur)
        );
    }

    // =========================
    // DELETE (AVEC VÉRIFICATION)
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formateurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // COURS D’UN FORMATEUR
    // =========================
    @GetMapping("/{id}/cours")
    public ResponseEntity<List<Cours>> getCoursByFormateur(@PathVariable Long id) {
        return ResponseEntity.ok(
                formateurService.getCoursByFormateur(id)
        );
    }
}
