package anya.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import anya.poubelle.CentreDeTri;
import anya.poubelle.Commerce;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class InterfaceCentreDeTriNewPartenaire extends Application {
	
	ObservableList<String> ListPartenaire = FXCollections.observableArrayList();
	
	 public void start(Stage primaryStage) {
	        // Configurer la fenêtre principale
	        primaryStage.setTitle("Enregistrer un nouveau partenaire");

	        // Créer un VBox pour contenir les éléments
	        VBox root = new VBox(10); // 10px d'espace entre chaque élément
	        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
	        
	        // Ajouter un menu
	        MenuBar menuBar = createMenuBar(primaryStage);
	        root.getChildren().add(menuBar);

	        // Ajouter un titre
	        Label title = new Label("Enregistrer un nouveau partenaire potentiel");
	        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	        root.getChildren().add(title);

	        // Ajouter le champ de texte et son label
	        HBox inputRow = new HBox(10); // 10px d'espace entre le label et le champ de texte
	        Label labelNom = new Label("Nom :");
	        TextField textFieldNom = new TextField();
	        textFieldNom.setPromptText("Entrez le nom du partenaire");
	        inputRow.getChildren().addAll(labelNom, textFieldNom);
	        inputRow.setStyle("-fx-alignment: center;");
	        root.getChildren().add(inputRow);

	        // Ajouter le bouton "Enregistrer"
	        Button boutonEnregistrer = new Button("Enregistrer");
	        root.getChildren().add(boutonEnregistrer);

	        // Configurer l'action du bouton "Enregistrer"
	        boutonEnregistrer.setOnAction(e -> {
	            String nomPartenaire = textFieldNom.getText().trim();
		        ListPartenaire.add(textFieldNom.getText()); // Récupérer le texte saisi

	            if (!nomPartenaire.isEmpty()) {
	                // Créer une nouvelle instance de Commerce
	                Commerce p = new Commerce(nomPartenaire);
	                InterfaceCentreDeTriAccueil.centreDeTri.ajouterPartenairePotentiel(p);  // Ajouter à l'instance unique


	                // Afficher une confirmation
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Succès");
	                alert.setHeaderText(null);
	                alert.setContentText("Le partenaire \"" + nomPartenaire + "\" a été enregistré avec succès.");
	                alert.showAndWait();

	                // Réinitialiser le champ de texte
	                textFieldNom.clear();
	            } else {
	                // Afficher un message d'erreur si le champ est vide
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Erreur");
	                alert.setHeaderText(null);
	                alert.setContentText("Veuillez entrer un nom pour le partenaire.");
	                alert.showAndWait();
	            }
	        });

	        // Ajouter un bouton pour afficher les partenaires potentiels
	        Button boutonAfficher = new Button("Afficher partenaires potentiels");
	        root.getChildren().add(boutonAfficher);
	        
	        // Configurer l'action du bouton "Afficher partenaires potentiels"
	        boutonAfficher.setOnAction(e -> afficherPartenairesPotentiels(InterfaceCentreDeTriAccueil.centreDeTri));

	        // Configurer la scène et afficher la fenêtre
	        Scene scene = new Scene(root, 600, 400);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    // Méthode pour afficher la liste des partenaires potentiels
	    private void afficherPartenairesPotentiels(CentreDeTri centreDeTri) {
	        // Créer une fenêtre pour afficher les partenaires potentiels
	        Stage stage = new Stage();
	        stage.setTitle("Partenaires potentiels");

	        VBox vbox = new VBox(10);
	        vbox.setStyle("-fx-padding: 20;");

	        ArrayList<Commerce> partenaires = centreDeTri.getPartenairesPotentiels();

	        // Vérifier si la liste de commerce est vide
	        if (partenaires.isEmpty()) {
	            Label label = new Label("Aucun partenaire potentiel enregistré pour le moment.");
	            vbox.getChildren().add(label);
	        } else {
	            Label label = new Label("Liste des partenaires potentiels :");
	            vbox.getChildren().add(label);

	            // Ajouter les noms des partenaires dans une liste
	            ListView<String> listView = new ListView<>();
	            for (Commerce partenaire : partenaires) {
	                listView.getItems().add(partenaire.getNom());
	            }
	            vbox.getChildren().add(listView);
	        }

	        Scene scene = new Scene(vbox, 600, 400);
	        stage.setScene(scene);
	        stage.show();
	    }

	    private MenuBar createMenuBar(Stage stage) {
	        MenuBar menuBar = new MenuBar();

	        Menu menuNavigation = new Menu("Menu");

	        MenuItem goToPropositionContrat = new MenuItem("Proposer un contrat");
	        goToPropositionContrat.setOnAction(e -> {
	            InterfacePropositionContrat propositionContrat = new InterfacePropositionContrat();
	            propositionContrat.start(stage);
	        });

	        MenuItem goToNewPoubelle = new MenuItem("Créer une nouvelle poubelle");
	        goToNewPoubelle.setOnAction(e -> {
	            InterfaceCentreDeTriNewPoubelle newPoubelle = new InterfaceCentreDeTriNewPoubelle();
	            newPoubelle.start(stage);
	        });

	        menuNavigation.getItems().addAll(goToPropositionContrat, goToNewPoubelle);
	        menuBar.getMenus().add(menuNavigation);

	        return menuBar;
	    }
	    public static void main(String[] args) {
	        Application.launch(args);
	    }
}