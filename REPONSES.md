# Réponses — Évaluation POO : héritage, polymorphisme, transtypage

- Matricule :1015748
- Nom et prénom :Sylla Aida
- N (deux derniers chiffres du matricule) :48

> Rédigez vos justifications **avec vos propres mots**. Elles serviront de base à l'échange oral.

## Exercice 1 — A. Affichage prédit

Écrivez ci-dessous, entre les deux lignes de trois accents graves, **une ligne par ligne affichée**, dans l'ordre.
Ne modifiez pas les lignes de trois accents graves.

```text
A vehicule
F null 0
E moto 148
Moto2
vehicule
moto
C3
C2
D-vehicule
A vehicule
F null 0
E moto 148
true false
true
2

ce qui suit explique les lignes du main

1:Le constructeur de Vehicule s’exécute d’abord, avec le champ Vehicule.nom

2: demarrer() est redéfinie dans Moto, donc c’est elle qui est appelée. Les champs de Moto ne sont pas encore initialisés : nom = null, cylindree = 0

3: Fin du constructeur de Moto, champs initialisés

4:Liaison dynamique : 148 / 50 = 2 (division entière)

5:Les champs ne sont pas polymorphes : c’est le type statique Vehicule qui compte

6:Le cast donne accès au champ de Moto

7:La surcharge comparer(Vehicule) est choisie à la compilation, puis redéfinie dans Moto

8:La surcharge comparer(Moto) est choisie à la compilation et n’est pas redéfinie

9:Méthode statique : type statique Vehicule

10:Le new Moto() est évalué avant le println, donc ses 3 lignes s’affichent d’abord. o.equals(v) vaut true (même objet) et o == new Moto() vaut false (objet différent)

11:Object.toString() donne Moto@hashcode

12:48 % 3 = 0 itération

13:Deux Moto créées (donc deux appels au constructeur de Vehicule)
```

## Exercice 1 — A. Justifications

1. Ligne qui commence par « F » :Quand on exécute new Moto(), le constructeur de Moto appelle d’abord super(), donc celui de Vehicule. À ce moment, Moto n’est pas encore « construite » : ses champs (nom, cylindree) n’ont pas reçu leurs valeurs d’initialisation, ils ont seulement la valeur par défaut de Java (null pour une référence, 0 pour un entier).

Dans le constructeur de Vehicule, l’appel demarrer() est résolu à l’exécution selon le vrai type de l’objet, qui est Moto. C’est donc la version redéfinie de Moto qui s’exécute (et non le « B »), et elle lit les champs de Moto encore vides. D’où F null 0. Le « B vehicule » n’apparaît jamais ici, puisque demarrer() de Vehicule n’est pas appelée.

2. Ligne « C3 » :Le compilateur choisit la signature d’après le type déclaré de l’argument. Ici v est déclaré Vehicule, donc c’est comparer(Vehicule) qui est retenue. À l’exécution, l’objet réel étant une Moto, on prend la version redéfinie dans Moto pour cette signature, qui renvoie "C3" (et non "C1")

3. Ligne « C2 » :Avec le cast (Moto) v, l’argument a maintenant le type déclaré Moto. Le compilateur choisit alors la surcharge la plus précise, comparer(Moto). Cette méthode n’existe que dans Vehicule (Moto ne la redéfinit pas, elle redéfinit seulement comparer(Vehicule)), donc c’est celle de Vehicule qui s’exécute et renvoie "C2"

4. Ligne qui commence par « D- » :categorie() est statique. Une méthode statique n’est pas redéfinie mais « masquée » : elle est liée à la compilation d’après le type déclaré de la variable, pas d’après l’objet réel. Comme v est déclaré Vehicule, v.categorie() appelle Vehicule.categorie() et affiche D-vehicule, même si l’objet est une Moto

5. Pourquoi des lignes « A », « F », « E » apparaissent avant « true false » :Pour afficher la ligne, Java doit d’abord construire entièrement la chaîne passée à println. Il évalue donc de gauche à droite :

o.equals(v) : même objet, donc true.
o == new Moto() : il faut créer un nouvel objet Moto pour faire la comparaison. Sa construction affiche A vehicule, F null 0, E moto 148.
La comparaison des références donne false, car ce sont deux objets distincts.

Ce n’est qu’ensuite que la chaîne "true false" est complète et que println l’affiche. Les trois lignes de construction apparaissent donc avant true false, bien qu’elles soient écrites « à l’intérieur » de la même instruction.

## Exercice 1 — B. Transtypage

Colonne « Compile ? » : `oui` ou `non`. Colonne « Exécution » : `OK`, `exception` (en précisant laquelle), ou `-` si le code ne compile pas.

| n° | Compile ? | Exécution | Pourquoi |
|----|-----------|-----------|----------|
| 1 | oui | OK | Upcast implicite : une `Moto` est un `Vehicule`. |
| 2 | non | - | `a` est déclaré `Vehicule` : le downcast vers `Moto` exige un cast explicite. |
| 3 | oui | OK | Cast légal, et l'objet référencé par `a` est bien une `Moto`. |
| 4 | oui | exception (`ClassCastException`) | Cast `Object` vers `Moto` légal à la compilation, mais `b` référence un `Camion`. |
| 5 | non | - | `Moto` et `Camion` sont des classes sœurs : cast « inconvertible ». |
| 6 | oui | exception (`ClassCastException`) | Cast `Object` vers `String` légal à la compilation, mais `b` est un `Camion`. |
| 7 | non | - | `b` est déclaré `Object`, qui n'a pas de méthode `type()`. |
| 8 | oui | OK | `(Vehicule) b` réussit (un `Camion` est un `Vehicule`) ; `x8` vaut `"Vehicule"`. |

Tarif de base écrit une seule fois (Q2) : seule Colis.tarif() contient la formule. ColisExpress et ColisFragile la réutilisent avec super.tarif().
toString (Q3) : il est écrit une seule fois dans Colis, grâce à la liaison dynamique de sigle() et tarif(). ColisFragile ajoute seulement la fin avec super.toString().
equals (Q4) : getClass() != o.getClass() est utilisé à la place de instanceof. Avec instanceof, un ColisFragile (qui est aussi un ColisStandard) pourrait être égal à un ColisStandard, ce que le sujet interdit. hashCode repose sur le seul code, donc deux objets égaux ont bien le même hash.
compareTo (Q5) : pour obtenir un ordre décroissant, on inverse les arguments (autre en premier). Le code est comparé en dernier, en ordre croissant.
Ordre des tests dans compterParType (Q6) : ColisFragile hérite de ColisStandard, donc instanceof ColisStandard serait vrai aussi pour un fragile. Il faut tester ColisFragile d’abord pour qu’un fragile ne compte pas comme standard. Le test o == null évite de compter les null dans « autres »