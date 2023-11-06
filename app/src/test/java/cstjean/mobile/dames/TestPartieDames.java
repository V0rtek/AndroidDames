package cstjean.mobile.dames;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Test pour la classe PartieDames.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand.
 */
public class TestPartieDames {
    /**
     * Test la création d'une partie de dame et le changement de tour.
     */
    @Test
    public void testCreer() {
        Damier damiers = new Damier();
        damiers.initialiser();
        PartieDames partie = new PartieDames(damiers);
        assertEquals(Pion.Couleur.Blanc, partie.getTour());
        partie.changerTours();
        assertEquals(Pion.Couleur.Noir, partie.getTour());
    }

    /**
     * Test l'historique de jeu.
     */
    @Test
    public void testHistorique() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);
        damier.ajouterPion(20, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(17, new Pion(Pion.Couleur.Noir));

        partie.deplacer(20, 14);

        assertEquals(Pion.Couleur.Noir, partie.getTour());
        partie.deplacer(17, 21);

        assertEquals(Pion.Couleur.Blanc, partie.getTour());
        partie.deplacer(14, 9);

        partie.retourHistorique(2);
        assertEquals(Pion.Couleur.Blanc, partie.getTour());
        assertNull(partie.getDamier().inspecterCase(9));

        partie.retourHistorique(1);
        assertEquals(Pion.Couleur.Noir, partie.getTour());
        assertNull(partie.getDamier().inspecterCase(21));

        partie.retourHistorique(0);
        assertEquals(Pion.Couleur.Blanc, partie.getTour());
        assertNotNull(partie.getDamier().inspecterCase(20));
        assertNotNull(partie.getDamier().inspecterCase(17));
    }

    /**
     * Test la validité de la liste de déplacements donné par getDeplacementsPossible.
     */
    @Test
    public void testGetDeplacementsPossible() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);

        assertNull(partie.getDeplacementsPossible(1));

        Pion pion1N = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(20, pion1N);
        Pion pion2B = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(32, pion2B);
        Pion pion3N = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(15, pion3N);
        Pion pion4B = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(37, pion4B);
        Pion pion5N = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(1, pion5N);
        Pion pion6B = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(6, pion6B);
        Pion pion7B = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(35, pion7B);
        Pion pion8N = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(16, pion8N);

        List<Integer> listDeplacement = new ArrayList<>();

        // Rien bloque Noir
        listDeplacement.add(25);
        listDeplacement.add(24);
        assertEquals(listDeplacement, partie.getDeplacementsPossible(20));
        listDeplacement.clear();

        // Rien bloque Blanc
        listDeplacement.add(27);
        listDeplacement.add(28);
        assertEquals(listDeplacement, partie.getDeplacementsPossible(32));
        listDeplacement.clear();

        // Bord + Allié bloque Noir
        assertEquals(listDeplacement, partie.getDeplacementsPossible(15));

        // Allié bloque Blanc
        listDeplacement.add(31);
        assertEquals(listDeplacement, partie.getDeplacementsPossible(37));
        listDeplacement.clear();

        // Bord + ennemi bloque Blanc
        assertEquals(listDeplacement, partie.getDeplacementsPossible(6));

        // Ennemie bloque Noir
        listDeplacement.add(7);
        assertEquals(listDeplacement, partie.getDeplacementsPossible(1));
        listDeplacement.clear();

        // Bord bloque Blanc
        listDeplacement.add(30);
        assertEquals(listDeplacement, partie.getDeplacementsPossible(35));
        listDeplacement.clear();

        // Bord bloque Noir
        listDeplacement.add(21);
        assertEquals(listDeplacement, partie.getDeplacementsPossible(16));
    }

    /**
     * Test la validité de la liste de déplacements donné par getDeplacementsPossibleDame.
     */
    @Test
    public void testGetDeplacementsPossibleDame() {
        Damier damier = new Damier();
        final PartieDames partie = new PartieDames(damier);
        Pion dame = new Dame(Pion.Couleur.Noir);
        damier.ajouterPion(20, dame);

        List<Integer> listDeplacement = new ArrayList<>();
        listDeplacement.add(24);
        listDeplacement.add(29);
        listDeplacement.add(33);
        listDeplacement.add(38);
        listDeplacement.add(42);
        listDeplacement.add(47);
        listDeplacement.add(25);
        listDeplacement.add(14);
        listDeplacement.add(9);
        listDeplacement.add(3);
        listDeplacement.add(15);

        assertEquals(listDeplacement, partie.getDeplacementsPossible(20));
        listDeplacement.clear();
    }

    /**
     * Test la fonction siPrisePossible.
     */
    @Test
    public void testSiPrisePossible() {
        Damier damier = new Damier();
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        Pion dameBlanche = new Dame(Pion.Couleur.Blanc);
        // Pion non prenable
        damier.ajouterPion(5, pionNoir);
        damier.ajouterPion(4, pionNoir);
        damier.ajouterPion(15, pionNoir);
        damier.ajouterPion(36, pionNoir);
        damier.ajouterPion(6, dameBlanche);
        damier.ajouterPion(35, pionNoir);
        damier.ajouterPion(1, dameBlanche);

        PartieDames partie = new PartieDames(damier);

        // Dame : Pas de prise
        damier.ajouterPion(10, dameBlanche);
        assertFalse(partie.siPrisePossible(10));

        // Dame : A une prise
        damier.ajouterPion(37, pionNoir);
        assertTrue(partie.siPrisePossible(10));

        // Pion : prise
        Pion pionBlanc = new Pion((Pion.Couleur.Blanc));
        damier.ajouterPion(41, pionBlanc);
        damier.ajouterPion(32, pionNoir);
        assertTrue(partie.siPrisePossible(37));
        assertFalse(partie.siPrisePossible(41));

        // Dame : Pas de prise (bloqué par le pion)
        assertFalse(partie.siPrisePossible(10));

        // Pion : Pas de prise sur la bordure
        assertFalse(partie.siPrisePossible(6));

        assertFalse(partie.siPrisePossible(35));

        assertTrue(partie.siPrisePossible(36));
    }

    /**
     * Test la fonction getPrisesPossibles et trouve ensuite les déplacements obligatoire pour un pion.
     */
    @Test
    public void testGetPrisesPossibles() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);

        // Les pions ennemis
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        // damier.ajouterPion(16, pionNoir);
        damier.ajouterPion(21, pionNoir);   // Pas mangeable car bloqué par pion 16
        damier.ajouterPion(22, pionNoir);   // Mangeable
        damier.ajouterPion(12, pionNoir);   // Mangeable à la suite de la prise du pion précédent

        // Le pion blanc
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(27, pionBlanc);

        Prise prisesB = partie.getPrisesPossibles(27);
        List<List<Prise>> listeMeilleursDeplacements = prisesB.trouverMeilleursSuiteDePrises();

        int[] suiteDesPrisesVoulus = new int[] {22, 12};
        int[] suiteDesDeplacementsVoulus = new int[] {18, 7};
        int indexDeplacement = 0;
        for (Prise d : listeMeilleursDeplacements.get(0)) {
            assertEquals(d.getPositionPrise(), suiteDesPrisesVoulus[indexDeplacement]);
            assertEquals(d.getPosition(), suiteDesDeplacementsVoulus[indexDeplacement]);
            indexDeplacement++;
        }
        listeMeilleursDeplacements.clear();

        // Les pions ennemis
        damier.ajouterPion(37, pionBlanc);
        damier.ajouterPion(38, pionBlanc);
        damier.ajouterPion(28, pionBlanc);
        damier.ajouterPion(18, pionBlanc);
        damier.ajouterPion(8, pionBlanc);
        damier.ajouterPion(39, pionBlanc); // Juste pour tester s'il prend le bon pion
        damier.ajouterPion(40, pionBlanc); // Juste pour tester s'il prend le bon pion
        Prise prisesN = partie.getPrisesPossibles(22);
        listeMeilleursDeplacements = prisesN.trouverMeilleursSuiteDePrises();

        suiteDesPrisesVoulus = new int[] {27, 37, 38, 28, 18, 8};
        suiteDesDeplacementsVoulus = new int[] {31, 42, 33, 22, 13, 2};

        indexDeplacement = 0;
        for (Prise d : listeMeilleursDeplacements.get(0)) {
            assertEquals(d.getPositionPrise(), suiteDesPrisesVoulus[indexDeplacement]);
            assertEquals(d.getPosition(), suiteDesDeplacementsVoulus[indexDeplacement]);
            indexDeplacement++;
        }
    }

    /**
     * Test la fonction getPrisesPossibles et trouve ensuite les déplacements obligatoire pour une dame.
     */
    @Test
    public void testGetPrisesPossiblesDames() {
        Damier damier = new Damier();

        // Les pions ennemis
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(19, pionNoir);
        damier.ajouterPion(45, pionNoir);
        damier.ajouterPion(24, pionNoir);

        PartieDames partie = new PartieDames(damier);
        // La dame
        Pion dameBlanche = new Dame(Pion.Couleur.Blanc);
        damier.ajouterPion(14, dameBlanche);

        Prise prisesDame = partie.getPrisesPossibles(14);
        List<List<Prise>> listeMeilleursDeplacements = prisesDame.trouverMeilleursSuiteDePrises();

        int[] suiteDesPrisesVoulus = new int[] {19};
        int[] suiteDesDeplacementVoulus = new int[] {23};
        int indexDeplacement = 0;
        for (Prise d : listeMeilleursDeplacements.get(0)) {
            assertEquals(d.getPositionPrise(), suiteDesPrisesVoulus[indexDeplacement]);
            assertEquals(d.getPosition(), suiteDesDeplacementVoulus[indexDeplacement]);
            indexDeplacement++;
        }
        listeMeilleursDeplacements.clear();

        // Les pions ennemis
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(7, pionBlanc);
        damier.ajouterPion(17, pionBlanc);
        damier.ajouterPion(27, pionBlanc);
        damier.ajouterPion(28, pionBlanc);
        damier.ajouterPion(30, pionBlanc);
        damier.ajouterPion(37, pionBlanc);
        damier.ajouterPion(38, pionBlanc);
        damier.ajouterPion(44, pionBlanc);
        damier.ajouterPion(46, pionBlanc);

        // La dame
        Pion dameNoire = new Dame(Pion.Couleur.Noir);
        damier.ajouterPion(19, dameNoire);

        Prise prisesDameN = partie.getPrisesPossibles(19);
        listeMeilleursDeplacements = prisesDameN.trouverMeilleursSuiteDePrises();

        int [][] suiteDesPrisesVoulus2 = new int[][] {{28, 38, 30, 14, 17, 27, 37},
                                                      {28, 27, 17, 14, 30, 38, 37}};
        int [][] suiteDesDeplacementVoulus2 = new int[][] {{32, 43, 25, 3, 21, 32, 41},
                                                           {32, 21, 3, 25, 43, 32, 41}};
        int indexlist = 0;
        for (List<Prise> listeMeilleurs : listeMeilleursDeplacements) {
            indexDeplacement = 0;
            for (Prise d : listeMeilleurs) {
                assertEquals(d.getPositionPrise(), suiteDesPrisesVoulus2[indexlist][indexDeplacement]);
                assertEquals(d.getPosition(), suiteDesDeplacementVoulus2[indexlist][indexDeplacement]);
                indexDeplacement++;
            }
            indexlist++;
        }
    }

    /**
     * Test les prises pour un pion.
     */
    @Test
    public void testPrisePion() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);

        // Le pion
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(22, pionNoir);

        // Les pions ennemis
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(27, pionBlanc);
        damier.ajouterPion(37, pionBlanc);
        damier.ajouterPion(38, pionBlanc);
        damier.ajouterPion(28, pionBlanc);
        damier.ajouterPion(18, pionBlanc);
        damier.ajouterPion(8, pionBlanc);
        damier.ajouterPion(39, pionBlanc); // just pour tester s'il prend le bon
        damier.ajouterPion(40, pionBlanc); // just pour tester s'il prend le bon

        Prise prisesN = partie.getPrisesPossibles(22);
        List<List<Prise>> listeMeilleursDeplacements = prisesN.trouverMeilleursSuiteDePrises();

        partie.prise(22, listeMeilleursDeplacements.get(0));

        assertNull(damier.inspecterCase(22));
        assertEquals(pionNoir, damier.inspecterCase(2));
        assertNull(damier.inspecterCase(27));
        assertNull(damier.inspecterCase(37));
        assertNull(damier.inspecterCase(38));
        assertNull(damier.inspecterCase(28));
        assertNull(damier.inspecterCase(18));
        assertNull(damier.inspecterCase(8));
        assertEquals(pionBlanc, damier.inspecterCase(39));
        assertEquals(pionBlanc, damier.inspecterCase(40));
    }

    /**
     * Test les prises pour une dame.
     */
    @Test
    public void testPriseDame() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);

        // Les pions ennemis
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(7, pionBlanc);
        damier.ajouterPion(17, pionBlanc);
        damier.ajouterPion(27, pionBlanc);
        damier.ajouterPion(28, pionBlanc);
        damier.ajouterPion(30, pionBlanc);
        damier.ajouterPion(37, pionBlanc);
        damier.ajouterPion(38, pionBlanc);
        damier.ajouterPion(44, pionBlanc);
        damier.ajouterPion(46, pionBlanc);
        damier.ajouterPion(27, pionBlanc);
        damier.ajouterPion(14, pionBlanc);

        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(19, pionNoir);
        damier.ajouterPion(40, pionNoir);
        damier.ajouterPion(24, pionNoir);

        // La dame
        Pion dameNoire = new Dame(Pion.Couleur.Noir);
        damier.ajouterPion(19, dameNoire);

        Prise prisesDameN = partie.getPrisesPossibles(19);
        List<List<Prise>> listeMeilleursDeplacements = prisesDameN.trouverMeilleursSuiteDePrises();

        partie.prise(19, listeMeilleursDeplacements.get(0));

        assertNull(damier.inspecterCase(28));
        assertNull(damier.inspecterCase(27));
        assertNull(damier.inspecterCase(17));
        assertNull(damier.inspecterCase(14));
        assertNull(damier.inspecterCase(30));
        assertNull(damier.inspecterCase(38));
        assertNull(damier.inspecterCase(37));

        assertEquals(dameNoire, damier.inspecterCase(41));
    }

    /**
     * Test les déplacements de base pour un pion.
     */
    @Test
    public void testDeplacerPion() {
        Damier damier = new Damier();
        damier.initialiser();
        PartieDames partie = new PartieDames(damier);
        partie.deplacer(31, 27);
        assertEquals(Pion.Couleur.Blanc, damier.inspecterCase(27).getCouleur());

        // Test les déplacements non diagonaux
        partie.deplacer(40, 30);
        assertNull(damier.inspecterCase(30));
        RepresentationDamier.getRepresentation(damier);

        // Test collision avec pions de même couleur
        partie.deplacer(33, 22);
        partie.deplacer(27, 22);
        assertEquals(Pion.Couleur.Blanc, damier.inspecterCase(27).getCouleur());

        // Test collision avec pions ennemis
        partie.deplacer(27, 18);
        assertEquals(Pion.Couleur.Blanc, damier.inspecterCase(27).getCouleur());

        // Test déplacement en sens inverse
        partie.deplacer(27, 31);
        assertEquals(Pion.Couleur.Blanc, damier.inspecterCase(27).getCouleur());
    }

    /**
     * Test les déplacements de base pour une dame.
     */
    @Test
    public void testDeplacerDame() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);

        damier.ajouterPion(7, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(18, new Dame(Pion.Couleur.Noir));

        partie.deplacer(7, 1);  // Transforme le pion en dame
        partie.deplacer(18, 40);
        partie.deplacer(1, 45);
        assertEquals(Pion.Couleur.Blanc, damier.inspecterCase(45).getCouleur());

        partie.changerTours();
        // Test les déplacements non diagonaux
        partie.deplacer(45, 35);
        assertNull(damier.inspecterCase(35));

        // Test collision avec pions de même couleur
        damier.ajouterPion(40, new Pion(Pion.Couleur.Blanc));
        partie.deplacer(45, 40);
        assertEquals(Pion.Couleur.Blanc, damier.inspecterCase(45).getCouleur());

        // Test collision avec pions ennemis
        partie.deplacer(45, 40);
        assertEquals(Pion.Couleur.Blanc, damier.inspecterCase(45).getCouleur());
    }

    /**
     * Test la transformation d'un pion en dame lorsqu'il arrive au bout du plateau.
     */
    @Test
    public void testTransformation() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);

        Pion pion1 = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(6, pion1);

        partie.deplacer(6, 1);
        assertEquals(Dame.class, damier.inspecterCase(1).getClass());

        Pion pion2 = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(41, pion2);

        partie.deplacer(41, 46);
        assertEquals(Dame.class, damier.inspecterCase(1).getClass());
    }

    /**
     * Test la fin de partie.
     */
    @Test
    public void testFinDePartie() {
        Damier damier = new Damier();
        PartieDames partie = new PartieDames(damier);

        // Damier vide
        assertTrue(partie.estFinDePartie());

        // Seulement un pion Blanc
        Pion pionB = new Pion(Pion.Couleur.Blanc);
        damier.ajouterPion(30, pionB);
        assertTrue(partie.estFinDePartie());

        // Reste pion des deux couleurs
        Pion pionN = new Pion(Pion.Couleur.Noir);
        damier.ajouterPion(25, pionN);
        assertFalse(partie.estFinDePartie());

        // Aucun déplacement possible
        for (int i = 21; i < 25; i++) {
            damier.ajouterPion(i, pionN);
        }
        for (int i = 26; i < 30; i++) {
            damier.ajouterPion(i, pionB);
        }
        assertTrue(partie.estFinDePartie());
    }
}