package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.GroupeCours;
import com.bezkoder.spring.security.jwt.payload.request.GroupeCoursRequest;
import com.bezkoder.spring.security.jwt.service.GroupeCoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupe-cours")
@CrossOrigin(origins = "*")
public class GroupeCoursController {

    private final GroupeCoursService service;

    public GroupeCoursController(GroupeCoursService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<GroupeCours> create(
            @RequestBody GroupeCoursRequest request
    ) {
        return ResponseEntity.ok(
                service.affecterCoursAGroupe(
                        request.getGroupeId(),
                        request.getCoursId(),
                        request.getVolumeHoraire(),
                        request.getCoefficient()
                )
        );
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<GroupeCours>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<GroupeCours> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<GroupeCours> update(
            @PathVariable Long id,
            @RequestBody GroupeCoursRequest request
    ) {
        return ResponseEntity.ok(
                service.update(
                        id,
                        request.getVolumeHoraire(),
                        request.getCoefficient()
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // LISTE DES COURS D’UN GROUPE
    @GetMapping("/groupe/{groupeId}")
    public ResponseEntity<List<GroupeCours>> getByGroupe(
            @PathVariable Long groupeId
    ) {
        return ResponseEntity.ok(service.getCoursByGroupe(groupeId));
    }
}
