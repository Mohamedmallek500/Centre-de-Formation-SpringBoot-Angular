package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursRequest {

    private String code;
    private String titre;
    private String description;
    private int nbHeures;
    private int coefficient;
    private Long formateurId;

}
