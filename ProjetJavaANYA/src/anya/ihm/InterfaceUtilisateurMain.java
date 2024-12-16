package anya.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import anya.poubelle.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InterfaceUtilisateurMain extends Application {

    private BorderPane root;
    private List<Commerce> magasinsPartenaires;
    private Utilisateur utilisateur;
    private CentreDeTri centreDeTri;
    private List<Dechet> dechets;

    private static final String CSV_PATH = System.getProperty("user.home") + "/Documents/eclipse-workspace/ProjetJavaANYA/data.csv";

    @Override
    public void start(Stage primaryStage) {
        try {
            chargerDonneesDepuisCSV();

            // Initialisation de l'interface utilisateur
            primaryStage.setTitle("Interface Utilisateur");
            root = new BorderPane();

            // Barre supérieure avec les informations utilisateur
            HBox topBar = new HBox();
            Label labelInfos = new Label("Nom: " + utilisateur.getNom() +
                    " | XP: " + utilisateur.getXP() +
                    " | Points Fidélité: " + utilisateur.getPointsFidelite() +
                    " | Argent: " + utilisateur.getArgentEpargne() + "€");
            topBar.setStyle("-fx-padding: 10;");
            topBar.getChildren().add(labelInfos);
            root.setTop(topBar);

            afficherMenuPrincipal();

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
        dechets = new ArrayList<>();

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

                        int randomIndex = new Random().nextInt(magasinsPartenaires.size());
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
                    
                    case "Dechet":
                        int poid = Integer.parseInt(parts[1].trim());
                        String typeDechetStr = parts[2].trim();
                        TypeDechet typeDechet = TypeDechet.valueOf(typeDechetStr.toLowerCase());
                        Dechet dechet = new Dechet(poid, typeDechet);
                        dechets.add(dechet);
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
    }



    private void afficherMenuPrincipal() {
        VBox centerPanel = new VBox(20);
        centerPanel.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Button btnInterfacePoubelle = new Button("Interface Poubelle");
        Button btnMesBonsAchats = new Button("Mes Bons d'Achats");
        Button btnReclamerBonsAchats = new Button("Réclamer Bons d'Achats");

        btnInterfacePoubelle.setOnAction(e -> afficherInterfacePoubelle());
        btnMesBonsAchats.setOnAction(e -> afficherBonsAchats());
        btnReclamerBonsAchats.setOnAction(e -> afficherReclamerBonAchat());

        centerPanel.getChildren().addAll(btnInterfacePoubelle, btnMesBonsAchats, btnReclamerBonsAchats);
        root.setCenter(centerPanel);
    }

    private void afficherReclamerBonAchat() {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label labelPoints = new Label("Points de fidélité disponibles : " + utilisateur.getPointsFidelite());

        List<BonAchat> bonsDisponibles = utilisateur.consulterBonsAchatDisponibles(0, 100).stream()
                .collect(Collectors.toList());

        ListView<BonAchat> listBonsDisponibles = new ListView<>();
        listBonsDisponibles.getItems().addAll(bonsDisponibles);

        Button btnConvertirPoints = new Button("Convertir Points en Bons d'Achat");
        btnConvertirPoints.setOnAction(e -> {
            BonAchat selectedBon = listBonsDisponibles.getSelectionModel().getSelectedItem();
            if (selectedBon != null && utilisateur.reclamerBonAchat(selectedBon)) {
                bonsDisponibles.remove(selectedBon);
                listBonsDisponibles.getItems().remove(selectedBon);
                labelPoints.setText("Points de fidélité disponibles : " + utilisateur.getPointsFidelite());
                afficherMessage("Bon d'achat réclamé avec succès !");
            } else {
                afficherMessage("Points insuffisants ou aucun bon sélectionné.");
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> afficherMenuPrincipal());

        vbox.getChildren().addAll(labelPoints, listBonsDisponibles, btnConvertirPoints, btnRetour);
        root.setCenter(vbox);
    }

    private void afficherBonsAchats() {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label labelBons = new Label("Nombre de bons d'achat réclamés : " + getBonsReclames().size());

        ComboBox<BonAchat> comboBons = new ComboBox<>();
        comboBons.getItems().addAll(getBonsReclames());

        Button btnSelectBon = new Button("Sélectionner");
        btnSelectBon.setOnAction(e -> {
            BonAchat selectedBon = comboBons.getValue();
            if (selectedBon != null) {
                afficherMessage("Bon d'achat sélectionné : " + selectedBon.getMontant() + "€");
            } else {
                afficherMessage("Veuillez sélectionner un bon d'achat.");
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> afficherMenuPrincipal());

        vbox.getChildren().addAll(labelBons, comboBons, btnSelectBon, btnRetour);
        root.setCenter(vbox);
    }

    private List<BonAchat> getBonsReclames() {
        return utilisateur.getBonsAchat().stream()
                .filter(bon -> bon.getEtat() == EtatBonAchat.RECLAME)
                .collect(Collectors.toList());
    }

    private void afficherInterfacePoubelle() {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Sélection de la poubelle
        Label labelPoubelles = new Label("Sélectionnez une poubelle:");
        ComboBox<PoubelleIntelligente> comboPoubelle = new ComboBox<>();
        comboPoubelle.getItems().addAll(centreDeTri.getPoubelles());

        // Sélection de la couleur du bac
        Label labelCouleur = new Label("Sélectionnez une couleur de bac:");
        ComboBox<String> comboCouleur = new ComboBox<>();
        for (CouleurBac couleur : CouleurBac.values()) {
            comboCouleur.getItems().add(couleur.toString());
        }

        Button btnDeverrouillerPoubelle = new Button("Déverrouiller Poubelle");

        btnDeverrouillerPoubelle.setOnAction(e -> {
            PoubelleIntelligente poubelleSelectionnee = comboPoubelle.getValue();
            String couleurSelectionnee = comboCouleur.getValue();

            if (poubelleSelectionnee != null && couleurSelectionnee != null) {
                if (poubelleSelectionnee.seConnecterEtDeverouiller(utilisateur)) {
                    afficherMessage("Poubelle déverrouillée !");
                    
                    // Passer au terminal pour la sélection des déchets
                    Scanner scanner = new Scanner(System.in);

                    // Afficher la liste des déchets
                    System.out.println("Liste des déchets disponibles :");
                    for (int i = 0; i < dechets.size(); i++) {
                        System.out.println((i + 1) + ". " + dechets.get(i));
                    }

                    // Demander à l'utilisateur de choisir un déchet
                    System.out.println("Entrez le numéro du déchet (1 à " + dechets.size() + "):");
                    int numeroDechet = scanner.nextInt();

                    // Valider le numéro entré
                    if (numeroDechet >= 1 && numeroDechet <= dechets.size()) {
                        Dechet dechetSelectionne = dechets.get(numeroDechet - 1);
                        utilisateur.placerDechetDansPoubelle(dechetSelectionne, poubelleSelectionnee, CouleurBac.valueOf(couleurSelectionnee));

                        System.out.println("Déchet placé avec succès dans la poubelle.");
                    } else {
                        System.out.println("Numéro invalide. Opération annulée.");
                    }
                } else {
                    afficherMessage("Problème : Poubelle pleine ou autre problème.");
                }
            } else {
                afficherMessage("Veuillez sélectionner une poubelle et une couleur.");
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> afficherMenuPrincipal());

        vbox.getChildren().addAll(labelPoubelles, comboPoubelle, labelCouleur, comboCouleur, btnDeverrouillerPoubelle, btnRetour);
        root.setCenter(vbox);
    }


    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
