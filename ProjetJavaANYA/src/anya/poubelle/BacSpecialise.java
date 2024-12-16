package anya.poubelle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class BacSpecialise {
	Random r  = new Random();
    private static final Map<CouleurBac, TypeDechet[]> typesConformes = Map.of(
    		CouleurBac.VERTE, new TypeDechet[] {TypeDechet.verre},
    		CouleurBac.JAUNE, new TypeDechet[] {TypeDechet.emballage, TypeDechet.carton, TypeDechet.plastique, TypeDechet.canette, TypeDechet.conserve},
            CouleurBac.BLEUE, new TypeDechet[] {TypeDechet.papier},
            CouleurBac.NOIRE, new TypeDechet[] {TypeDechet.autre});
	private static int nbBacSpecialise;
	private final int idBacSpecialise = nbBacSpecialise;
	private final TypeDechet[] typesDechetsConformes;
	private int capaciteMax = 100;
	private ArrayList<Depot> contenance = new ArrayList<Depot>();
	private final CouleurBac couleur;
	private final PoubelleIntelligente poubelleMere;
	private double accuracy;
	
    public BacSpecialise(CouleurBac couleur, PoubelleIntelligente poubelleMere, double accuracy) {
        this.poubelleMere = poubelleMere;
        nbBacSpecialise ++;
        this.couleur = couleur;
        this.typesDechetsConformes = typesConformes.get(this.couleur);
        this.accuracy = accuracy;
    }
    
    public PoubelleIntelligente getPoubelleMere() {
    	return this.poubelleMere;
    }
    
    public boolean peutRecevoir(double poid) {
    	if (this.poubelleMere.getEtat() != EtatPoubelle.Pleine) {
	    	double totalPoid = 0;
	        for (Depot depot : this.contenance) {
	            totalPoid += depot.getPoid();
	        }
	        return poid <= (this.capaciteMax - totalPoid);
    	} else {
    		return false;
    	}
    }
    
    public Depot placerDechet(Dechet dechet, Utilisateur utilisateur) {
    	if (this.poubelleMere.getEtat() == EtatPoubelle.Deverouillee) {
	    	TypeDechet typeDevine = dechet.devinerType(accuracy);
	    	boolean triConforme = Arrays.asList(this.typesDechetsConformes).contains(typeDevine) & (this.accuracy > r.nextDouble());
	    	double mult = this.poubelleMere.getCentreDeTriMere().demanderScoreParUnite(this.couleur, triConforme);
	    	Depot depot = new Depot(utilisateur, this, mult * dechet.getPoid(), triConforme, dechet.getPoid());
	    	this.contenance.add(depot);
	    	return depot;
    	} else {
    		return null;
    	}
    }
    
    public double[] vider() {
    	double vidageConforme = 0;
    	double vidageTotal = 0;
    	for (Depot depot : this.contenance) {
    		double p = depot.getPoid();
    		vidageTotal += p;
    		if (depot.getSucces()) {
    			vidageConforme += p;
    		}
    	}
    	this.contenance = new ArrayList<Depot>();
    	double[] resultat = {vidageTotal, vidageConforme/vidageTotal};
    	return resultat;
    }

	public CouleurBac getCouleur() {
		return this.couleur;
	}
}
