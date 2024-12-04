Lire le ManualTest.java dont voilà les deux sorties possibles (pour rappel, la reconnaissance de déchets est simulée comme imparfaite, donc avec un facteur aléatoire).

```
Début du test manuel.
Centre de tri créé : Centre de tri de Saint-Germain-En-Laye
Poubelle intelligente créée à la position : ([D@3cd1f1c8)
Poubelle ajoutée au centre de tri.
Commerce créé : Carrefour
Commerce ajouté comme partenaire potentiel.
Utilisateur créé : Yassine
Déchet créé : verre avec un poids de 0.5 kg
Avant le jet, le nombre de points de fidélités de l'utilisateur est 0.0 points.
Utilisateur connecté et poubelle déverrouillée.
Jet de déchet a réussi.
Après le jet, le nombre de points de fidélités de l'utilisateur est 5.0 points.
Proposition de partenariat envoyée.
Propositions reçues : 1
Acceptation de la première proposition.
Avant la réclamation, le nombre de points de fidélités de l'utilisateur est 5.0 points.
Bons d'achat disponibles : 1
Tentative de réclamation du premier bon.
L'utilisateur a réclamé son bon d'achat.
Avant la réclamation, le nombre de points de fidélités de l'utilisateur est 0.0 points.
Fin du test manuel.
```

Ou : 

```
Début du test manuel.
Centre de tri créé : Centre de tri de Saint-Germain-En-Laye
Poubelle intelligente créée à la position : ([D@3cd1f1c8)
Poubelle ajoutée au centre de tri.
Commerce créé : Carrefour
Commerce ajouté comme partenaire potentiel.
Utilisateur créé : Yassine
Déchet créé : verre avec un poids de 0.5 kg
Avant le jet, le nombre de points de fidélités de l'utilisateur est 0.0 points.
Utilisateur connecté et poubelle déverrouillée.
Jet de déchet a réussi.
Après le jet, le nombre de points de fidélités de l'utilisateur est -2.5 points.
Proposition de partenariat envoyée.
Propositions reçues : 1
Acceptation de la première proposition.
Avant la réclamation, le nombre de points de fidélités de l'utilisateur est -2.5 points.
Bons d'achat disponibles : 1
Tentative de réclamation du premier bon.
Le bon d'achat n'a pas pu être réclamé.
Avant la réclamation, le nombre de points de fidélités de l'utilisateur est -2.5 points.
Fin du test manuel.
```
