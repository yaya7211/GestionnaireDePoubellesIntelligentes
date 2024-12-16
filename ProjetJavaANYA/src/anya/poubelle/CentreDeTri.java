package anya.poubelle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CentreDeTri {
	private ArrayList<BonAchat> bonsAchatDisponibles = new ArrayList<BonAchat>();
	private ArrayList<BonAchat> bonsAchatIndisponibles = new ArrayList<BonAchat>();
	private ArrayList<PropositionPartenariat> propositionRefusees = new ArrayList<PropositionPartenariat>();
	private String nom;
	private ArrayList<PoubelleIntelligente> poubellesAVider = new ArrayList<PoubelleIntelligente>();
    private HashMap<CouleurBac, ArrayList<double[]>> entrepotDechets = new HashMap<>(); // Initialize here
    private ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
    private ArrayList<PoubelleIntelligente> poubelles = new ArrayList<PoubelleIntelligente>();
    private ArrayList<Commerce> partenairesPotentiels = new ArrayList<Commerce>();
    private HashMap<CouleurBac, Double> pointsParCouleur = new HashMap<CouleurBac, Double>(Map.of(
    		CouleurBac.BLEUE, 5.0,
    		CouleurBac.JAUNE, 5.0,
    		CouleurBac.NOIRE, 5.0,
    		CouleurBac.VERTE, 5.0));
    
    
    public ArrayList<PoubelleIntelligente> getPoubellesAVider() {
    	return this.poubellesAVider;
    }
	
	public CentreDeTri(String nom) {
		this.nom = nom;
	}
	
	public void ajouterPartenairePotentiel(Commerce commerce) {
		this.partenairesPotentiels.add(commerce);
	}
	
	public ArrayList<Commerce> getPartenairesPotentiels() {
		return this.partenairesPotentiels;
	}
	
	public void ajouterUtilisateur(Utilisateur utilisateur) {
		this.utilisateurs.add(utilisateur);
	}
	
	public void ajouterPoubelle(PoubelleIntelligente poubelle) {
		this.poubelles.add(poubelle);
	}
	
	public void demanderVidage(PoubelleIntelligente poubelle) {
		this.poubellesAVider.add(poubelle);
	}
	
	public void lancerTourneeVidage() {
		for (PoubelleIntelligente poubelle: this.poubellesAVider) {
			for (CouleurBac couleurBac: CouleurBac.values()) {
				double[] vidage = poubelle.getBacSpecialise(couleurBac).vider();
				this.entrepotDechets.get(couleurBac).add(vidage);
			}
		}
	}
	
	public void setPointsPourCouleur(CouleurBac couleur, Double points) {
		this.pointsParCouleur.put(couleur, points);
	}
	
	public double demanderScoreParUnite(CouleurBac couleur, boolean success) {
		if (success) {
			return this.pointsParCouleur.get(couleur);
		} else {
			return (-1) * this.pointsParCouleur.get(couleur);
		}
	}
	public ArrayList<BonAchat> consulterBonsAchatsDisponibles(int iMin, int iMax) {
	    iMin = Math.max(0, iMin);
	    iMax = Math.min(this.bonsAchatDisponibles.size(), iMax);
		return new ArrayList<>(bonsAchatDisponibles.subList(iMin, iMax));
	}
	
	public void signalerChangementEtatBonAchat(BonAchat bonAchat) {
		if (bonsAchatDisponibles.contains(bonAchat)) {
			bonsAchatDisponibles.remove(bonAchat);
			bonsAchatIndisponibles.add(bonAchat);
		}
	}

	public ArrayList<PoubelleIntelligente> getPoubelles() {
		return this.poubelles;
	}
	
	public void proposerPartenariat(Commerce partenaire, double montant, double nbPointsRequis) {
		PropositionPartenariat proposition = new PropositionPartenariat(this, montant, nbPointsRequis, partenaire);
	}
	
	public void recevoirAcceptationPropositionPatrenariat(BonAchat partenariat) {
		this.bonsAchatDisponibles.add(partenariat);
	}

	public void recevoirRefusPropositionPartenariat(PropositionPartenariat propositionPartenariat) {
		this.propositionRefusees.add(propositionPartenariat);
	}

	public String getNom() {
		return this.nom;
	}

	public ArrayList<PropositionPartenariat> getPropositionRefusees() {
		return this.propositionRefusees;
	}
}
