package anya.classTest;

import java.time.LocalDate;

import anya.poubelle.CentreDeTri;
import anya.poubelle.Commerce;
import anya.poubelle.EtatProposition;
import anya.poubelle.PropositionPartenariat;

public class TestPropositionPartenariat {
    public static void main(String[] args) {
        CentreDeTri centre = new CentreDeTri("Centre Test");
        Commerce commerce = new Commerce("Commerce Test");

        PropositionPartenariat proposition = new PropositionPartenariat(centre, 150.0, 300.0, commerce);
        TestFunction.isNonNull(proposition, "proposition");
        TestFunction.areEqual(proposition.getCentreDeTri(), centre, "Vérification du centre de tri");
        TestFunction.areEqual(proposition.getMontant(), 150.0, "Vérification du montant initial");
        TestFunction.areEqual(proposition.getEtat(), EtatProposition.EnAttente, "Vérification de l'état initial");

        LocalDate expirationDate = LocalDate.now().plusDays(30);
        String categorieProduit = "Électronique";
        TestFunction.isNonNull(expirationDate, "dateExpiration après initialisation");
        
        proposition.accepter(expirationDate, categorieProduit);
        TestFunction.areEqual(proposition.getEtat(), EtatProposition.Accepte, "Vérification de l'état après acceptation");
        TestFunction.isNonNull(proposition.getCentreDeTri(), "Vérification de la réception du bon d'achat au centre de tri");

        PropositionPartenariat proposition2 = new PropositionPartenariat(centre, 200.0, 400.0, commerce);
        TestFunction.isNonNull(proposition2, "proposition2");
        proposition2.refuser();
        TestFunction.areEqual(proposition2.getEtat(), EtatProposition.Refuse, "Vérification de l'état après refus");
        TestFunction.isType(proposition2, PropositionPartenariat.class, "Type après création et refus");

        System.out.println("[SUCS] Tous les tests ont été exécutés.");
    }
}
