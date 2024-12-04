package anya.poubelle;
import java.util.Random;
import java.util.Arrays;

public class Dechet {
	Random r = new Random();
	private static final TypeDechet[] typesDechets = TypeDechet.values();
	private final double poid;
	private final TypeDechet type;
	
	public Dechet(double poid, TypeDechet type) {
		this.poid = poid;
		if (Arrays.asList(typesDechets).contains(type)) {
			this.type = type;
		} else {
			this.type = typesDechets[r.nextInt(typesDechets.length)];
		}
	} 
	
	public double getPoid() {
		return this.poid;
	}
	
	public TypeDechet getType() {
		return this.type;
	}
	
	public TypeDechet devinerType(double accuracy) {
		if (r.nextDouble() <= accuracy) {
			return getType();
		} else {
			return typesDechets[r.nextInt(typesDechets.length)];
		}
	}
}
