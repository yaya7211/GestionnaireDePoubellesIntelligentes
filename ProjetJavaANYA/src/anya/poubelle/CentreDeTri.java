package anya.poubelle;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	public double demanderScoreParUnite(CouleurBac couleur, boolean success) {
		//la couleur est une enum, demandez la moi	
		//demerdesen sich tant qu'il donne un score négatif pour le success == false et un score positif pour le success = true
		if (success) {
			return 10;
		} else {
			return -10;
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
}
