package anya.classTest;

import anya.poubelle.Dechet;
import anya.poubelle.TypeDechet;

public class TestDechet {
    public static void main(String[] args) {
        // Test du constructeur avec un type valide
        Dechet dechet1 = new Dechet(2.5, TypeDechet.plastique);
        TestFunction.isNonNull(dechet1, "dechet1");
        TestFunction.areEqual(dechet1.getPoid(), 2.5, "Vérification du poids du déchet");
        TestFunction.areEqual(dechet1.getType(), TypeDechet.plastique, "Vérification du type de déchet");

        // Test du constructeur avec un type invalide (null)
        Dechet dechet2 = new Dechet(1.2, null);
        TestFunction.isNonNull(dechet2, "dechet2");
        TestFunction.areEqual(dechet2.getPoid(), 1.2, "Vérification du poids du déchet 2");
        TestFunction.isNonNull(dechet2.getType(), "Vérification que le type est attribué aléatoirement");

        // Test de la méthode devinerType avec une précision de 100%
        TypeDechet typeDevine = dechet1.devinerType(1.0);
        TestFunction.areEqual(typeDevine, dechet1.getType(), "Vérification de devinerType avec 100% de précision");

        // Test de la méthode devinerType avec une précision de 0%
        boolean resultDifferent = false;
        for (int i = 0; i < 10; i++) { // Boucle pour vérifier qu'un type différent est parfois retourné
            typeDevine = dechet1.devinerType(0.0);
            if (typeDevine != dechet1.getType()) {
                resultDifferent = true;
                break;
            }
        }
        TestFunction.areEqual(resultDifferent, true, "Vérification de devinerType avec 0% de précision");
    }
}
