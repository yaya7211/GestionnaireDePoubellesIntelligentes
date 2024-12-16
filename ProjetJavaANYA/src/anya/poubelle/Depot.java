package anya.poubelle;
import java.time.LocalDateTime;

public class Depot {
	private final int idDepot;
	private final double points;
	private final Utilisateur utilisateur;
	private final BacSpecialise bac;
	private final LocalDateTime dateTime; 
	private boolean succes;
	private double quantite;
	
	
	private static int nbDepots = 0;
	
	public Depot(Utilisateur utilisateur, BacSpecialise bac, double points, boolean succes, double quantite) {
		this.idDepot = nbDepots;
		nbDepots++;
		this.utilisateur = utilisateur;
		this.points = points;
		this.bac = bac;
		this.dateTime = LocalDateTime.now();
		this.succes = succes;
		this.quantite = quantite;
	}
	
	public boolean getSucces() {
		return this.succes;
	}
	
	public double getCreditScore() {
		return this.points;
	}
	
	public PoubelleIntelligente getPoubelle() {
		return this.bac.getPoubelleMere();
	}

	public double getPoid() {
		return this.quantite;
	}
}
