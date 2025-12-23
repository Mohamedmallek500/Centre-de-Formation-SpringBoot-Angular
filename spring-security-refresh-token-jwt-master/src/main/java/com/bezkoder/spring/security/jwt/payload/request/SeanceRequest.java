package com.bezkoder.spring.security.jwt.payload.request;

import com.bezkoder.spring.security.jwt.models.TypeSeance;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeanceRequest {

    private Long groupeId;
    private Long coursId;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private String salle;
    private TypeSeance typeSeance;
}
