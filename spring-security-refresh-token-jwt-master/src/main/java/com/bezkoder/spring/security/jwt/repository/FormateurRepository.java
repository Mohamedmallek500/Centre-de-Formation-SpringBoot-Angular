package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormateurRepository extends JpaRepository<Formateur, Long> {
}
