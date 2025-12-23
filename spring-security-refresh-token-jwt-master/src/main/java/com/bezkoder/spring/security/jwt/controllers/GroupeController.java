package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Groupe;
import com.bezkoder.spring.security.jwt.payload.request.GroupeRequest;
import com.bezkoder.spring.security.jwt.service.GroupeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
@CrossOrigin(origins = "*")
public class GroupeController {

    private final GroupeService groupeService;

    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @PostMapping
    public ResponseEntity<Groupe> creerGroupe(
            @RequestBody GroupeRequest request
    ) {
        Groupe groupe = groupeService.creerGroupe(
                request.getNom(),
                request.getSessionId()
        );
        return ResponseEntity.ok(groupe);
    }

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return ResponseEntity.ok(groupeService.getAllGroupes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Groupe> getGroupe(@PathVariable Long id) {
        return ResponseEntity.ok(groupeService.getGroupeById(id));
    }
}
