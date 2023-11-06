package cstjean.mobile.dames;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * La classe d'un damier pour un jeu de dames. Contient des pions sur ses cases.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand.
 */
public class Damier {
    /**
     * Représentation d'une case qui ne contient pas de pion.
     */
    public static final String REPRESENTATIONCASEVIDE = "-";

    /**
     * Représentation d'une case qui ne contient pas de pion.
     */
    public static final int NB_CASES = 50;

    /**
     * Liste des pions sur le damier.
     */
    private final Map<Integer, Pion> damier;

    /**
     * Constructeur.
     */
    Damier() {
        damier = new LinkedHashMap<>();
    }

    /**
     * Constructeur.
     *
     * @param damier Liste des pions sur le damier.
     */
    Damier(Map<Integer, Pion> damier) {
        this.damier = damier;
    }

    /**
     * Créer un jeu de dame de base si le plateau est vide.
     */
    public void initialiser() {
        if (getNbDePions() == 0) {
            for (int i = 1; i <= 50; i++) {
                if (i <= 20) {
                    ajouterPion(i, new Pion(Pion.Couleur.Noir));
                }
                if (i > 30) {
                    ajouterPion(i, new Pion(Pion.Couleur.Blanc));
                }
            }
        }
    }

    /**
     * Place un pion sur le damier en l'ajoutant à la liste de pions selon l'index mentionné.
     *
     * @param pos Index du pion dans la liste.
     * @param pion Classe du pion à ajouter.
     */
    public void ajouterPion(int pos, Pion pion) {
        if (getNbDePions() <= 50 && (pos > 0 && pos <= 50)) {
            damier.put(pos, pion);
        }
    }

    /**
     * Retire un pion sur le damier à l'endroit mentionné.
     *
     * @param pos Index du pion dans la liste.
     */
    public void retirerPion(int pos) {
        damier.remove(pos);
    }

    /**
     * Donne le nombre de pions (valeurs non-nulle) dans la liste.
     *
     * @return Le nombre de pions sur le damier.
     */
    public int getNbDePions() {
        int cpt = 0;
        for (Map.Entry<Integer, Pion> pions : damier.entrySet()) {
            if (pions != null) {
                cpt++;
            }
        }
        return cpt;
    }

    /**
     * Donne la liste de pions avec leurs positions.
     *
     * @return Un Map avec comme clé la position sur le damier.
     */
    public Map<Integer, Pion> getMapDamier() {
        return damier;
    }

    /**
     * Regarde à une position précise s'il y a un pion et le retourne.
     *
     * @param pos Index de la liste inspecté.
     * @return Le pion à la position donnée.
     */
    public Pion inspecterCase(int pos) {
        return damier.get(pos);
    }

    /**
     * Donne la position de toutes les pions de la couleur spécifiée.
     *
     * @param couleur La couleur de pion à rechercher.
     * @return Liste de toutes les positions de pion de la couleur spécifiée.
     */
    public ArrayList<Integer> getToutesPositionsPionDuneCouleur(Pion.Couleur couleur) {
        ArrayList<Integer> listePosPion = new ArrayList<>();

        for (Map.Entry<Integer, Pion> pion : damier.entrySet()) {
            if (pion.getValue().getCouleur() == couleur) {
                listePosPion.add(pion.getKey());
            }
        }

        return listePosPion;
    }
}
