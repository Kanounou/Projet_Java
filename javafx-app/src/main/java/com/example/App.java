package com.example;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
    private Gestion_utilisateur gu;
    private TextArea textArea;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Connexion link = new Connexion("jdbc:mysql://localhost:3306/tp_java", "root", "");
    
        gu = new Gestion_utilisateur(link);
        menuPrincipal();
    }

    private void menuPrincipal() {
        Label label = new Label("Gestion des Utilisateurs");

        TableView<Utilisateur> tableView = new TableView<>();

        // Créer les colonnes
        TableColumn<Utilisateur, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Utilisateur, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Utilisateur, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Utilisateur, LocalDateTime> createdAtCol = new TableColumn<>("Créé le");
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Utilisateur, LocalDateTime> updatedAtCol = new TableColumn<>("Mis à jour le");
        updatedAtCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        // Ajouter les colonnes au TableView
        tableView.getColumns().addAll(idCol, nomCol, emailCol, createdAtCol, updatedAtCol);

        Button listButton = new Button("Lister Utilisateurs");
        listButton.setOnAction(event -> {
            ArrayList<Utilisateur> utilisateurs = gu.listerUtilisateurs();
            ObservableList<Utilisateur> utilisateursObservable = FXCollections.observableArrayList(utilisateurs);
            tableView.setItems(utilisateursObservable);
        });

        Button addButton = new Button("Ajouter Utilisateur");
        addButton.setOnAction(event -> ajouterUtilisateur());

        Button updateButton = new Button("Modifier Utilisateur");
        updateButton.setOnAction(event -> demanderIdUtilisateur());

        Button deleteButton = new Button("Supprimer Utilisateur");
        deleteButton.setOnAction(event -> supprimerUtilisateur());

        Button searchButton = new Button("Rechercher Utilisateur par Email ou Nom");
        searchButton.setOnAction(event -> rechercherUtilisateur());

        Button exportButton = new Button("Exporter Utilisateurs en CSV");
        exportButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                textArea.setText(gu.exporterUtilisateursEnCSV(file.getAbsolutePath()));
            }
        });

        Button quitButton = new Button("Quitter");
        quitButton.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de sortie");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment quitter ?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                primaryStage.close();
            }
        });

        VBox layout = new VBox(10, label, addButton, listButton, updateButton, deleteButton, searchButton, exportButton, quitButton, tableView);
        Scene scene = new Scene(layout, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setTitle("Gestion des Utilisateurs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void ajouterUtilisateur() {
        Label label = new Label("Ajouter un Utilisateur");
        TextField nameField = new TextField();
        nameField.setPromptText("Nom de l'utilisateur");
    
        TextField emailField = new TextField();
        emailField.setPromptText("Email de l'utilisateur");
    
        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (name.isEmpty() || email.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            } else {
                Utilisateur utilisateur = new Utilisateur(0, name, email, LocalDateTime.now(), LocalDateTime.now());
                textArea.setText(gu.ajouterUtilisateur(utilisateur));
                afficherMessageConfirmation("Utilisateur ajouté avec succès.");
                menuPrincipal();
            }

            emailField.setText("");
            nameField.clear();
        });
    
        Button backButton = new Button("Retour");
        backButton.setOnAction(event -> menuPrincipal());
    
        VBox layout = new VBox(10, label, nameField, emailField, saveButton, backButton);
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void demanderIdUtilisateur() {
        Label label = new Label("Modifier un Utilisateur");
        TextField idField = new TextField();
        idField.setPromptText("ID de l'utilisateur");

        Button nextButton = new Button("Suivant");
        nextButton.setOnAction(event -> {
            String id = idField.getText();
            if (id.isEmpty()) {
                textArea.setText("Veuillez entrer l'ID de l'utilisateur à modifier.");
            } else {
                modifierUtilisateur(Integer.parseInt(id));
            }
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(event -> menuPrincipal());

        VBox layout = new VBox(10, label, idField, nextButton, backButton);
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void modifierUtilisateur(int id) {
        Label label = new Label("Modifier un Utilisateur");
        TextField nameField = new TextField();
        nameField.setPromptText("Nouveau Nom");

        TextField emailField = new TextField();
        emailField.setPromptText("Nouvel Email");

        Button updateButton = new Button("Modifier");
        updateButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (name.isEmpty() || email.isEmpty()) {
                textArea.setText("Veuillez remplir tous les champs.");
            } else {
                textArea.setText(gu.modifierUtilisateur(id, name, email));
                afficherMessageConfirmation("Utilisateur modifié avec succès.");
                menuPrincipal();
            }
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(event -> menuPrincipal());

        VBox layout = new VBox(10, label, nameField, emailField, updateButton, backButton);
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void supprimerUtilisateur() {
        Label label = new Label("Supprimer un Utilisateur");
        TextField idField = new TextField();
        idField.setPromptText("ID de l'utilisateur");

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> {
            String id = idField.getText();
            if (id.isEmpty()) {
                textArea.setText("Veuillez entrer l'ID de l'utilisateur à supprimer.");
            } else {
                textArea.setText(gu.supprimerUtilisateur(Integer.parseInt(id)));
                afficherMessageConfirmation("Utilisateur supprimé avec succès.");
                menuPrincipal();
            }
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(event -> menuPrincipal());

        VBox layout = new VBox(10, label, idField, deleteButton, backButton);
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void rechercherUtilisateur() {
        Label label = new Label("Rechercher un Utilisateur par Email ou Nom");
        TextField searchField = new TextField();
        searchField.setPromptText("Entrez un email ou un nom");

        TableView<Utilisateur> tableView = new TableView<>();
        tableView.setVisible(false); // Masquer le tableau au départ

        // Créer les colonnes
        TableColumn<Utilisateur, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Utilisateur, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Utilisateur, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Utilisateur, LocalDateTime> createdAtCol = new TableColumn<>("Créé le");
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Utilisateur, LocalDateTime> updatedAtCol = new TableColumn<>("Mis à jour le");
        updatedAtCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        // Ajouter les colonnes au TableView
        tableView.getColumns().addAll(idCol, nomCol, emailCol, createdAtCol, updatedAtCol);

        // Mettre à jour les suggestions en fonction de la saisie
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                tableView.setVisible(false);
            } else {
                ArrayList<Utilisateur> utilisateurs = gu.rechercherParEmailOuNom(newValue);
                ObservableList<Utilisateur> suggestions = FXCollections.observableArrayList(utilisateurs);
                tableView.setItems(suggestions);
                tableView.setVisible(true);
            }
        });

        Button searchButton = new Button("Rechercher");
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText();
            if (searchTerm.isEmpty()) {
                textArea.setText("Veuillez entrer un email ou un nom.");
            } else {
                ArrayList<Utilisateur> utilisateurs = gu.rechercherParEmailOuNom(searchTerm);
                ObservableList<Utilisateur> suggestions = FXCollections.observableArrayList(utilisateurs);
                tableView.setItems(suggestions);
                tableView.setVisible(true);
            }
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(event -> menuPrincipal());

        VBox layout = new VBox(10, label, searchField, tableView, searchButton, backButton);
        Scene scene = new Scene(layout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void afficherMessageConfirmation(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}