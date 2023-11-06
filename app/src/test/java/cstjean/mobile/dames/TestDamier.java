package cstjean.mobile.dames;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;

/**
 * Test pour la classe Damier.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand.
 */
public class TestDamier {

    /**
     * Tests l'ajout de pion dans un damier.
     */
    @Test
    public void testAjouter() {
        Damier damier = new Damier();
        Pion pion1 = new Pion(Pion.Couleur.Noir);
        Pion pion2 = new Pion(Pion.Couleur.Blanc);

        damier.ajouterPion(38, pion1);
        assertEquals(1, damier.getNbDePions());

        damier.ajouterPion(30, pion2);
        assertEquals(2, damier.getNbDePions());

        assertEquals(pion1, damier.inspecterCase(38));
        assertEquals(pion2, damier.inspecterCase(30));

        assertEquals(Pion.Couleur.Noir, damier.inspecterCase(38).getCouleur());
        assertNull(damier.inspecterCase(28));
    }

    /**
     * Test pour l'initialisation d'un damier de base.
     */
    @Test
    public void testInitialiser() {
        final int nbDePionsDames = 40;
        final int nbDeCasesDames = 50;

        Damier damier = new Damier();
        damier.initialiser();
        assertEquals(nbDePionsDames, damier.getNbDePions());
        for (int i = 1; i <= nbDeCasesDames; i++) {
            if (i <= 20) {
                assertEquals('P', damier.inspecterCase(i).getRepresentation());
            }
            if (i > 30) {
                assertEquals('p', damier.inspecterCase(i).getRepresentation());
            }
        }

        System.out.println(damier);

        assertEquals(
                "-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "----------\n" +
                "----------\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-",
                RepresentationDamier.getRepresentation(damier));
    }

    /**
     * Test pour le get de toutes les positions de pion d'une couleur.
     */
    @Test
    public void testGetToutesPosPion() {
        Damier damier = new Damier();
        Pion pion0 = new Pion(Pion.Couleur.Noir);
        Pion pion1 = new Pion(Pion.Couleur.Blanc);
        Pion pion2 = new Pion(Pion.Couleur.Blanc);
        Pion pion3 = new Pion(Pion.Couleur.Blanc);
        Pion dame1 = new Dame(Pion.Couleur.Blanc);
        Pion dame2 = new Dame(Pion.Couleur.Blanc);

        damier.ajouterPion(30, pion0);
        damier.ajouterPion(2, pion1);
        damier.ajouterPion(3, pion2);
        damier.ajouterPion(4, pion3);
        damier.ajouterPion(5, dame1);
        damier.ajouterPion(6, dame2);
        ArrayList<Integer> listePion = new ArrayList<>();

        for (int i = 2; i <= 6; i++) {
            listePion.add(i);
        }

        assertEquals(listePion, damier.getToutesPositionsPionDuneCouleur(Pion.Couleur.Blanc));

        damier.retirerPion(3);
        damier.ajouterPion(9, pion2);

        listePion.remove(1);
        listePion.add(9);

        assertEquals(listePion, damier.getToutesPositionsPionDuneCouleur(Pion.Couleur.Blanc));

        listePion.clear();
        listePion.add(30);

        assertEquals(listePion, damier.getToutesPositionsPionDuneCouleur(Pion.Couleur.Noir));
    }
}
