package model;

import model.graph.Sommet;

import java.util.Objects;

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
     * Constructeur sans paramètre
     */
    public Client()
    {
        super();
        this.numeroClient = 0;
        this.quantite = 0;
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
    public int getNbMarchandisesÀLivrer() {
        return quantite;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return numeroClient == client.numeroClient &&
                quantite == client.quantite;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), numeroClient, quantite);
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
