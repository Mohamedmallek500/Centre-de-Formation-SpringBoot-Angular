package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Cours;
import com.bezkoder.spring.security.jwt.payload.request.CoursRequest;
import com.bezkoder.spring.security.jwt.service.CoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
@CrossOrigin(origins = "*")
public class CoursController {

    private final CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    // CREATE (BODY uniquement)
    @PostMapping
    public ResponseEntity<Cours> creer(
            @RequestBody CoursRequest request
    ) {
        return ResponseEntity.ok(
                coursService.creerCours(request)
        );
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Cours>> getAll() {
        return ResponseEntity.ok(coursService.getAll());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Cours> getById(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.getById(id));
    }

    // UPDATE (BODY uniquement)
    @PutMapping("/{id}")
    public ResponseEntity<Cours> update(
            @PathVariable Long id,
            @RequestBody CoursRequest request
    ) {
        return ResponseEntity.ok(
                coursService.update(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coursService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
