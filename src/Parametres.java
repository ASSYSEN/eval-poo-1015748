/**
 * Paramètres personnels de l'évaluation.
 * 1) Remplacez MATRICULE par votre matricule complet.
 * 2) Remplacez N par les DEUX DERNIERS CHIFFRES de votre matricule (ex. 2410537 -> 37).
 * Ne modifiez rien d'autre dans ce fichier : les valeurs ci-dessous sont calculées à partir de N.
 */
public final class Parametres {
    public static final String MATRICULE = "1015748";
    public static final int N = 48;                                // <-- À REMPLACER

    public static final long TARIF_KG = 400 + 10 * (N % 10);      // FCFA par kg
    public static final long SURCHARGE_HORS_DAKAR = 1500;         // FCFA
    public static final int CAPACITE = 15 + N % 7;                // kg (exercice 3)

    private Parametres() { }
}
