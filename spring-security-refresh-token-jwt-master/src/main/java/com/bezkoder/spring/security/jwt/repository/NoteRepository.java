package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Note;
import com.bezkoder.spring.security.jwt.models.TypeNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    boolean existsByEtudiantIdAndCoursIdAndTypeNote(
            Long etudiantId,
            Long coursId,
            TypeNote typeNote
    );

    List<Note> findByEtudiantId(Long etudiantId);

    List<Note> findByCoursId(Long coursId);
}
