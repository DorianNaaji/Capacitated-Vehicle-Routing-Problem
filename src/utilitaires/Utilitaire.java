package utilitaires;

import model.Client;

public class Utilitaire {

    /**
     * Méthode permettant de calculer la distance euclidienne entre deux éléments.
     * @param positionX1 la position x du premier élément.
     * @param positionX2 la position x du deuxiième élément.
     * @param positionY1 la position y du premier élément.
     * @param positionY2 la position y du deuxième élément.
     * @return  la distance euclidienne entre les deux éléments.
     */
    public static double distanceEuclidienne(int positionX1, int positionY1, int positionX2, int positionY2) {

        double distanceEuclidienne = Math.sqrt(Math.pow(positionX2 - positionX1, 2) + Math.pow(positionY2 - positionY1, 2));

        return distanceEuclidienne;
    }

}
