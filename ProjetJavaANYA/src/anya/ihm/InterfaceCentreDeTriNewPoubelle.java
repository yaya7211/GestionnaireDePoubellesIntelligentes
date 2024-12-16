// InterfaceCentreDeTriNewPoubelle.java
package anya.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import anya.poubelle.CentreDeTri;
import anya.poubelle.PoubelleIntelligente;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InterfaceCentreDeTriNewPoubelle extends Application {

	public void start(Stage primaryStage) {

        // Créer un VBox pour contenir les éléments
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Ajouter un menu
        MenuBar menuBar = createMenuBar(primaryStage);
        root.getChildren().add(menuBar);

        // Ajouter un titre
        Label title = new Label("Enregistrer une nouvelle Poubelle");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        root.getChildren().add(title);

        // Ajouter les champs pour longitude et latitude
        HBox inputRow = new HBox(10);
        Label labelLongitude = new Label("Longitude :");
        TextField textFieldLongitude = new TextField();
        textFieldLongitude.setPromptText("Entrez une longitude");
        Label labelLatitude = new Label("Latitude :");
        TextField textFieldLatitude = new TextField();
        textFieldLatitude.setPromptText("Entrez une latitude");
        Label labelNom = new Label("Nom :");
        TextField textFieldNom = new TextField();
        textFieldLatitude.setPromptText("Entrez un nom");

        inputRow.getChildren().addAll(labelLongitude, textFieldLongitude, labelLatitude, textFieldLatitude, labelNom, textFieldNom);
        inputRow.setStyle("-fx-alignment: center;");
        root.getChildren().add(inputRow);

        // Bouton "Installer"
        Button boutonInstaller = new Button("Installer");
        root.getChildren().add(boutonInstaller);

        boutonInstaller.setOnAction(e -> {
            String longitudePoubelle = textFieldLongitude.getText().trim();
            String latitudePoubelle = textFieldLatitude.getText().trim();
            String nom = textFieldNom.getText().trim();

            if (!latitudePoubelle.isEmpty() && !longitudePoubelle.isEmpty()) {
                try {
                    double latitude = Double.parseDouble(latitudePoubelle);
                    double longitude = Double.parseDouble(longitudePoubelle);

                    PoubelleIntelligente poubelle = new PoubelleIntelligente(latitude, longitude, InterfaceCentreDeTriAccueil.centreDeTri, nom);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("La poubelle a été enregistrée avec succès.");
                    alert.showAndWait();

                    System.out.println("Ajout de la poubelle à " + longitudePoubelle + ", " + latitudePoubelle);
                    
                    textFieldLongitude.clear();
                    textFieldLatitude.clear();
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez entrer des valeurs numériques valides pour la longitude et la latitude.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer la longitude et la latitude de la poubelle.");
                alert.showAndWait();
            }
        });

        // Bouton pour afficher la liste des poubelles
        Button boutonAfficherListe = new Button("Afficher la liste des poubelles");
        boutonAfficherListe.setOnAction(e -> afficherListePoubelles(InterfaceCentreDeTriAccueil.centreDeTri));

        root.getChildren().add(boutonAfficherListe);

        // Configurer la scène
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Enregistrer une Poubelle");
        primaryStage.show();
    }

    // Méthode pour afficher la liste des poubelles
    private void afficherListePoubelles(CentreDeTri centreDeTri) {
        Stage listeStage = new Stage();
        listeStage.setTitle("Liste des Poubelles");

        // Créer un VBox pour afficher la liste des poubelles
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Récupérer la liste des poubelles à partir du CentreDeTri
        ArrayList<PoubelleIntelligente> poubellesList = centreDeTri.getPoubelles();

        // Si la liste est vide
        if (poubellesList.isEmpty()) {
            vbox.getChildren().add(new Label("Aucune poubelle enregistrée."));
        } else {
            // Ajouter chaque poubelle à la liste
            for (PoubelleIntelligente poubelle : poubellesList) {
                vbox.getChildren().add(new Label("Poubelle à " + poubelle.toStringCoordonnees()));
            }
        }

        // Créer la scène pour afficher la liste
        Scene scene = new Scene(vbox, 1000, 400);
        listeStage.setScene(scene);
        listeStage.show();
    }
    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu menuNavigation = new Menu("Menu");

        MenuItem goToPropositionContrat = new MenuItem("Proposer un Contrat");
        goToPropositionContrat.setOnAction(e -> {
            InterfacePropositionContrat propositionContrat = new InterfacePropositionContrat();
            propositionContrat.start(stage);
        });

        MenuItem goToNewPartenaire= new MenuItem("Renseigner un nouveau partenaire potentiel");
        goToNewPartenaire.setOnAction(e -> {
            InterfaceCentreDeTriNewPartenaire newPartenaire = new InterfaceCentreDeTriNewPartenaire();
            newPartenaire.start(stage);
        });

        menuNavigation.getItems().addAll(goToPropositionContrat, goToNewPartenaire);
        menuBar.getMenus().add(menuNavigation);

        return menuBar;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
