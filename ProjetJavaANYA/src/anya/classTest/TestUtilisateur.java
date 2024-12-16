package anya.classTest;

import java.time.LocalDate;

import anya.poubelle.*;

public class TestUtilisateur {

    public static void main(String[] args) {
        CentreDeTri centreDeTri = new CentreDeTri("CentreTest");
        Utilisateur utilisateur = new Utilisateur("TestUser", centreDeTri);
        PoubelleIntelligente poubelle = new PoubelleIntelligente(0.1, 0.1, centreDeTri, "Poubelle de SPSGEL");
        Commerce commerce = new Commerce("La boucherie");
        Dechet dechet = new Dechet(1, TypeDechet.plastique);
        BonAchat bon = new BonAchat(4.0, 4.0, LocalDate.now().plusDays(5), commerce, "produit", centreDeTri);
        centreDeTri.recevoirAcceptationPropositionPatrenariat(bon);
        
        TestFunction.areEqual(utilisateur.getNom(), "TestUser", "Utilisateur.getNom");
        
        TestFunction.areEqual(utilisateur.getPointsFidelite(), 0.0, "Utilisateur.getPointsFidelite avant depot");
        TestFunction.areEqual(utilisateur.getArgentEpargne(), 0.0, "Utilisateur.getArgentEpargne avant depot");
        TestFunction.areEqual(utilisateur.getHistoriqueDepots().size(), 0, "Utilisateur.getHistorique avant depot");
        TestFunction.areEqual(utilisateur.getBonsAchat().size(), 0, "Utilisateur.getBonsAhat avant depot");
        
        TestFunction.areEqual(utilisateur.placerDechetDansPoubelle(dechet, poubelle, CouleurBac.JAUNE), false, "Utilisateur.placerDechet sans déverouiller");
        
        utilisateur.seConnecterEtDeverouiller(poubelle);
        TestFunction.areEqual(utilisateur.placerDechetDansPoubelle(dechet, poubelle, CouleurBac.JAUNE), true, "Utilisateur.placerDechet avec couleur conforme");
        TestFunction.areEqual(utilisateur.getPointsFidelite(), 5.0, "Utilisateur.getPointsFidelite après depot");

        utilisateur.seConnecterEtDeverouiller(poubelle);
        TestFunction.areEqual(utilisateur.placerDechetDansPoubelle(dechet, poubelle, CouleurBac.NOIRE), true, "Utilisateur.placerDechet avec couleur non conforme");
        TestFunction.areEqual(utilisateur.getPointsFidelite(), 0.0, "Utilisateur.getPointsFidelite après depot");
        
        utilisateur.seConnecterEtDeverouiller(poubelle);
        TestFunction.areEqual(utilisateur.placerDechetDansPoubelle(dechet, poubelle, CouleurBac.NOIRE), true, "Utilisateur.placerDechet avec couleur non conforme en négatif");
        TestFunction.areEqual(utilisateur.getPointsFidelite(), -2.5, "Utilisateur.getPointsFidelite après depot");
        
        
        TestFunction.areEqual(utilisateur.consulterBonsAchatDisponibles(0, 1).contains(bon), true, "utilisateur.consulterBonsAchatDisponibles");
        
        TestFunction.areEqual(utilisateur.reclamerBonAchat(bon), false, "utilisateur.reclamerBonAchat sans points");
        
        for(int i=0; i< 5; i++) {
	        utilisateur.placerDechetDansPoubelle(dechet, poubelle, CouleurBac.JAUNE);
	        utilisateur.seConnecterEtDeverouiller(poubelle);
        }
        TestFunction.areEqual(utilisateur.reclamerBonAchat(bon), true, "utilisateur.reclamerBonAchat avec assez de points");
        
    }
}
