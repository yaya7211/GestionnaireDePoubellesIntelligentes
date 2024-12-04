package anya.poubelle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class PoubelleIntelligente {
	Random r = new Random();
	private EtatPoubelle etat = EtatPoubelle.Verouillee;
	private CentreDeTri centreDeTriMere;
	private static int nbPoubelles = 0;
	private final int idPoubelle;
	private double lat;
	private double lon;
    private HashMap<CouleurBac, BacSpecialise> bacsMap = new HashMap<>(); // Initialize here
	private Utilisateur utilisateurEnCours;
    private static CouleurBac[] couleurs = CouleurBac.values();
	
    public PoubelleIntelligente(double lat, double lon, CentreDeTri centreDeTriMere) {
    	this.lat = lat;
    	this.lon = lon;
    	this.idPoubelle = nbPoubelles;
    	nbPoubelles++;
    	this.centreDeTriMere = centreDeTriMere;
    	centreDeTriMere.ajouterPoubelle(this);
    	for (CouleurBac couleur : couleurs) {
    		this.bacsMap.put(couleur, new BacSpecialise(couleur, this, 0.85 + 0.15 * r.nextGaussian()));
    	}
    }
    
    public Collection<BacSpecialise> getTousLesBacs() {
    	return this.bacsMap.values();
    }
	
	public BacSpecialise getBacSpecialise(CouleurBac couleur) {
		return this.bacsMap.get(couleur);
	}
	
	public boolean seConnecterEtDeverouiller(Utilisateur utilisateur) {
		if (this.etat != EtatPoubelle.Pleine) {
			this.utilisateurEnCours = utilisateur;
			this.etat = EtatPoubelle.Deverouillee;
			return true;
		} else {
			return false;
		}
	}
	
	public void notifierRemplissage() {
		this.etat = EtatPoubelle.Pleine;
		centreDeTriMere.demanderVidage(this);
	}
	
	public CentreDeTri getCentreDeTriMere() {
		return this.centreDeTriMere;
	}
	
	public EtatPoubelle getEtat() {
		return this.etat;
	}

	public void seDeconnecterEtVerouiller() {
			this.utilisateurEnCours = null;
			this.etat = EtatPoubelle.Verouillee;
	}

	public double[] getCoordonnees() {
		double[] coo =  {this.lat, this.lon};
		return coo;
	}
}
