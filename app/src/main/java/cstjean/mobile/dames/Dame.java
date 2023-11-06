package cstjean.mobile.dames;

/**
 * Classe pour un pion de type dame.
 *
 * @author Zachary Deschênes-Tremblay, Justin Morand
 */
public class Dame extends Pion {

    /**
     * Constructeur.
     */
    Dame() {
        super(Couleur.Blanc);
    }

    /**
     * Constructeur.
     *
     * @param couleur Noir ou blanc.
     */
    Dame(Couleur couleur) {
        super(couleur);
    }

    /**
     * Getter pour savoir comment représenter le pion dame sur le damier.
     *
     * @return Le caractère représentant la dame.
     */
    public char getRepresentation() {
        if (getCouleur() == Couleur.Noir) {
            return 'D';
        } else {
            return 'd';
        }
    }
}
