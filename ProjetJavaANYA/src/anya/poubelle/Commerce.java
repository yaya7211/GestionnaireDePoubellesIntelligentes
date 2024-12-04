package anya.poubelle;

import java.time.LocalDate;
import java.util.ArrayList;

public class Commerce {
	private String nom;
	private ArrayList<PropositionPartenariat> propositionsEnAttente =  new ArrayList<PropositionPartenariat>();
	
	public Commerce(String nom) {
		this.nom = nom;
	}

	public void recevoirPropositionPartenariat(PropositionPartenariat proposition) {
		this.propositionsEnAttente.add(proposition);
	}
	
	public ArrayList<PropositionPartenariat> getPropositionsEnAttente(int iMin, int iMax) {
	    iMin = Math.max(0, iMin);
	    iMax = Math.min(this.propositionsEnAttente.size(), iMax);
	    return new ArrayList<>(propositionsEnAttente.subList(iMin, iMax));	
	}
	
	public void refuser(PropositionPartenariat proposition) {
		proposition.refuser();
		this.propositionsEnAttente.remove(proposition);
	}
	
	public void accepter(PropositionPartenariat proposition, LocalDate dateExpiration) {
		proposition.accepter(dateExpiration);
		this.propositionsEnAttente.remove(proposition);

	}

	public String getNom() {
		return this.nom;
	}
	
	
}
