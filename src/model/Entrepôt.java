package model;

public class Entrepôt extends Client
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
        super(0, posX, posY, 0);
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
