package cstjean.mobile.dames;

import junit.framework.TestCase;

/**
 * Test pour la classe Pion.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand.
 */
public class TestPion extends TestCase {

    /**
     * Tests la création de pion.
     */
    public void testCreer() {
        Pion.Couleur couleurN = Pion.Couleur.Noir;
        Pion pion1 = creerPion(couleurN);
        assertEquals(couleurN, pion1.getCouleur());

        Pion.Couleur couleurB = Pion.Couleur.Blanc;
        Pion pion2 = creerPion(couleurB);
        assertEquals(couleurB, pion2.getCouleur());

        Pion pion3 = creerPion();
        assertEquals(couleurB, pion3.getCouleur());
    }

    /**
     * Test de la représentation.
     */
    public void testRepresentation() {
        Pion pion2 = creerPion(Pion.Couleur.Noir);
        assertEquals(getRepresentation("Noir"), pion2.getRepresentation());

        Pion pion3 = creerPion(Pion.Couleur.Blanc);
        assertEquals(getRepresentation("Blanc"), pion3.getRepresentation());
    }

    /**
     * Créer un pion sans argument.
     *
     * @return Le pion créé (blanc de base).
     */
    protected Pion creerPion() {
        return new Pion();
    }

    /**
     * Créer un pion avec arguments.
     *
     * @param couleur La couleur du pion.
     * @return Le pion créé avec la couleur en paramètre.
     */
    protected Pion creerPion(Pion.Couleur couleur) {
        return new Pion(couleur);
    }

    /**
     * Donne la représentation attendue du pion selon sa couleur.
     *
     * @param couleur La couleur du pion.
     * @return La représentation du pion en caractère.
     */
    protected char getRepresentation(String couleur) {
        if ("Noir".equals(couleur)) {
            return 'P';
        } else if ("Blanc".equals(couleur)) {
            return 'p';
        }
        return ' ';
    }
}
