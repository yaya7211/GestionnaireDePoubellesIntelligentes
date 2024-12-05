package anya.poubelle;

import anya.poubelle.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManualTest {
    public static void main(String[] args) {
        System.out.println("Début du test manuel.");

        // Création du centre de tri
        CentreDeTri centreDeTri = new CentreDeTri("Centre de tri de Saint-Germain-En-Laye");
        System.out.println("Centre de tri créé : " + centreDeTri.getNom());

        // Création de la poubelle intelligente
        PoubelleIntelligente poubelle = new PoubelleIntelligente(48.90058, 2.07033, centreDeTri);
        System.out.println("Poubelle intelligente créée à la position : (" + poubelle.toStringCoordonnees() + ")");
        centreDeTri.setPointsPourCouleur(CouleurBac.VERTE, 10.0);

        // Création d'un commerce partenaire potentiel
        Commerce supermarche = new Commerce("Carrefour");
        System.out.println("Commerce créé : " + supermarche.getNom());
        try {
            centreDeTri.ajouterPartenairePotentiel(supermarche);
            System.out.println("Commerce ajouté comme partenaire potentiel.");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout du commerce comme partenaire potentiel : " + e.getMessage());
        }

        // Création d'un utilisateur
        Utilisateur user = new Utilisateur("Yassine", centreDeTri);
        System.out.println("Utilisateur créé : " + user.getNom());

        // Création d'un déchet
        Dechet dechet = new Dechet(0.5, TypeDechet.verre);
        System.out.println("Déchet créé : " + dechet.getType() + " avec un poids de " + dechet.getPoid() + " kg");

        // Connexion de l'utilisateur et déverrouillage de la poubelle
        System.out.println("Avant le jet, le nombre de points de fidélités de l'utilisateur est " + user.getPointsFidelite() + " points.");

        
        try {
            user.seConnecterEtDeverouiller(poubelle);
            System.out.println("Utilisateur connecté et poubelle déverrouillée.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion ou du déverrouillage de la poubelle : " + e.getMessage());
        }

        // Placement du déchet dans la poubelle
        boolean dechetPlacé = user.placerDechetDansPoubelle(dechet, poubelle, CouleurBac.VERTE);
        if (dechetPlacé) {
            System.out.println("Jet de déchet a réussi.");
        } else {
            System.out.println("Jet de déchet a échoué.");
        }
        
        System.out.println("Après le jet, le nombre de points de fidélités de l'utilisateur est " + user.getPointsFidelite() + " points.");

        // Proposer un partenariat
        try {
            centreDeTri.proposerPartenariat(supermarche, 10, 5);
            System.out.println("Proposition de partenariat envoyée.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la proposition de partenariat : " + e.getMessage());
        }

        // Gestion des propositions de partenariat
        try {
            ArrayList<PropositionPartenariat> propositions = supermarche.getPropositionsEnAttente(0, 10);
            System.out.println("Propositions reçues : " + propositions.size());
            if (!propositions.isEmpty()) {
                System.out.println("Acceptation de la première proposition.");
                supermarche.accepter(propositions.get(0), LocalDate.now().plusDays(5));
                if (propositions.size() > 1) {
                    System.out.println("Refus de la deuxième proposition.");
                    propositions.get(1).refuser();
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la gestion des propositions : " + e.getMessage());
        }
        
        System.out.println("Avant la réclamation, le nombre de points de fidélités de l'utilisateur est " + user.getPointsFidelite() + " points.");

        // Consultation des bons d'achat disponibles
        try {
            ArrayList<BonAchat> bons = user.consulterBonsAchatDisponibles(0, 10);
            System.out.println("Bons d'achat disponibles : " + bons.size());
            if (!bons.isEmpty()) {
                System.out.println("Tentative de réclamation du premier bon.");
                if (user.reclamerBonAchat(bons.get(0))) {
                    System.out.println("L'utilisateur a réclamé son bon d'achat.");
                } else {
                    System.out.println("Le bon d'achat n'a pas pu être réclamé.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la consultation ou de la réclamation des bons d'achat : " + e.getMessage());
        }
        
        System.out.println("Après la réclamation, le nombre de points de fidélités de l'utilisateur est " + user.getPointsFidelite() + " points.");


        System.out.println("Fin du test manuel.");
    }
}
