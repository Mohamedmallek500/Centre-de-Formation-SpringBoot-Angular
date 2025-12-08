package com.bezkoder.spring.security.jwt.service;

import com.bezkoder.spring.security.jwt.models.Etudiant;
import com.bezkoder.spring.security.jwt.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    public String generateMatricule() {

        int year = LocalDate.now().getYear();
        String prefix = "ETU-" + year + "-";   // ex: "ETU-2025-"

        Etudiant last = etudiantRepository.findTopByOrderByIdDesc();

        int nextNumber = 1;

        if (last != null && last.getMatricule() != null &&
                last.getMatricule().startsWith(prefix)) {
            // ex: "ETU-2025-00012"
            String[] parts = last.getMatricule().split("-");
            try {
                nextNumber = Integer.parseInt(parts[2]) + 1;
            } catch (Exception e) {
                nextNumber = 1; // au cas où matricule mal formé
            }
        }

        return prefix + String.format("%05d", nextNumber);  // ETU-2025-00001
    }
}
