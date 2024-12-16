package anya.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import anya.poubelle.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InterfaceCentreDeTriAccueil extends Application {

    public static CentreDeTri centreDeTri;  
    private Utilisateur utilisateur;
    private List<Commerce> magasinsPartenaires;
    private List<BonAchat> bonsDisponibles;

    private static final String CSV_PATH = System.getProperty("user.home") + "/Documents/eclipse-workspace/ProjetJavaANYA/data.csv";
    private static final Random RANDOM = new Random();

    @Override
    public void start(Stage primaryStage) {
        try {
            chargerDonneesDepuisCSV();

            // Vérifications post-chargement
            if (centreDeTri == null) {
                throw new IllegalStateException("CentreDeTri non initialisé dans le CSV.");
            }
            if (utilisateur == null) {
                throw new IllegalStateException("Utilisateur non initialisé dans le CSV.");
            }

            // Titre de la fenêtre
            primaryStage.setTitle("Accueil");

            // Création de la VBox pour l'agencement vertical
            VBox root = new VBox(20); // 20px d'espacement entre les éléments
            root.setStyle("-fx-padding: 20; -fx-alignment: center;");

            // Titre principal
            Label title = new Label("ANYA : Centre De Tri");
            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            root.getChildren().add(title);

            // Création des boutons
            Button btnCreerPoubelle = new Button("Créer une poubelle");
            Button btnRenseignerPartenaire = new Button("Renseigner un partenaire potentiel");
            Button btnEtablirContrat = new Button("Etablir un contrat");

            // Ajouter les boutons à la VBox
            root.getChildren().addAll(btnCreerPoubelle, btnRenseignerPartenaire, btnEtablirContrat);

            // Configurer les actions des boutons
            btnCreerPoubelle.setOnAction(e -> {
                // Lancer l'interface Centre De Tri New Poubelle sans redémarrer l'application
                InterfaceCentreDeTriNewPoubelle interfacePoubelle = new InterfaceCentreDeTriNewPoubelle();
                interfacePoubelle.start(new Stage());  // Ouvrir dans une nouvelle fenêtre
                primaryStage.close(); // Fermer l'interface d'accueil après le lancement
            });

            btnRenseignerPartenaire.setOnAction(e -> {
                // Lancer l'interface Centre De Tri New Partenaire sans redémarrer l'application
                InterfaceCentreDeTriNewPartenaire interfacePartenaire = new InterfaceCentreDeTriNewPartenaire();
                interfacePartenaire.start(new Stage());  // Ouvrir dans une nouvelle fenêtre
                primaryStage.close(); // Fermer l'interface d'accueil après le lancement
            });

            btnEtablirContrat.setOnAction(e -> {
                // Lancer l'interface Proposition Contrat sans redémarrer l'application
                InterfacePropositionContrat interfaceContrat = new InterfacePropositionContrat();
                interfaceContrat.start(new Stage());  // Ouvrir dans une nouvelle fenêtre
                primaryStage.close(); // Fermer l'interface d'accueil après le lancement
            });

            // Création de la scène
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            afficherMessage("Erreur lors du chargement des données : " + e.getMessage());
        }
    }

    private void chargerDonneesDepuisCSV() throws IOException {
        centreDeTri = null;
        utilisateur = null;
        magasinsPartenaires = new ArrayList<>();
        bonsDisponibles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;

            // Lire et ignorer la première ligne (en-têtes)
            line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String type = parts[0].trim(); // Type de l'objet

                switch (type) {
                    case "CentreDeTri":
                        String nomCentre = parts[1].trim();
                        centreDeTri = new CentreDeTri(nomCentre);
                        break;

                    case "Utilisateur":
                        String nomUtilisateur = parts[1].trim();
                        int pointsFidelite = Integer.parseInt(parts[2].trim());
                        int argentEpargne = Integer.parseInt(parts[3].trim());
                        int xp = Integer.parseInt(parts[4].trim());
                        utilisateur = new Utilisateur(nomUtilisateur, centreDeTri);
                        utilisateur.setPointsFidelite(pointsFidelite);
                        utilisateur.setArgentEpargne(argentEpargne);
                        utilisateur.setXP(xp);
                        break;

                    case "Commerce":
                        String nomCommerce = parts[1].trim();
                        Commerce commerce = new Commerce(nomCommerce);
                        magasinsPartenaires.add(commerce);
                        centreDeTri.ajouterPartenairePotentiel(commerce);
                        break;

                    case "BonAchat":
                        double montant = Double.parseDouble(parts[1].trim());
                        double reduction = Double.parseDouble(parts[2].trim());
                        String dateString = parts[3].trim();
                        LocalDate dateExpiration;

                        try {
                            dateExpiration = LocalDate.parse(dateString);
                        } catch (DateTimeParseException e) {
                            throw new IllegalArgumentException("Date invalide : " + dateString, e);
                        }
                        String categorie = parts[4].trim();

                        if (magasinsPartenaires.isEmpty()) {
                            throw new IllegalStateException("Aucun Commerce disponible pour associer un BonAchat.");
                        }

                        int randomIndex = RANDOM.nextInt(magasinsPartenaires.size());
                        Commerce commerceAssocie = magasinsPartenaires.get(randomIndex);
                        BonAchat bonAchat = new BonAchat(montant, reduction, dateExpiration, commerceAssocie, categorie, centreDeTri);
                        centreDeTri.recevoirAcceptationPropositionPatrenariat(bonAchat);
                        break;

                    case "PoubelleIntelligente":
                        double latitude = Double.parseDouble(parts[1].trim());
                        double longitude = Double.parseDouble(parts[2].trim());
                        String nomPoubelle = parts[3].trim();
                        PoubelleIntelligente poubelle = new PoubelleIntelligente(latitude, longitude, centreDeTri, nomPoubelle);
                        break;

                    default:
                        System.err.println("Type inconnu dans le fichier CSV : " + type);
                }
            }
        }

        if (centreDeTri == null) {
            throw new IllegalStateException("CentreDeTri non initialisé dans le CSV.");
        }
        if (utilisateur == null) {
            throw new IllegalStateException("Utilisateur non initialisé dans le CSV.");
        }

        // Optionnel: Charger les bons disponibles si nécessaire
        // bonsDisponibles = centreDeTri.consulterBonsAchatsDisponibles(0, 10);
    }

    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
