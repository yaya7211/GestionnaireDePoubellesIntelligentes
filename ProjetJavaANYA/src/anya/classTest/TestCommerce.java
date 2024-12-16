package anya.classTest;

import java.time.LocalDate;

import anya.poubelle.BacSpecialise;
import anya.poubelle.CentreDeTri;
import anya.poubelle.Commerce;
import anya.poubelle.CouleurBac;
import anya.poubelle.Dechet;
import anya.poubelle.PoubelleIntelligente;
import anya.poubelle.PropositionPartenariat;
import anya.poubelle.TypeDechet;
import anya.poubelle.Utilisateur;

public class TestCommerce {
    public static void main(String[] args) {
        Commerce commerce = new Commerce("Supermarché Test");
        CentreDeTri centre = new CentreDeTri("Centre");
        PropositionPartenariat prop1 = new PropositionPartenariat(centre, 100.0, 200.0, commerce);
        PropositionPartenariat prop2 = new PropositionPartenariat(centre, 100.0, 200.0, commerce);

        TestFunction.areEqual(commerce.getNom(), "Supermarché Test", "Vérification du nom du commerce");

        commerce.recevoirPropositionPartenariat(prop1);
        commerce.recevoirPropositionPartenariat(prop2);
        TestFunction.areEqual(commerce.getPropositionsEnAttente(0, 2).size(), 2, "Ajout de propositions de partenariat");
        
        commerce.refuser(prop1);
        TestFunction.areEqual(commerce.getPropositionsEnAttente(0, 2).contains(prop1), false, "Refus d'une proposition");

        LocalDate dateExpiration = LocalDate.now().plusDays(30);
        commerce.accepter(prop2, dateExpiration, "Electroménager");
        TestFunction.areEqual(commerce.getPropositionsEnAttente(0, 2).contains(prop2), false, "Acceptation d'une proposition");

        TestFunction.areEqual(commerce.getPropositionsEnAttente(0, 2).size(), 0, "Vérification des propositions après acceptation et refus");
    }
}
