package anya.classTest;

import anya.poubelle.BonAchat;
import anya.poubelle.Commerce;
import anya.poubelle.CentreDeTri;
import anya.poubelle.Utilisateur;
import java.time.LocalDate;

public class TestBonAchat {
    public static void main(String[] args) {
        // Initialisation des objets nécessaires
        CentreDeTri centreDeTri = new CentreDeTri("Centre Paris");
        Commerce commerce = new Commerce("Commerce Anya");
        Utilisateur utilisateur = new Utilisateur("Anya", centreDeTri);
        BonAchat bonAchat = new BonAchat(
            100.0,
            50.0,
            LocalDate.now().plusDays(10),
            commerce,
            "Alimentation",
            centreDeTri);

        // Test des getters
        TestFunction.isNonNull(bonAchat.getCentreDeTriMere(), "getCentreDeTriMere");
        TestFunction.areEqual(bonAchat.getMontant(), 50.0, "getMontant");
        TestFunction.areEqual(bonAchat.getPointsRequis(), 100.0, "getPointsRequis");

        // Test de l'état initial
        TestFunction.isType(bonAchat, BonAchat.class, "Création du BonAchat");
        TestFunction.areEqual(bonAchat.estValide(), true, "estValide");

        // Test de réclamation du BonAchat
        bonAchat.reclamePar(utilisateur);
        TestFunction.areEqual(bonAchat.getCentreDeTriMere(), centreDeTri, "réclamation par utilisateur");

        // Test d'utilisation du BonAchat
        boolean utilisationReussie = bonAchat.utiliser();
        TestFunction.areEqual(utilisationReussie, true, "utilisation du BonAchat expire");

        // Test d'un BonAchat expiré
        BonAchat bonAchatExpire = new BonAchat(
            200.0, // Points requis
            75.0,  // Montant
            LocalDate.now().minusDays(1), // Date expirée
            commerce, // Commerce
            "Electronique", // Catégorie produits
            centreDeTri // Centre de tri
        );
        boolean utilisationEchouee = bonAchatExpire.utiliser();
        TestFunction.areEqual(utilisationEchouee, false, "utilisation d'un BonAchat expiré");
        TestFunction.areEqual(bonAchatExpire.estValide(), false, "validité d'un BonAchat expiré");
    }
}
