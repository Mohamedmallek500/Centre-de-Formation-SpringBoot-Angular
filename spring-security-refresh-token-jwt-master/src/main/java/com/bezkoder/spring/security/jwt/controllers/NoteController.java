package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Note;
import com.bezkoder.spring.security.jwt.payload.request.NoteRequest;
import com.bezkoder.spring.security.jwt.payload.request.NoteUpdateRequest;
import com.bezkoder.spring.security.jwt.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin("*")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Note> create(
            @RequestBody NoteRequest request
    ) {
        return ResponseEntity.ok(noteService.ajouterNote(request));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Note> update(
            @PathVariable Long id,
            @RequestBody NoteUpdateRequest request
    ) {
        return ResponseEntity.ok(
                noteService.modifierNote(id, request.getValeur())
        );
    }


    // NOTES D’UN ÉTUDIANT
    @GetMapping("/etudiant/{id}")
    public ResponseEntity<List<Note>> getByEtudiant(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.getNotesEtudiant(id));
    }

    // NOTES D’UN COURS
    @GetMapping("/cours/{id}")
    public ResponseEntity<List<Note>> getByCours(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.getNotesCours(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
