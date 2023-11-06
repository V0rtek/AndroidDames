package cstjean.mobile.dames;

/**
 * Test la classe Dame bas sur les tests de TestPion.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand.
 */
public class TestDame extends TestPion {
    @Override
    protected Pion creerPion() {
        return new Dame();
    }

    @Override
    protected Pion creerPion(Pion.Couleur couleur) {
        return new Dame(couleur);
    }

    @Override
    protected char getRepresentation(String couleur) {
        if ("Noir".equals(couleur)) {
            return 'D';
        } else if ("Blanc".equals(couleur)) {
            return 'd';
        }
        return ' ';
    }
}
