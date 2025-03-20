package com.example;

import java.time.LocalDateTime;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Utilisateur(String nom, String email) {
        this.nom = nom;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Utilisateur(int id, String nom, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isValidEmail() {
        return this.email.contains("@gmail.com");
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getName() {
        return nom;
    }

    public void setName(String name) {
        this.nom = name;
    }
}