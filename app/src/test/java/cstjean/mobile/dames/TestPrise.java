package cstjean.mobile.dames;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

/**
 * Test la classe Prise.
 */
public class TestPrise {

    /**
     * Test la création d'une prise.
     */
    @Test
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
    @Test
    public void testPrisesEnfants() {
        Prise racine = new Prise(1);
        Prise enfant = new Prise(12, 7);

        racine.addPriseEnfant(enfant);
        assertEquals(enfant, racine.getPrisesEnfants().get(0));

        List<List<Prise>> listeMeilleursPrises = racine.trouverMeilleursSuiteDePrises();
        assertEquals(12, listeMeilleursPrises.get(0).get(0).getPosition());
    }
}
