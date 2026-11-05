public class Inventaire {

    public static long valeurAssuree(Object[] objects) {
        long somme = 0;
        for (Object o : objects) {
            if (o instanceof ColisFragile) {
                somme += ((ColisFragile) o).getValeurDeclaree();
            }
        }
        return somme;
    }

    // Résultat : {standard, express, fragile, autres}
    public static int[] compterParType(Object[] objects) {
        int[] res = new int[4];
        for (Object o : objects) {
            if (o == null) {
                continue; // les null sont ignorés
            }
            if (o instanceof ColisFragile) {      // à tester AVANT ColisStandard
                res[2]++;
            } else if (o instanceof ColisStandard) {
                res[0]++;
            } else if (o instanceof ColisExpress) {
                res[1]++;
            } else {
                res[3]++;
            }
        }
        return res;
    }
}