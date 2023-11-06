package cstjean.mobile.dames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe qui gère les actions liées à une partie de Dame.
 */
public class PartieDames {
    /**
     * Le damier qui sert de base à la partie.
     */
    private Damier damier;

    /**
     * Indique si le tour est à l'équipe des blancs ou des noirs.
     */
    private Pion.Couleur tourActuel = Pion.Couleur.Blanc;

    /**
     * Historique des tours précédents.
     */
    private List<Map<Integer, Pion>> historique = new ArrayList<>();

    /**
     * Constructeur.
     *
     * @param damier Le damier qui sert de base à la partie.
     */
    public PartieDames(Damier damier) {
        this.damier = damier;
    }

    /**
     * Remplace le damier par le damier dans l'historique indiqué.
     *
     * @param indexTour L'index de l'historique qui va renplaser le damier (2 = 2em coups de la partie)
     */
    public void retourHistorique(int indexTour) {
        if (indexTour <= historique.size() && indexTour >= 0) {
            if (indexTour % 2 == 0) {
                if (tourActuel == Pion.Couleur.Noir) {
                    changerTours();
                }
            } else {
                if (tourActuel == Pion.Couleur.Blanc) {
                    changerTours();
                }
            }

            this.damier = new Damier(historique.get(indexTour));

            List<Map<Integer, Pion>> nouvelHistorique = new ArrayList<>();

            for (int i = 0; i <= indexTour; i++) {
                nouvelHistorique.add(historique.get(i));
            }

            this.historique = nouvelHistorique;
        }
    }

    /**
     * Déplace un pion sur le damier si le déplacement est possible à l'endroit indiqué (n'inclus pas les prises).
     *
     * @param posInitiale La position d'où le pion partira.
     * @param posCible La position où le pion arrivera si possible.
     */
    public void deplacer(int posInitiale, int posCible) {
        Pion pionActuel = damier.inspecterCase(posInitiale);
        boolean estDame = pionActuel.getClass() == Dame.class;

        if (estDame) {
            if (tourActuel == Pion.Couleur.Blanc) {
                if (pionActuel.getCouleur() == Pion.Couleur.Blanc &&
                        getDeplacementsPossibleDame(posInitiale).contains(posCible)) {
                    changerTours();
                    damier.retirerPion(posInitiale);
                    damier.ajouterPion(posCible, pionActuel);
                }
            } else {
                if (pionActuel.getCouleur() == Pion.Couleur.Noir &&
                        getDeplacementsPossibleDame(posInitiale).contains(posCible)) {
                    changerTours();
                    damier.retirerPion(posInitiale);
                    damier.ajouterPion(posCible, pionActuel);
                }
            }
        } else {
            if (tourActuel == Pion.Couleur.Blanc) {
                if (pionActuel.getCouleur() == Pion.Couleur.Blanc &&
                        getDeplacementsPossible(posInitiale).contains(posCible)) {
                    changerTours();
                    damier.retirerPion(posInitiale);
                    damier.ajouterPion(posCible, pionActuel);
                    if (estBaseNoir(posCible)) {
                        transformerDames(posCible);
                    }
                }
            } else {
                if (pionActuel.getCouleur() == Pion.Couleur.Noir &&
                        getDeplacementsPossible(posInitiale).contains(posCible)) {
                    changerTours();
                    damier.retirerPion(posInitiale);
                    damier.ajouterPion(posCible, pionActuel);
                    if (estBaseBlanche(posCible)) {
                        transformerDames(posCible);
                    }
                }
            }
        }
    }

    /**
     * Éxécute la liste de prise donnée.
     *
     * @param posInitiale Position du pion qui fait la prise.
     * @param listePrise Liste de prise qui done les instructions de la prise.
     */
    public void prise(int posInitiale, List<Prise> listePrise) {
        changerTours();
        Pion pion = damier.inspecterCase(posInitiale);
        damier.retirerPion(posInitiale);

        for (Prise prise : listePrise) {
            damier.retirerPion(prise.getPositionPrise());
        }

        damier.ajouterPion(listePrise.getLast().getPosition(), pion);
    }

    /**
     * Retourne toutes les prises possibles pour le pion spécifié.
     *
     * @param position La position du pion duquel on recherchera les meilleures prises.
     * @return L'arbre de prises racine.
     */
    public Prise getPrisesPossibles(int position) {
        Pion pionActuel = damier.inspecterCase(position);
        Prise racine = new Prise(position);
        List<Integer> pionManges = new ArrayList<>();

        if (pionActuel.getClass() == Dame.class) {
            return ajouterPrisesPossiblesDame(racine, racine, pionActuel.getCouleur(), pionManges);
        } else {
            return ajouterPrisesPossibles(racine, racine, pionActuel.getCouleur(), pionManges);
        }
    }

    /**
     * Créer l'arbre des prises possibles de manière récursive.
     *
     * @param priseActuelle La prise actuellement en traitement.
     * @param racine La racine de l'arbre de prises.
     * @param couleurPion La couleur du pion de base.
     * @param pionManges La liste de pion déja mangé.
     * @return L'arbre de prises final.
     */
    public Prise ajouterPrisesPossibles(Prise priseActuelle, Prise racine,
                                        Pion.Couleur couleurPion, List<Integer> pionManges) {
        int positionDeBase = priseActuelle.getPosition();
        int caseActuelle = positionDeBase;

        for (int direction = 0; direction < 4; direction++) {
            int incrementPair = getIncrementPair(direction);
            int incrementImpair = getIncrementImpair(direction);

            caseActuelle += estRangeePaire(caseActuelle) ? incrementPair : incrementImpair;
            if (!estVide(caseActuelle) && !estBandeDroite(caseActuelle) && !estBandeGauche(caseActuelle)) {
                // Si un ennemi est présent
                if (estEnnemi(caseActuelle, couleurPion) && !pionManges.contains(caseActuelle)) {
                    int positionPieceMangee = caseActuelle;
                    caseActuelle += estRangeePaire(caseActuelle) ? incrementPair : incrementImpair;
                    // Si la case d'atterrissage après la prise est vide
                    if (estVide(caseActuelle) || caseActuelle == racine.getPosition()) {
                        pionManges.add(positionPieceMangee);

                        Prise nouvellePrise = new Prise(caseActuelle, positionPieceMangee);
                        priseActuelle.addPriseEnfant(nouvellePrise);

                        List<Integer> nouvelleListePionManges = new ArrayList<>(pionManges);
                        ajouterPrisesPossibles(nouvellePrise, racine, couleurPion, nouvelleListePionManges);
                    }
                }
            }

            caseActuelle = positionDeBase;
        }

        return racine;
    }

    /**
     * Créer l'arbre des prises possibles de manière récursive.
     *
     * @param priseActuelle La prise actuellement en traitement.
     * @param racine La racine de l'arbre de prises.
     * @param couleurPion La couleur du pion de base.
     * @param pionManges Les pions mangés dans des prises précédentes.
     * @return L'arbre de prises final.
     */
    public Prise ajouterPrisesPossiblesDame(Prise priseActuelle, Prise racine,
                                            Pion.Couleur couleurPion, List<Integer> pionManges) {
        int positionDeBase = priseActuelle.getPosition();
        int caseActuelle = positionDeBase;
        for (int direction = 0; direction < 4; direction++) {
            int incrementPair = getIncrementPair(direction);
            int incrementImpair = getIncrementImpair(direction);
            if (estBandeDroite(positionDeBase) &&
                    (direction == Direction.BasDroite.ordinal() || direction == Direction.HautDroite.ordinal())) {
                continue;
            } else if (estBandeGauche(positionDeBase) &&
                    (direction == Direction.BasGauche.ordinal() || direction == Direction.HautGauche.ordinal())) {
                continue;
            }

            boolean estBordure = false;
            int positionPieceMangee = 0;
            while (caseActuelle <= 50 && caseActuelle > 0) {
                if (estBordure) {
                    break;
                } else if (!estVide(caseActuelle) && caseActuelle != racine.getPosition()) {
                    // Si un ennemi est présent
                    if (estEnnemi(caseActuelle, couleurPion)) {
                        if (positionPieceMangee != 0 || estBandeGauche(caseActuelle) || estBandeDroite(caseActuelle)) {
                            break;
                        } else if (!pionManges.contains(caseActuelle)) {
                            positionPieceMangee = caseActuelle;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                } else if (positionPieceMangee != 0) {
                    Prise nouvellePrise = new Prise(caseActuelle, positionPieceMangee);
                    priseActuelle.addPriseEnfant(nouvellePrise);

                    List<Integer> nouvelleListePionManges = new ArrayList<>(pionManges);
                    nouvelleListePionManges.add(positionPieceMangee);
                    ajouterPrisesPossiblesDame(nouvellePrise, racine, couleurPion, nouvelleListePionManges);
                }

                // direction % 2 : Sert à savoir si direction est à gauche ou non
                estBordure = direction % 2 == 0 && estBandeGauche(caseActuelle) ||
                             direction % 2 != 0 && estBandeDroite(caseActuelle);

                caseActuelle += estRangeePaire(caseActuelle) ? incrementPair : incrementImpair;
            }

            caseActuelle = positionDeBase;
        }

        return racine;
    }

    /**
     * Donne la liste des déplacements valides pour un pion en particulier.
     *
     * @param position La position d'où les vérifications seront faitent.
     * @return Déplacements possibles pour le pion dans la position en question.
     */
    public List<Integer> getDeplacementsPossible(int position) {
        List<Integer> listeDeplacements;
        Pion pion = damier.inspecterCase(position);
        if (pion == null) {
            return null;
        }

        if (pion.getClass() == Dame.class) {
            listeDeplacements = getDeplacementsPossibleDame(position);
        } else {
            listeDeplacements = getDeplacementsPossiblePion(position);
        }

        return listeDeplacements;
    }

    /**
     * Donne tous les déplacements possibles pour un pion.
     *
     * @param position La position du pion dont l'on veut savoir les déplacements
     * @return La list de tous les déplacements possible du pion.
     */
    public List<Integer> getDeplacementsPossiblePion(int position) {
        Pion pion = damier.inspecterCase(position);

        Pion.Couleur couleurPion = pion.getCouleur();

        List<Integer> listeDeplacements = new ArrayList<>();

        int deplacementGauche;
        int deplacementDroite;

        if (couleurPion == Pion.Couleur.Noir) {
            if (estRangeePaire(position)) {
                deplacementGauche = 6;
                deplacementDroite = 5;
                if (estBandeDroite(position)) {
                    deplacementGauche = -1;
                }
            } else {
                deplacementGauche = 5;
                deplacementDroite = 4;
                if (estBandeGauche(position)) {
                    deplacementDroite = -1;
                }
            }
        } else {
            if (estRangeePaire(position)) {
                deplacementGauche = -5;
                deplacementDroite = -4;
                if (estBandeDroite(position)) {
                    deplacementDroite = -1;
                }
            } else {
                deplacementGauche = -6;
                deplacementDroite = -5;
                if (estBandeGauche(position)) {
                    deplacementGauche = -1;
                }
            }
        }

        if (deplacementGauche != -1 && estVide(position + deplacementGauche)) {
            listeDeplacements.add(position + deplacementGauche);
        }
        if (deplacementDroite != -1 && estVide(position + deplacementDroite)) {
            listeDeplacements.add(position + deplacementDroite);
        }

        return listeDeplacements;
    }

    /**
     * Donne tous les déplacements possibles pour une dame.
     *
     * @param pos La position de la dame dont l'on veut savoir les déplacements
     * @return La liste de tous les déplacements possible de la dame.
     */
    public List<Integer> getDeplacementsPossibleDame(int pos) { // (Premiere step)

        List<Integer> listeDeplacement = new ArrayList<>();

        int caseActuelle = pos;

        // Vérifie dans les 4 directions
        for (int direction = 0; direction < 4; direction++) {
            int incrementPair = getIncrementPair(direction);
            int incrementImpair = getIncrementImpair(direction);

            while (caseActuelle <= 50 && caseActuelle > 0) {
                if (estVide(caseActuelle)) {
                    listeDeplacement.add(caseActuelle);
                }

                // direction % 2 : Sert à savoir si direction est à gauche ou non
                if (direction % 2 == 0 && estBandeGauche(caseActuelle) ||
                        direction % 2 != 0 && estBandeDroite(caseActuelle)) {
                    break;
                }

                caseActuelle += estRangeePaire(caseActuelle) ? incrementPair : incrementImpair;
            }

            caseActuelle = pos;
        }

        return listeDeplacement;
    }

    /**
     * Vérifie si un pion ou une dame a une prise possible.
     *
     * @param position position du pion ou de la dame dont l'on veut savoir s'il y a une prise possible.
     * @return True si il y a une prise possible sinon false.
     */
    public boolean siPrisePossible(int position) {
        int caseActuelle = position;

        Pion pion = damier.inspecterCase(position);

        if (pion.getClass() == Dame.class) {
            for (int direction = 0; direction < 4; direction++) {
                int incrementPair = getIncrementPair(direction);
                int incrementImpair = getIncrementImpair(direction);

                // direction % 2 : Sert à savoir si direction est à gauche ou non
                if (direction % 2 == 0 && estBandeGauche(caseActuelle) ||
                        direction % 2 != 0 && estBandeDroite(caseActuelle)) {
                    // Comportement anormal: L'instruction est éxécutée mais n'est pas considérée dans le coverage.
                    continue;
                }

                while (caseActuelle <= 50 && caseActuelle > 0) {
                    if (!estVide(caseActuelle) && (caseActuelle != position)) {
                        if (estEnnemiPrenable(caseActuelle, pion.getCouleur())) {
                            if (estRangeePaire(caseActuelle)) {
                                return estVide(caseActuelle + incrementPair);
                            } else {
                                return estVide(caseActuelle + incrementImpair);
                            }
                        } else {
                            break;
                        }
                    }

                    if (estRangeePaire(caseActuelle)) {
                        caseActuelle += incrementPair;
                    } else {
                        caseActuelle += incrementImpair;
                    }
                }

                caseActuelle = position;
            }
        } else {
            if (estRangeePaire(position)) {
                return estEnnemiPrenable(position, -4) && estVide(position - 9) && !estBandeDroite(position) ||
                        estEnnemiPrenable(position, -5) && estVide(position - 11) && !estBandeGauche(position) ||
                        estEnnemiPrenable(position, 5) && estVide(position + 9) && !estBandeGauche(position) ||
                        estEnnemiPrenable(position, 6) && estVide(position + 11) && !estBandeDroite(position);
            } else {
                return estEnnemiPrenable(position, - 6) && estVide(position + 11) && !estBandeGauche(position) ||
                        estEnnemiPrenable(position, - 5) && estVide(position - 9) && !estBandeDroite(position) ||
                        estEnnemiPrenable(position, 4) && estVide(position + 9) && !estBandeGauche(position) ||
                        estEnnemiPrenable(position, 5) && estVide(position + 11) && !estBandeDroite(position);
            }
        }
        return false;
    }

    /**
     * Compare le pion initial avec le pion ciblé pour savoir s'ils ont ennemi et
     * Vérifie si le pion ciblé est un ennemi si à la possibilité d'être pris.
     *
     * @param pos La position du pion initial dont la comparaison est faite.
     * @param relationCible La relation entre le pion initial et le pion ciblé (ex. -6).
     * @return true si le pion ciblé est ennemi et prenable sinon false.
     */
    private boolean estEnnemiPrenable(int pos, int relationCible) {
        int posEnnemi = pos + relationCible;
        return estEnnemi(pos, relationCible) &&
                !estBandeDroite(posEnnemi) &&
                !estBandeGauche(posEnnemi) &&
                !estBaseBlanche(posEnnemi) &&
                !estBaseNoir(posEnnemi);
    }

    /**
     * Compare le pion initial avec le pion ciblé pour savoir s'ils ont ennemi et
     * Vérifie si le pion ciblé est un ennemi si à la possibilitée d'être pris.
     *
     * @param posEnnemi La position du pion ciblé dont la comparaison est faite.
     * @param couleur La couleur de pion que l'on veut comparer.
     * @return true si le pion ciblé est ennemi et prenable sinon false.
     */
    private boolean estEnnemiPrenable(int posEnnemi, Pion.Couleur couleur) {
        return estEnnemi(posEnnemi, couleur) &&
                !estBandeDroite(posEnnemi) &&
                !estBandeGauche(posEnnemi) &&
                !estBaseBlanche(posEnnemi) &&
                !estBaseNoir(posEnnemi);
    }

    /**
     * Compare le pion initial avec le pion ciblé pour savoir s'ils sont ennemis.
     *
     * @param pos La position du pion initial dont la comparaison est faite.
     * @param relationCible La relation entre le pion initial et le pion ciblé (ex. -6).
     * @return true si le pion ciblé est ennemi sinon false.
     */
    private boolean estEnnemi(int pos, int relationCible) {
        if (!estVide(pos + relationCible)) {
            return damier.inspecterCase(pos + relationCible).getCouleur() != damier.inspecterCase(pos).getCouleur();
        }
        return false;
    }

    /**
     * Compare le pion ciblé avec la couleur pour savoir s'ils sont ennemis.
     *
     * @param posEnnemi La position du pion ciblé dont la comparaison est faite.
     * @param couleur la couleur de pion que l'on veut comparer.
     * @return true si le pion ciblé est ennemi sinon false.
     */
    private boolean estEnnemi(int posEnnemi, Pion.Couleur couleur) {
        return damier.inspecterCase(posEnnemi).getCouleur() != couleur;
    }

    /**
     * Vérifie si la position donnée est sur une rangée paire.
     *
     * @param pos La position du pion à vérifier.
     * @return true si sur une rangée paire sinon false.
     */
    private boolean estRangeePaire(int pos) {
        final int[] ListePaire = {5, 4, 3, 2, 1};    // Représente une rangée paire
        return contains(ListePaire, pos % 10);
    }

    /**
     * Vérifie si la position donnée est vide.
     *
     * @param pos La position du pion à vérifier.
     * @return true si vide sinon false.
     */
    private boolean estVide(int pos) {
        return damier.inspecterCase(pos) == null;
    }

    /**
     * Vérifie si la position donnée est sur la bande droite selon les blancs.
     *
     * @param pos La position du pion à vérifier.
     * @return true si sur la bande droite sinon false.
     */
    private boolean estBandeDroite(int pos) {
        final int[] ListeCaseBandeDroite = {5, 15, 25, 35, 45};
        return contains(ListeCaseBandeDroite, pos);
    }

    /**
     * Vérifie si la position donnée est sur la bande gauche selon les blancs.
     *
     * @param pos La position du pion à vérifier.
     * @return true si sur la bande gauche sinon false.
     */
    private boolean estBandeGauche(int pos) {
        final int[] ListeCaseBandeGauche = {6, 16, 26, 36, 46};
        return contains(ListeCaseBandeGauche, pos);
    }

    /**
     * Vérifie si la position donnée est sur une base blanche.
     *
     * @param pos La position du pion à vérifier.
     * @return true si sur une base blanche sinon false.
     */
    private boolean estBaseBlanche(int pos) {
        final int DebutRangeeBaseBlanc = 46;
        return pos >= DebutRangeeBaseBlanc;
    }

    /**
     * Vérifie si la position donnée est sur une base noire.
     *
     * @param pos La position du pion à vérifier.
     * @return true si sur une base noire sinon false.
     */
    private boolean estBaseNoir(int pos) {
        final int DebutRangeeBaseNoir = 5;
        return pos <= DebutRangeeBaseNoir;
    }

    /**
     * Transforme un pion régulier en dame.
     *
     * @param pos Position du pion à transformer.
     */
    private void transformerDames(int pos) {
        damier.ajouterPion(pos, new Dame(damier.inspecterCase(pos).getCouleur()));
    }

    /**
     * Selon la notation Manoury, donne la valeur à ajouter pour un déplacement dans la direction indiquée si le pion
     * est sur une rangée paire.
     *
     * @param direction L'id de la direction de déplacement.
     * @return La valeur à ajouter pour déplacer la pièce dans la direction voulue.
     */
    private int getIncrementPair(int direction) {
        if (direction == Direction.BasGauche.ordinal()) {
            return 5;
        } else if (direction == Direction.BasDroite.ordinal()) {
            return 6;
        } else if (direction == Direction.HautGauche.ordinal()) {
            return -5;
        } else {
            return -4;
        }
    }

    /**
     * Selon la notation Manoury, donne la valeur à ajouter pour un déplacement dans la direction indiquée si le pion
     * est sur une rangée impaire.
     *
     * @param direction L'id de la direction de déplacement.
     * @return La valeur à ajouter pour déplacer la pièce dans la direction voulue.
     */
    private int getIncrementImpair(int direction) {
        if (direction == Direction.BasGauche.ordinal()) {
            return 4;
        } else if (direction == Direction.BasDroite.ordinal()) {
            return 5;
        } else if (direction == Direction.HautGauche.ordinal()) {
            return -6;
        } else {
            return -5;
        }
    }

    /**
     * Vérifie la couleur du joueur à qui s'est le tour de déplacer un pion.
     *
     * @return La couleur du joueur à qui c'est le tour.
     */
    public Pion.Couleur getTour() {
        return tourActuel;
    }

    /**
     * Donne le damier actuellement utilisé.
     *
     * @return Le damier qui sert de base à la partie.
     */
    public Damier getDamier() {
        return damier;
    }

    /**
     * Vérifie si plus aucun déplacements n'est possible ou si tous les pions d'une équipe ont été mangés.
     *
     * @return Un booléen qui indique si la partie est finie.
     */
    public boolean estFinDePartie() {
        boolean damierContientBlanc = false;
        boolean damierContientNoir = false;
        boolean aucunDeplacementPossible = true;

        ArrayList<Integer> listBlanc = damier.getToutesPositionsPionDuneCouleur(Pion.Couleur.Blanc);
        ArrayList<Integer> listNoir = damier.getToutesPositionsPionDuneCouleur(Pion.Couleur.Noir);

        if (!listBlanc.isEmpty()) {
            damierContientBlanc = true;
        }
        if (!listNoir.isEmpty()) {
            damierContientNoir = true;
        }

        for (int i = 0; i < 50; i++) {
            if (damier.inspecterCase(i) != null) {
                if (!getDeplacementsPossible(i).isEmpty()) {
                    aucunDeplacementPossible = false;
                }
            }
        }

        return (!damierContientNoir || !damierContientBlanc) || aucunDeplacementPossible;
    }

    /**
     * Change la couleur du joueur à qui s'est le tour de déplacer un pion (de blanc à noir ou noir à blanc).
     */
    public void changerTours() {
        Map<Integer, Pion> mapTour = new HashMap<>(damier.getMapDamier());
        historique.add(mapTour);
        if (tourActuel == Pion.Couleur.Blanc) {
            tourActuel = Pion.Couleur.Noir;
        } else {
            tourActuel = Pion.Couleur.Blanc;
        }
    }

    /**
     * Les directions possibles pour les déplacements sur le damier.
     */
    private enum Direction { BasGauche, BasDroite, HautGauche, HautDroite }

    /**
     * Vérifie si un array contient un élement (int).
     *
     * @return True si l'array contient l'élement (int) sinon false.
     */
    private boolean contains(int[] array, int elementCible) {
        for (final int element : array) {
            if (elementCible == element) {
                return true;
            }
        }
        return false;
    }
}
