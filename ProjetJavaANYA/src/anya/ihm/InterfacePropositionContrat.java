package anya.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import anya.poubelle.Commerce;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InterfacePropositionContrat extends Application {

    public void start(Stage primaryStage) {

        // Configurer la fenêtre principale
        primaryStage.setTitle("Proposition de Partenariat");

        // Créer un VBox pour contenir les éléments
        VBox root = new VBox(10); // 10px d'espace entre les éléments
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        
     // Ajouter un menu
        MenuBar menuBar = createMenuBar(primaryStage);
        root.getChildren().add(menuBar);

        // Ajouter un titre
        Label title = new Label("Proposition de Partenariat");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        root.getChildren().add(title);

        // Menu déroulant pour les commerces
        Label labelCommerce = new Label("Sélectionnez un commerce :");
        ComboBox<Commerce> comboBoxCommerces = new ComboBox<>();
        ArrayList<Commerce> partenairesPotentiels = InterfaceCentreDeTriAccueil.centreDeTri.getPartenairesPotentiels(); // Liste des commerces
        comboBoxCommerces.getItems().addAll(partenairesPotentiels); // Ajouter les objets Commerce directement
        root.getChildren().addAll(labelCommerce, comboBoxCommerces);

        // Champ pour entrer le prix
        Label labelPrix = new Label("Valeur du bon d'achat (en euros) :");
        TextField textFieldPrix = new TextField();
        textFieldPrix.setPromptText("Entrez le prix proposé");
        root.getChildren().addAll(labelPrix, textFieldPrix);

        // Champ pour entrer le nombre de points
        Label labelPoints = new Label("Nombre de points nécessaire pour l'acquisition du bon:");
        TextField textFieldPoints = new TextField();
        textFieldPoints.setPromptText("Entrez le nombre de points");
        root.getChildren().addAll(labelPoints, textFieldPoints);

        // Bouton "Proposer"
        Button boutonProposer = new Button("Proposer");
        root.getChildren().add(boutonProposer);

        // Configurer l'action du bouton "Proposer"
        boutonProposer.setOnAction(e -> {
            Commerce commerceSelectionne = comboBoxCommerces.getValue(); // Commerce sélectionné
            String prixTexte = textFieldPrix.getText().trim(); // Texte dans le champ de prix
            String pointsTexte = textFieldPoints.getText().trim(); // Texte dans le champ de points

            if (commerceSelectionne != null && !prixTexte.isEmpty() && !pointsTexte.isEmpty()) {
                try {
                    // Convertir le prix et les points en double
                    double prix = Double.parseDouble(prixTexte);
                    double points = Double.parseDouble(pointsTexte);

                    // Appeler la méthode proposerPartenariat
                    InterfaceCentreDeTriAccueil.centreDeTri.proposerPartenariat(commerceSelectionne, prix, points);

                    // Afficher une confirmation
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Partenariat proposé avec succès au commerce \"" + commerceSelectionne.getNom() + "\".");
                    alert.showAndWait();

                    // Réinitialiser les champs de texte et le menu déroulant
                    comboBoxCommerces.setValue(null);
                    textFieldPrix.clear();
                    textFieldPoints.clear();
                } catch (NumberFormatException ex) {
                    // Afficher un message d'erreur si les conversions échouent
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez entrer des valeurs numériques valides pour le prix et les points.");
                    alert.showAndWait();
                }
            } else {
                // Afficher un message d'erreur si un champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un commerce et entrer un prix ainsi qu'un nombre de points.");
                alert.showAndWait();
            }
        });

        // Configurer la scène et afficher la fenêtre
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu menuNavigation = new Menu("Navigation");

        MenuItem goToNewPoubelle = new MenuItem("Créer une nouvelle Poubelle");
        goToNewPoubelle.setOnAction(e -> {
            InterfaceCentreDeTriNewPoubelle newPoubelle = new InterfaceCentreDeTriNewPoubelle();
            newPoubelle.start(stage);
        });

        MenuItem goToNewPartenaire= new MenuItem("Renseigner un nouveau partenaire potentiel");
        goToNewPartenaire.setOnAction(e -> {
            InterfaceCentreDeTriNewPartenaire newPartenaire = new InterfaceCentreDeTriNewPartenaire();
            newPartenaire.start(stage);
        });

        menuNavigation.getItems().addAll(goToNewPoubelle, goToNewPartenaire);
        menuBar.getMenus().add(menuNavigation);

        return menuBar;
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}