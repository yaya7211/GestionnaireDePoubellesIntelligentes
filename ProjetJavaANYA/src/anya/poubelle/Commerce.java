package anya.poubelle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Commerce {
	private String nom;
	private HashSet<PropositionPartenariat> propositionsEnAttente = new HashSet<>();	
	public Commerce(String nom) {
		this.nom = nom;
	}

	public void recevoirPropositionPartenariat(PropositionPartenariat proposition) {
		this.propositionsEnAttente.add(proposition);
	}
	
	public ArrayList<PropositionPartenariat> getPropositionsEnAttente(int iMin, int iMax) {
	    ArrayList<PropositionPartenariat> list = new ArrayList<>(this.propositionsEnAttente);
	    iMin = Math.max(0, iMin);
	    iMax = Math.min(list.size(), iMax);
	    return new ArrayList<>(list.subList(iMin, iMax));
	}

	public void refuser(PropositionPartenariat proposition) {
		this.propositionsEnAttente.remove(proposition);
		proposition.refuser();
	}
	
	public void accepter(PropositionPartenariat proposition, LocalDate dateExpiration, String categorie) {
		this.propositionsEnAttente.remove(proposition);
		proposition.accepter(dateExpiration, categorie);
	}

	public String getNom() {
		return this.nom;
	}
	
	@Override
	public String toString() {
		return this.getNom();
	}
	
	
}
