package model.graph;

import java.util.Objects;

public class Sommet
{
    private int positionX;
    private int positionY;

    /**
     * Constructeur deux paramètres d'un sommet.
     * @param posX
     * @param posY
     */
    public Sommet(int posX, int posY)
    {
        this.positionX = posX;
        this.positionY = posY;
    }

    public double CalculCoût(Sommet voisin)
    {
        // Le coût est la distance entre deux sommets voisins.
        return utilitaires.Utilitaire.distanceEuclidienne
        (
            this.getPositionX(),
            this.getPositionY(),
            voisin.getPositionX(),
            voisin.getPositionY()
        );
    }

    /**
     * Récupère la position X du sommet dans le plan.
     * @return la position X du sommet dans le plan.
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Modifie la position X du sommet dans le plan.
     * @param positionX la nouvelle position.
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Récupère la position Y du sommet dans le plan.
     * @return la position Y du client du sommet dans le plan.
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Modifie la position Y du sommet dans le plan.
     * @param positionY la nouvelle position.
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sommet sommet = (Sommet) o;
        return positionX == sommet.positionX &&
                positionY == sommet.positionY;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(positionX, positionY);
    }

    @Override
    public String toString()
    {
        return "Sommet{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }
}
