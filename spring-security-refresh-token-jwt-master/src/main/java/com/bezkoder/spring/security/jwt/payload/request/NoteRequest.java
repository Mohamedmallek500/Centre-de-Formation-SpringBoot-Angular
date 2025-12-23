package com.bezkoder.spring.security.jwt.payload.request;

import com.bezkoder.spring.security.jwt.models.TypeNote;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequest {

    private Long etudiantId;
    private Long coursId;
    private double valeur;
    private TypeNote typeNote;

}
