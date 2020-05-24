package model;

import model.graph.Sommet;

/**
 * Représente un entrepôt.
 */
public class Entrepôt extends Sommet
{
    /**
     * Constructeur 2-params d'un entrepôt
     *
     * @param posX la position X
     * @param posY la position Y
     */
    public Entrepôt(int posX, int posY)
    {
        // quantité et numéro valent toujours 0 pour un entrepôt
        super(posX, posY);
    }

    @Override
    public String toString()
    {
        return "Entrepôt{" +
                "positionX=" + this.getPositionX() +
                ", positionY=" + this.getPositionY() +
                '}';
    }
}
