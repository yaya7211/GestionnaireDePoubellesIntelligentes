package anya.classTest;

import anya.poubelle.BacSpecialise;
import anya.poubelle.CentreDeTri;
import anya.poubelle.CouleurBac;
import anya.poubelle.Dechet;
import anya.poubelle.PoubelleIntelligente;
import anya.poubelle.TypeDechet;
import anya.poubelle.Utilisateur;

import anya.poubelle.BacSpecialise;
import anya.poubelle.CentreDeTri;
import anya.poubelle.CouleurBac;
import anya.poubelle.Dechet;
import anya.poubelle.PoubelleIntelligente;
import anya.poubelle.TypeDechet;
import anya.poubelle.Utilisateur;

import java.util.Arrays;

import anya.poubelle.BacSpecialise;
import anya.poubelle.CentreDeTri;
import anya.poubelle.CouleurBac;
import anya.poubelle.Dechet;
import anya.poubelle.Depot;
import anya.poubelle.PoubelleIntelligente;
import anya.poubelle.TypeDechet;
import anya.poubelle.Utilisateur;

public class TestBacSpecialise {
    public static void main(String[] args) {
		CentreDeTri c = new CentreDeTri("CentreTest");
		PoubelleIntelligente p = new PoubelleIntelligente(10.1, 1.10, c, "P");
		BacSpecialise b = new BacSpecialise(CouleurBac.BLEUE, p, 1);
		Utilisateur u = new Utilisateur("User", c);
		Dechet d1 = new Dechet(45, TypeDechet.papier);
		Dechet d0 = new Dechet(45, TypeDechet.autre);
		
		TestFunction.areEqual(b.getPoubelleMere(), p, "BacSpecialise.getPoubelleMere");
		
		TestFunction.areEqual(b.peutRecevoir(10), true, "BacSpecialise.peutRecevoir avec assez d'espace");
		
		u.seConnecterEtDeverouiller(p);
		TestFunction.isType(b.placerDechet(d1, u), Depot.class, "BacSpecialise.placerDechet avec le bon déchet");
		TestFunction.isType(b.placerDechet(d0, u), Depot.class, "BacSpecialise.placerDechet avec le mauvais déchet");
		
		TestFunction.areEqual(b.peutRecevoir(10), true, "BacSpecialise.peutRecevoir avec juste assez d'espace");
		TestFunction.areEqual(b.peutRecevoir(11), false, "BacSpecialise.peutRecevoir manquant d'espace");

		p.seDeconnecterEtVerouiller();
		TestFunction.isNull(b.placerDechet(d0, u), "BacSpecialise.placerDechet avec la poubelle verouillée");
		
		double[] expectedVidage = {d0.getPoid() + d1.getPoid(), 0.5};
		TestFunction.areEqual(Arrays.equals(b.vider(), expectedVidage), true, "BacSpecialise.vider");
		
		TestFunction.areEqual(b.peutRecevoir(11), true, "BacSpecialise.peutRecevoir après vidage car pleine");
	}
}
