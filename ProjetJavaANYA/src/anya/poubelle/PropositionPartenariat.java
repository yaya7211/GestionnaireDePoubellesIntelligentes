package anya.poubelle;

import java.time.LocalDate;

public class PropositionPartenariat {

	private final CentreDeTri centreDeTri;
	private double montant;
	private LocalDate dateExpiration;
	private EtatProposition etat = EtatProposition.EnAttente;
	private BonAchat bon;
	private final double pointsRequis;
	private final Commerce commerce;
	private String categorieProduit;
	private String rule;

	public PropositionPartenariat(CentreDeTri centreDeTri, double montant, double pointsRequis, Commerce commerce) {
		this.centreDeTri = centreDeTri;
		this.montant = montant;
		this.pointsRequis = pointsRequis;
		this.commerce = commerce;
		commerce.recevoirPropositionPartenariat(this);
	}
	
	public CentreDeTri getCentreDeTri() {
		return this.centreDeTri;
	}
	
	public double getMontant() {
		return this.montant;
	}
	
	public void accepter(LocalDate dateExpiration, String categorie) {
		this.dateExpiration = dateExpiration;
		this.categorieProduit = categorie;
		this.bon = new BonAchat(this.pointsRequis, this.montant, this.dateExpiration, this.commerce, this.categorieProduit, this.centreDeTri);
		this.centreDeTri.recevoirAcceptationPropositionPatrenariat(this.bon);
		this.etat = EtatProposition.Accepte;
	}
	
	public void refuser() {
		this.centreDeTri.recevoirRefusPropositionPartenariat(this);
		this.etat = EtatProposition.Refuse;
	}
	
	public EtatProposition getEtat() {
		return this.etat;
	}

	public Object getPointsRequis() {
		return this.pointsRequis;
	}

	public String getDateExpiration() {
		return this.dateExpiration.toString();
	}

	public String getCategorieProduit() {
		return this.categorieProduit;
	}
	
}
