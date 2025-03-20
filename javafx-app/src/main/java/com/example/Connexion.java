package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private String url;
    private String utilisateur;
    private String motDePasse;
    private Connection connexion;

    public Connexion(String url, String utilisateur, String motDePasse) {
        this.url = url;
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
        try {
            this.connexion = DriverManager.getConnection(this.url, this.utilisateur, this.motDePasse);
            System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }

    public Connection getConnexion() {
        return this.connexion;
    }

    public void close() {
        if (this.connexion != null) {
            try {
                this.connexion.close();
                System.out.println("Connexion fermée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}