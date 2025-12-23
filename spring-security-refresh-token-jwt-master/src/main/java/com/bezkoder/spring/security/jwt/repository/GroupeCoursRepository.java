package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.GroupeCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeCoursRepository extends JpaRepository<GroupeCours, Long> {

    boolean existsByGroupeIdAndCoursId(Long groupeId, Long coursId);

    List<GroupeCours> findByGroupeId(Long groupeId);
}
