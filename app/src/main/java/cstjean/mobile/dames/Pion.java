package cstjean.mobile.dames;

/**
 * La classe pour un pion de jeu de dames.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand.
 */
public class Pion {
    /**
     * La couleur du pion.
     */
    private final Couleur couleur;

    /**
     * Constructeur.
     */
    Pion() {
        this.couleur = Couleur.Blanc;
    }

    /**
     * Constructeur.
     *
     * @param couleur Noir ou blanc.
     */
    Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Getter.
     *
     * @return La couleur du pion.
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * La représentation du pion une fois sur le damier.
     *
     * @return Le caractère représentant la couleur du pion.
     */
    public char getRepresentation() {
        if (couleur == Couleur.Noir) {
            return 'P';
        } else {
            return 'p';
        }
    }

    /**
     * Les couleurs de pions possibles.
     */
    public enum Couleur { Noir, Blanc }
}
