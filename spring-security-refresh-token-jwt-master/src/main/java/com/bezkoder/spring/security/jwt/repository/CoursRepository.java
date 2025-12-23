package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    boolean existsByCode(String code);
}
