package utilitaires;

import model.Client;

public class Utilitaire {

    //todo : SOMMET

    /**
     * Méthode permettant de calculer la distance euclidienne entre deux éléments
     * @param positionX1
     * @param positionX2
     * @param positionY1
     * @param positionY2
     * @return  la distance euclidienne entre deux éléments
     */
    public static double distanceEuclidienne(int positionX1, int positionY1, int positionX2, int positionY2) {

        double distanceEuclidienne = Math.sqrt(Math.pow(positionX2 - positionX1, 2) + Math.pow(positionY2 - positionY1, 2));

        return distanceEuclidienne;
    }

}
