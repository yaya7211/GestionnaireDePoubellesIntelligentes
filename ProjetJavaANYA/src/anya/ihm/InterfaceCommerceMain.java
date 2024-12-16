package anya.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import anya.poubelle.EtatProposition;
import anya.poubelle.CentreDeTri;
import anya.poubelle.Commerce;
import anya.poubelle.PropositionPartenariat;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import java.time.LocalDate;

public class InterfaceCommerceMain extends Application {

    private Commerce commerce;
    private ObservableList<PropositionPartenariat> propositions;
    private CentreDeTri centreDeTri;

    // scène principale
    private Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        // création du commerce et du centre de tri
        commerce = new Commerce("Carrefour");
        centreDeTri = new CentreDeTri("Centre de tri de Saint-Germain-En-Laye");

        // données artificielles
        PropositionPartenariat prop1 = new PropositionPartenariat(centreDeTri, 100, 5, commerce);
        PropositionPartenariat prop2 = new PropositionPartenariat(centreDeTri, 150, 10, commerce);
        PropositionPartenariat prop3 = new PropositionPartenariat(centreDeTri, 200, 30, commerce);
        PropositionPartenariat prop4 = new PropositionPartenariat(centreDeTri, 400, 20, commerce);
        PropositionPartenariat prop5 = new PropositionPartenariat(centreDeTri, 140, 60, commerce);
        
        commerce.recevoirPropositionPartenariat(prop1);
        commerce.recevoirPropositionPartenariat(prop2);
        commerce.recevoirPropositionPartenariat(prop3);
        commerce.recevoirPropositionPartenariat(prop4);
        commerce.recevoirPropositionPartenariat(prop5);

        // ObservableList pour la ListView
        propositions = FXCollections.observableArrayList(commerce.getPropositionsEnAttente(0, 10));
        ListView<PropositionPartenariat> listView = new ListView<>(propositions);

        // affichage des éléments de la ListView
        listView.setCellFactory(param -> new ListCell<PropositionPartenariat>() {
            @Override
            protected void updateItem(PropositionPartenariat item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    StringBuilder text = new StringBuilder();
                    text.append("ID: ").append(item.hashCode())
                        .append(" | CentreDeTri: ").append(centreDeTri.getNom())
                        .append(" | État: ").append(item.getEtat())
                        .append(" | Points: ").append(item.getPointsRequis())
                        .append(" | Montant: ").append(item.getMontant());

                    setText(text.toString());
                } else {
                    setText(null);
                }
            }
        });

        // ComboBox pour filtrer par état
        ComboBox<String> filterComboBox = new ComboBox<>();
        
        filterComboBox.getItems().add("Tous les contrats");
        for (EtatProposition etat : EtatProposition.values()) {
            filterComboBox.getItems().add(etat.name());
        }
        filterComboBox.setValue("Tous les contrats");

        // Action de filtrage sur la ComboBox
        filterComboBox.setOnAction(event -> {
            String selectedState = filterComboBox.getValue();
            propositions.clear();
            if (selectedState.equals("Tous les contrats")) {
                for (PropositionPartenariat proposition : commerce.getPropositionsEnAttente(0, 10)) {
                    if (!propositions.contains(proposition)) {
                        propositions.add(proposition);
                    }
                }
            } else {
                EtatProposition etat = EtatProposition.valueOf(selectedState);
                for (PropositionPartenariat proposition : commerce.getPropositionsEnAttente(0, 10)) {
                    if (proposition.getEtat() == etat && !propositions.contains(proposition)) {
                        propositions.add(proposition);
                    }
                }
            }
        });
        
        // filtrage déclenché dès l'ouverture de la page
        appliquerFiltre(filterComboBox.getValue());

        // Bouton pour consulter 
        Button consulterButton = new Button("Consulter");
        consulterButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        consulterButton.setOnAction(event -> {
            if (!listView.getSelectionModel().isEmpty()) {
                PropositionPartenariat selectedProposal = listView.getSelectionModel().getSelectedItem();
                
                if (selectedProposal.getEtat() == EtatProposition.EnAttente) {
                    openConsulterPartenariatEnAttente(selectedProposal, primaryStage, mainScene);
                } else if (selectedProposal.getEtat() == EtatProposition.Accepte) {
                    openConsulterContrat(selectedProposal, primaryStage, mainScene);
                }
            }
        });

        // esthétisme de la page
        HBox buttonBox = new HBox(10, consulterButton);
        buttonBox.setAlignment(Pos.TOP_LEFT); 
        
        VBox vbox = new VBox(10, filterComboBox, listView, buttonBox);
        vbox.setPadding(new javafx.geometry.Insets(10));
        vbox.setStyle("-fx-background-color: #f4f4f4;");
        
        mainScene = new Scene(vbox, 600, 400);  

        primaryStage.setTitle("Propositions de Partenariat");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    // méthode de filtrage
    private void appliquerFiltre(String selectedState) {
        propositions.clear();

        if (selectedState.equals("Tous les contrats")) {
            for (PropositionPartenariat proposition : commerce.getPropositionsEnAttente(0, 10)) {
                if (!propositions.contains(proposition)) {
                    propositions.add(proposition);
                }
            }
        } else {
            EtatProposition etat = EtatProposition.valueOf(selectedState);
            for (PropositionPartenariat proposition : commerce.getPropositionsEnAttente(0, 10)) {
                if (proposition.getEtat() == etat && !propositions.contains(proposition)) {
                    propositions.add(proposition);
                }
            }
        }
    }

    private void openConsulterPartenariatEnAttente(PropositionPartenariat selectedProposal, Stage primaryStage, Scene previousScene) {
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        Text titre = new Text("Émetteur : " + centreDeTri.getNom());
        titre.setStyle("-fx-font-weight: bold;");
        
        Text montantPropose = new Text("Montant proposé : " + selectedProposal.getMontant());
        Text pointsRequis = new Text("Points requis : " + selectedProposal.getPointsRequis());

        Button btnRepondre = new Button("Répondre");
        btnRepondre.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnRepondre.setOnAction(e -> openEntrerInfo(selectedProposal, primaryStage, previousScene));

        Button btnRefuser = new Button("Refuser");
        btnRefuser.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnRefuser.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Refuser cette proposition ?");
            alert.setContentText("Cette action est définitive.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    commerce.refuser(selectedProposal);
                    primaryStage.setScene(previousScene);  
                }
            });
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnRetour.setOnAction(e -> primaryStage.setScene(previousScene));

        layout.getChildren().addAll(titre, montantPropose, pointsRequis, btnRepondre, btnRefuser, btnRetour);
        layout.setAlignment(Pos.CENTER);

        Scene newScene = new Scene(layout, 600, 400);
        primaryStage.setScene(newScene);
    }

    private void openConsulterContrat(PropositionPartenariat selectedProposal, Stage primaryStage, Scene previousScene) {
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        Text titre = new Text("Émetteur : " + centreDeTri.getNom()); 
        Text montantPropose = new Text("Montant proposé : " + selectedProposal.getMontant());  
        Text pointsRequis = new Text("Points requis : " + selectedProposal.getPointsRequis()); 
        Text dateFin = new Text("Date de fin : " + selectedProposal.getDateExpiration());  
        Text categorieProduit = new Text("Catégorie de Produit : " + selectedProposal.getCategorieProduit());  

        Button btnRetour = new Button("Retour");
        btnRetour.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnRetour.setOnAction(e -> primaryStage.setScene(previousScene));

        layout.getChildren().addAll(titre, montantPropose, pointsRequis, categorieProduit, dateFin, btnRetour);
        layout.setAlignment(Pos.TOP_LEFT);

        Scene newScene = new Scene(layout, 600, 400);
        primaryStage.setScene(newScene);
    }

    private void openEntrerInfo(PropositionPartenariat selectedProposal, Stage primaryStage, Scene previousScene) {
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        Text titre = new Text("Titre");

        Label dateLabel = new Label("Date d'expiration");
        DatePicker datePicker = new DatePicker();

        Label categoryLabel = new Label("Catégorie de produit :");
        TextField categoryField = new TextField();

        Button btnConfirmer = new Button("Confirmer");
        btnConfirmer.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnConfirmer.setOnAction(e -> {
            boolean hasErrors = false;
            StringBuilder errorMessage = new StringBuilder();

            if (datePicker.getValue() == null) {
                hasErrors = true;
                errorMessage.append("- La date d'expiration n'est pas sélectionnée.\n");
            }

            if (categoryField.getText().isEmpty()) {
                hasErrors = true;
                errorMessage.append("- Le champ de catégorie de produit est vide.\n");
            }

            if (hasErrors) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("Champs incomplets");
                alert.setContentText(errorMessage.toString());
                alert.showAndWait();
            } else {
                LocalDate selectedDate = datePicker.getValue();
                String selectedCategory = categoryField.getText();
                if (selectedDate.isAfter(LocalDate.now())) {
                    selectedProposal.accepter(selectedDate, selectedCategory);
                    primaryStage.setScene(previousScene);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de date");
                    alert.setHeaderText("Date invalide");
                    alert.setContentText("La date doit être ultérieure à aujourd'hui.");
                    alert.showAndWait();
                }
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnRetour.setOnAction(e -> primaryStage.setScene(previousScene));

        layout.getChildren().addAll(titre, dateLabel, datePicker, categoryLabel, categoryField, btnConfirmer, btnRetour);
        layout.setAlignment(Pos.TOP_LEFT);
        Scene newScene = new Scene(layout, 600, 400);
        primaryStage.setScene(newScene);
    }
}
