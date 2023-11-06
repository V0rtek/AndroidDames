package cstjean.mobile.dames;

/**
 * Sert à faire un modèle de base de damier pour les tests.
 */
public class RepresentationDamier {
    /**
     * Créer une représentation du damier et de ses pions.
     *
     * @param damier Le damier qui doit être représenté.
     *  @return La représentation du damier en String.
     */
    public static String getRepresentation(Damier damier) {
        boolean rangeePaire = true;
        StringBuilder representation = new StringBuilder();

        for (int i = 1; i <= Damier.NB_CASES; i++) {
            if (rangeePaire) {
                representation.append(Damier.REPRESENTATIONCASEVIDE);
            }

            if (damier.inspecterCase(i) != null) {
                representation.append(damier.inspecterCase(i).getRepresentation());
            } else {
                representation.append(Damier.REPRESENTATIONCASEVIDE);
            }

            if (!rangeePaire) {
                representation.append(Damier.REPRESENTATIONCASEVIDE);
            }

            if (i % 5 == 0 && Damier.NB_CASES != i) {
                representation.append("\n");
                rangeePaire = !rangeePaire;
            }
        }
        return representation.toString();
    }
}
