package cstjean.mobile.dames;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Le noeud d'un arbre des prises possibles.
 */
public class Prise {
    /**
     * Position d'arrivée après la prise.
     */
    private final int position;

    /**
     * Position de la prise.
     */
    private int positionPrise;

    /**
     * Une liste des prises possibles après celle-ci.
     */
    private final List<Prise> prisesEnfants;

    /**
     * Constructeur.
     *
     * @param position Position d'arrivée après la prise.
     * @param positionPrise Position du pion pris.
     */
    public Prise(int position, int positionPrise) {
        this.position = position;
        this.positionPrise = positionPrise;
        this.prisesEnfants = new ArrayList<>();
    }

    /**
     * Constructeur de la prise racine, représente la position de base.
     *
     * @param position Position de départ.
     */
    public Prise(int position) {
        this.position = position;
        this.prisesEnfants = new ArrayList<>();
    }

    /**
     * Donne les meilleurs suites de prises possibles.
     *
     * @return Une liste des suites de prises qui mange le plus de pions.
     */
    public List<List<Prise>> trouverMeilleursSuiteDePrises() {
        List<List<Prise>> chemins = new ArrayList<>();
        List<List<Prise>> cheminsLesPlusLongs = new ArrayList<>();
        List<Prise> cheminActuel = new ArrayList<>();

        rechercheMeilleurBranche(this, cheminActuel, chemins);

        // Trouve les plus longues chaines de prises (chemins)
        int maxLength = 0;
        for (List<Prise> chemin : chemins) {
            if (chemin.size() > maxLength) {
                maxLength = chemin.size();
            }
        }

        for (List<Prise> chemin : chemins) {
            if (chemin.size() == maxLength) {
                chemin.remove(0);
                cheminsLesPlusLongs.add(chemin);
            }
        }

        return cheminsLesPlusLongs;
    }

    /**
     * Fonction récursive qui créer les suites de prises possibles grace à l'arbre de prises possibles.
     *
     * @param prise Le noeud actuel.
     * @param priseActuel Le chemin (ou suite de prises) actuellement en construction.
     * @param chemins Liste des chemins (ou suite de prises) trouvés.
     */
    private void rechercheMeilleurBranche(Prise prise, List<Prise> priseActuel, List<List<Prise>> chemins) {
        if (prise == null) {
            return;
        }

        priseActuel.add(prise);

        if (prise.prisesEnfants.isEmpty()) {
            chemins.add(new ArrayList<>(priseActuel));
        } else {
            for (Prise priseEnfant : prise.prisesEnfants) {
                rechercheMeilleurBranche(priseEnfant, priseActuel, chemins);
            }
        }

        priseActuel.remove(priseActuel.size() - 1);
    }

    /**
     * Ajoute une prise à la liste des prises enfant.
     *
     * @param prise Le déplacement à ajouter.
     */
    public void addPriseEnfant(Prise prise) {
        prisesEnfants.add(prise);
    }

    /**
     * Donne la liste des prises enfants.
     *
     * @return Les prises enfants.
     */
    public List<Prise> getPrisesEnfants() {
        return prisesEnfants;
    }

    /**
     * Donne la position d'arrivée après la prise.
     *
     * @return La position d'arrivée.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Donne la position du pion pris.
     *
     * @return La position du pion pris.
     */
    public int getPositionPrise() {
        return positionPrise;
    }
}
