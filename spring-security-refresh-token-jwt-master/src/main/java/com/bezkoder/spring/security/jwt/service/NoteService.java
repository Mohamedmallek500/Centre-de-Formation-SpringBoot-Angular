package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.Cours;
import com.bezkoder.spring.security.jwt.models.Etudiant;
import com.bezkoder.spring.security.jwt.models.Note;
import com.bezkoder.spring.security.jwt.payload.request.NoteRequest;
import com.bezkoder.spring.security.jwt.repository.CoursRepository;
import com.bezkoder.spring.security.jwt.repository.EtudiantRepository;
import com.bezkoder.spring.security.jwt.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;

    public NoteService(
            NoteRepository noteRepository,
            EtudiantRepository etudiantRepository,
            CoursRepository coursRepository
    ) {
        this.noteRepository = noteRepository;
        this.etudiantRepository = etudiantRepository;
        this.coursRepository = coursRepository;
    }

    // CREATE
    public Note ajouterNote(NoteRequest request) {

        if (request.getValeur() < 0 || request.getValeur() > 20) {
            throw new RuntimeException("Note invalide (0 - 20)");
        }

        if (noteRepository.existsByEtudiantIdAndCoursIdAndTypeNote(
                request.getEtudiantId(),
                request.getCoursId(),
                request.getTypeNote()
        )) {
            throw new RuntimeException("Note déjà saisie pour ce type");
        }

        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Cours cours = coursRepository.findById(request.getCoursId())
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        Note note = new Note();
        note.setValeur(request.getValeur());
        note.setTypeNote(request.getTypeNote());
        note.setEtudiant(etudiant);
        note.setCours(cours);

        return noteRepository.save(note);
    }

    // UPDATE
    public Note modifierNote(Long id, double valeur) {

        if (valeur < 0 || valeur > 20) {
            throw new RuntimeException("Note invalide (0 - 20)");
        }

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));

        note.setValeur(valeur);
        return noteRepository.save(note);
    }

    // READ
    public List<Note> getNotesEtudiant(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    public List<Note> getNotesCours(Long coursId) {
        return noteRepository.findByCoursId(coursId);
    }

    // DELETE
    public void supprimer(Long id) {
        noteRepository.deleteById(id);
    }
}
