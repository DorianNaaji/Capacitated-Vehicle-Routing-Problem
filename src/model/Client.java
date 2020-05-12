package model;

import model.graph.Sommet;

/**
 * Classe représentant un client tel que défini par les fichiers .txt fournis.
 */
public class Client extends Sommet
{

    private int numeroClient;
    private int quantite;

    /**
     * Constructeur 4-params d'un client.
     * @param no le numéro de client.
     * @param posX la position X.
     * @param posY la position Y.
     * @param qte la quantité à livrer.
     */
    public Client(int no, int posX, int posY, int qte)
    {
        super(posX, posY);
        this.numeroClient = no;
        this.quantite = qte;
    }

    /**
     * Récupère le numéro de client.
     * @return le numéro de client.
     */
    public int getNumeroClient() {
        return numeroClient;
    }

    /**
     * Récupère la quantité à livrer.
     * @return la quantité à livrer.
     */
    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString()
    {
        return "Client{" +
                "numeroClient=" + numeroClient +
                ", positionX=" + this.getPositionX() +
                ", positionY=" + this.getPositionY() +
                ", quantite=" + quantite +
                '}';
    }
}
