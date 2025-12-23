package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.SessionPedagogique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionPedagogiqueRepository extends JpaRepository<SessionPedagogique, Long> {
}
