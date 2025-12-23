package com.bezkoder.spring.security.jwt.payload.request;

public class GroupeRequest {

    private String nom;
    private Long sessionId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}