package anya.classTest;

import anya.poubelle.BacSpecialise;
import anya.poubelle.CentreDeTri;
import anya.poubelle.CouleurBac;
import anya.poubelle.Dechet;
import anya.poubelle.EtatPoubelle;
import anya.poubelle.PoubelleIntelligente;
import anya.poubelle.TypeDechet;
import anya.poubelle.Utilisateur;

public class TestPoubelleIntelligente {
	public static void main(String[] args) {
		CentreDeTri c = new CentreDeTri("CentreTest");
		PoubelleIntelligente p = new PoubelleIntelligente(10.1, 1.10, c, "Poubelle immeuble");
		BacSpecialise b = new BacSpecialise(CouleurBac.BLEUE, p, 1);
		Utilisateur u = new Utilisateur("User", c);
		Dechet d1 = new Dechet(45, TypeDechet.papier);
		Dechet d0 = new Dechet(45, TypeDechet.autre);
		
		TestFunction.areEqual(p.getCentreDeTriMere(), c, "PoubelleIntelligente.getCentreDeTriMere()");
		
		for(Object bac: p.getTousLesBacs()) {
			TestFunction.isType(bac, BacSpecialise.class, "PoubelleIntelligente.getTousLesBacs par bac");
		}
		TestFunction.areEqual(p.getTousLesBacs().size(), 4, "PoubelleIntelligente.getTousLesBacs pour la taille");
		
		for(CouleurBac couleur: CouleurBac.values()) {
			TestFunction.areEqual(p.getBacSpecialise(couleur).getCouleur(), couleur, "PoubelleIntelligente.getBacSpacialise par couleur");
		}
		
		TestFunction.areEqual(p.getEtat(), EtatPoubelle.Verouillee, "PoubelleIntelligente.seConnecterEtDeverouiller et PoubelleIntelligente.getEtat avant dévérouillage");
		p.seConnecterEtDeverouiller(u);
		TestFunction.areEqual(p.getEtat(), EtatPoubelle.Deverouillee, "PoubelleIntelligente.seConnecterEtDeverouiller et PoubelleIntelligente.getEtat après dévérouillage");
		
		p.notifierRemplissage();
		TestFunction.areEqual(c.getPoubellesAVider().get(0), p, "PoubelleIntelligente.notifierRemplissage");
	}
}
