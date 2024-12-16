package anya.classTest;

import anya.poubelle.BacSpecialise;
import anya.poubelle.BonAchat;
import anya.poubelle.CentreDeTri;
import anya.poubelle.Commerce;
import anya.poubelle.CouleurBac;
import anya.poubelle.Dechet;
import anya.poubelle.PoubelleIntelligente;
import anya.poubelle.PropositionPartenariat;
import anya.poubelle.TypeDechet;
import anya.poubelle.Utilisateur;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestCentreDeTri {
    public static void main(String[] args) {
        CentreDeTri centre = new CentreDeTri("CentreTest");
        PoubelleIntelligente poubelle = new PoubelleIntelligente(10.1, 1.10, centre, "Poubelle dehors");
        BacSpecialise bac = new BacSpecialise(CouleurBac.BLEUE, poubelle, 1);
        Utilisateur utilisateur = new Utilisateur("User", centre);
        Commerce commerce = new Commerce("Partenaire");
        Dechet dechet = new Dechet(45, TypeDechet.papier);

        TestFunction.areEqual(centre.getNom(), "CentreTest", "Vérification du nom du centre");

        centre.ajouterPartenairePotentiel(commerce);
        ArrayList<Commerce> partenairesPotentiels = centre.getPartenairesPotentiels();
        TestFunction.areEqual(partenairesPotentiels.contains(commerce), true, "Ajout d'un partenaire potentiel");

        centre.ajouterUtilisateur(utilisateur);
        TestFunction.areEqual(centre.getPoubelles().get(0), poubelle, "Vérification des poubelles initiales");

        centre.ajouterPoubelle(poubelle);
        TestFunction.areEqual(centre.getPoubelles().get(1), poubelle, "Ajout d'une poubelle");

        centre.demanderVidage(poubelle);
        TestFunction.areEqual(centre.getPoubellesAVider().contains(poubelle), true, "Demande de vidage d'une poubelle");

        centre.setPointsPourCouleur(CouleurBac.BLEUE, 10.0);
        double score = centre.demanderScoreParUnite(CouleurBac.BLEUE, true);
        TestFunction.areEqual(score, 10.0, "Modification et récupération des points pour une couleur de bac");

        centre.recevoirAcceptationPropositionPatrenariat(new BonAchat(50, 5, LocalDate.now(), commerce, "produit", centre));
        ArrayList<BonAchat> bons = centre.consulterBonsAchatsDisponibles(0, 1);
        TestFunction.areEqual(bons.size(), 1, "Consultation des bons d'achat disponibles");

        BonAchat bon = new BonAchat(50, 5, LocalDate.now(), commerce, "produit", centre);
        centre.signalerChangementEtatBonAchat(bon);
        TestFunction.areEqual(centre.consulterBonsAchatsDisponibles(0, 1).contains(bon), false, "Signalement de changement d'état du bon d'achat");

        centre.proposerPartenariat(commerce, 100.0, 200.0);
        TestFunction.isNonNull(commerce, "Proposition de partenariat");

        PropositionPartenariat proposition = new PropositionPartenariat(centre, 100.0, 200.0, commerce);
        centre.recevoirRefusPropositionPartenariat(proposition);
        TestFunction.areEqual(centre.getPropositionRefusees().contains(proposition), true, "Réception d'un refus de partenariat");
    }
}
