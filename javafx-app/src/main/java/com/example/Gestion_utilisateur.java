package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Gestion_utilisateur {
    private Connexion link;
   
    

    public Gestion_utilisateur(Connexion link) {
        this.link = link;
        
    }

    public ArrayList<Utilisateur> listerUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            Statement stmt = this.link.getConnexion().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nom, email, createdAt, updatedAt FROM utilisateurs order by id ");
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return utilisateurs;
    }

    public boolean emailExisteDeja(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
            PreparedStatement pstmt = this.link.getConnexion().prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'email : " + e.getMessage());
        }
        return false;
    }

    public String ajouterUtilisateur(Utilisateur utilisateur) {
        // message de confirmation que l'utilisateur a été bien ajouté
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation d'ajout");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment ajouter cet utilisateur ?");
        if (alert.showAndWait().get() != ButtonType.OK) {
            return "Ajout annulé.";
        }
        // vérification de l'email
        if (!utilisateur.isValidEmail()) {
            return "Email non valide ! L'email doit contenir '@gmail.com'.";
        }
        // vérification de l'email s'il existe déjà
        if (emailExisteDeja(utilisateur.getEmail())) {
            return "Email déjà utilisé !";
        }
        // insertion de l'utilisateur dans la base de données
        try {
            String sql = "INSERT INTO utilisateurs (nom, email, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = this.link.getConnexion().prepareStatement(sql);
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setTimestamp(3, Timestamp.valueOf(utilisateur.getCreatedAt()));
            pstmt.setTimestamp(4, Timestamp.valueOf(utilisateur.getUpdatedAt()));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Utilisateur ajouté avec succès.";
            } else {
                //pour afficher une alerte si le nom est invalide
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Erreur");
                alert2.setHeaderText(null);
                alert2.setContentText("Erreur lors de l'ajout de l'utilisateur.");
                alert2.showAndWait();
                return "Erreur lors de l'ajout de l'utilisateur.";

                //return "Nom non valide !" ;
            }

        } catch (SQLException e) {
            return "Erreur lors de l'ajout : " + e.getMessage();
        }
    }

    public String modifierUtilisateur(int id, String nouveauNom, String nouvelEmail) {
        Utilisateur utilisateur = new Utilisateur(nouveauNom, nouvelEmail);
        if (!utilisateur.isValidEmail()) {
            return "Email non valide ! L'email doit contenir '@gmail.com'.";
        }
        if (emailExisteDeja(nouvelEmail)) {
            return "Email déjà utilisé !";
        }
        try {
            String sqlUpdate = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";
            PreparedStatement pstmtUpdate = this.link.getConnexion().prepareStatement(sqlUpdate);
            pstmtUpdate.setString(1, nouveauNom);
            pstmtUpdate.setString(2, nouvelEmail);
            pstmtUpdate.setInt(3, id);
            int rowsAffected = pstmtUpdate.executeUpdate();
            if (rowsAffected > 0) {
                return "Modification réussie.";
            } else {
                return "Aucun utilisateur trouvé avec cet ID.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la modification : " + e.getMessage();
        }
    }

    public String supprimerUtilisateur(int id) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cet utilisateur ?");
        if (alert.showAndWait().get() != ButtonType.OK) {
            return "Suppression annulée.";
        }

        try {
            String sql = "DELETE FROM utilisateurs WHERE id=?";
            PreparedStatement pstmt = this.link.getConnexion().prepareStatement(sql);
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Utilisateur supprimé avec succès.";
            } else {
                return "Aucun utilisateur trouvé avec cet ID.";
            }
        } catch (SQLException e) {
            return "Erreur lors de la suppression : " + e.getMessage();
        }
    }

    public ArrayList<Utilisateur> rechercherParEmailOuNom(String recherche) {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            String sql = "SELECT * FROM utilisateurs WHERE email = ? OR nom LIKE ?";
            PreparedStatement pstmt = this.link.getConnexion().prepareStatement(sql);
            pstmt.setString(1, recherche);
            pstmt.setString(2, "%" + recherche + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
        return utilisateurs;
    }

    public String exporterUtilisateursEnCSV(String filePath) {
        ArrayList<Utilisateur> utilisateurs = listerUtilisateurs();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Nom,Email,CreatedAt,UpdatedAt\n");
            for (Utilisateur utilisateur : utilisateurs) {
                writer.append(String.valueOf(utilisateur.getId())).append(",");
                writer.append(utilisateur.getNom()).append(",");
                writer.append(utilisateur.getEmail()).append(",");
                writer.append(utilisateur.getCreatedAt().toString()).append(",");
                writer.append(utilisateur.getUpdatedAt().toString()).append("\n");
            }
            return "Utilisateurs exportés avec succès dans le fichier " + filePath;
        } catch (IOException e) {
            return "Erreur lors de l'exportation : " + e.getMessage();
        }
    }

   
}