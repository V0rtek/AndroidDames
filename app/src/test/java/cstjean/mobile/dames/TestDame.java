package cstjean.mobile.dames;

import org.junit.Test;

/**
 * Test la classe Dame bas sur les tests de TestPion.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand.
 */
public class TestDame {
    @Test
    protected Pion creerPion() {
        return new Dame();
    }

    @Test
    protected Pion creerPion(Pion.Couleur couleur) {
        return new Dame(couleur);
    }

    @Test
    protected char getRepresentation(String couleur) {
        if ("Noir".equals(couleur)) {
            return 'D';
        } else if ("Blanc".equals(couleur)) {
            return 'd';
        }
        return ' ';
    }
}
