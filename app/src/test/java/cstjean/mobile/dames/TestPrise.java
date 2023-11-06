package cstjean.mobile.dames;

import java.util.List;
import junit.framework.TestCase;

/**
 * Test la classe Prise.
 */
public class TestPrise extends TestCase {
    /**
     * Test la création d'une prise.
     */
    public void testCreer() {
        Prise racine = new Prise(1);
        assertEquals(1, racine.getPosition());

        Prise prise = new Prise(1, 7);
        assertEquals(1, prise.getPosition());
        assertEquals(7, prise.getPositionPrise());
    }

    /**
     * Fait des tests sur les enfants d'une prise.
     */
    public void testPrisesEnfants() {
        Prise racine = new Prise(1);
        Prise enfant = new Prise(12, 7);

        racine.addPriseEnfant(enfant);
        assertEquals(enfant, racine.getPrisesEnfants().getFirst());

        List<List<Prise>> listeMeilleursPrises = racine.trouverMeilleursSuiteDePrises();
        assertEquals(12, listeMeilleursPrises.getFirst().getFirst().getPosition());
    }
}
