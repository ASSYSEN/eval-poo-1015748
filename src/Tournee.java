public class Tournee {

    // a) Tri fusion
    public static void trierFusion(Colis[] t) {
        int n = t.length;
        if (n <= 1) return;                         // cas de base

        int milieu = n / 2;
        Colis[] gauche = new Colis[milieu];
        Colis[] droite = new Colis[n - milieu];
        for (int i = 0; i < milieu; i++) gauche[i] = t[i];
        for (int i = milieu; i < n; i++) droite[i - milieu] = t[i];

        trierFusion(gauche);
        trierFusion(droite);
        fusionner(t, gauche, droite);
    }

    private static void fusionner(Colis[] t, Colis[] g, Colis[] d) {
        int i = 0, j = 0, k = 0;
        while (i < g.length && j < d.length) {
            if (g[i].compareTo(d[j]) <= 0) t[k++] = g[i++];
            else                           t[k++] = d[j++];
        }
        while (i < g.length) t[k++] = g[i++];       // reste de g
        while (j < d.length) t[k++] = d[j++];       // reste de d
    }

    // b) Chargement optimal (sac à dos 0/1)
    public static Colis[] chargementOptimal(Colis[] t, int capacite) {
        int n = t.length;
        long[][] meilleur = new long[n + 1][capacite + 1];   // ligne 0 = zéros

        for (int i = 1; i <= n; i++) {
            int poids = t[i - 1].getPoids();
            long tarif = t[i - 1].tarif();
            for (int cap = 0; cap <= capacite; cap++) {
                meilleur[i][cap] = meilleur[i - 1][cap];      // sans le colis i
                if (poids <= cap) {
                    long avec = meilleur[i - 1][cap - poids] + tarif;
                    if (avec > meilleur[i][cap]) meilleur[i][cap] = avec;
                }
            }
        }

        // Reconstruction : on remonte depuis meilleur[n][capacite]
        boolean[] pris = new boolean[n];
        int nb = 0;
        int cap = capacite;
        for (int i = n; i >= 1; i--) {
            if (meilleur[i][cap] != meilleur[i - 1][cap]) {
                pris[i - 1] = true;
                nb++;
                cap -= t[i - 1].getPoids();
            }
        }

        Colis[] resultat = new Colis[nb];                      // taille exacte
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (pris[i]) resultat[k++] = t[i];
        }
        return resultat;
    }

    // c) Programme principal
    public static void main(String[] args) {
        Colis[] lot = {
            new ColisStandard("C01", 4, false),
            new ColisExpress("C02", 3, true),
            new ColisFragile("C03", 5, false, 120_000),
            new ColisStandard("C04", 7, true),
            new ColisExpress("C05", 2, false),
            new ColisFragile("C06", 6, true, 45_000),
            new ColisStandard("C07", 1, true),
            new ColisExpress("C08", 8, false),
            new ColisFragile("C09", 3, false, 300_000),
            new ColisStandard("C10", 5, false),
        };

        trierFusion(lot);
        for (Colis c : lot) System.out.println(c);

        Colis[] choisis = chargementOptimal(lot, Parametres.CAPACITE);
        long somme = 0;
        for (Colis c : choisis) somme += c.tarif();
        System.out.println("Recette optimale : " + somme
            + " FCFA (capacite " + Parametres.CAPACITE + " kg)");
    }
}