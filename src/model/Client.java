package model;

/**
 * Classe représentant un client tel que défini par les fichiers .txt fournis
 */
public class Client {

    private int numeroClient;
    private int positionX;
    private int positionY;
    private int quantite;

    /**
     * Constructeur 4-params d'un client
     * @param no le numéro de client
     * @param posX la position X
     * @param posY la position Y
     * @param qte la quantité à livrer
     */
    public Client(int no, int posX, int posY, int qte)
    {
        this.numeroClient = no;
        this.positionX = posX;
        this.positionY = posY;
        this.quantite = qte;
    }

    /**
     *
     * @return le numéro de client
     */
    public int getNumeroClient() {
        return numeroClient;
    }

    public void setNumeroClient(int numeroClient) {
        this.numeroClient = numeroClient;
    }

    /**
     *
     * @return la position X du client
     */
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     *
     * @return la position Y du client
     */
    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     *
     * @return la quantité à livrer
     */
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString()
    {
        return "Client{" +
                "numeroClient=" + numeroClient +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", quantite=" + quantite +
                '}';
    }
}
