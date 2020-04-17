package model;

/**
 * Classe représentant un client tel que défini par les fichiers .txt fournis
 */
public class Client {

    private int numeroClient;
    private int positionX;
    private int positionY;
    private int quantite;

    public Client(String informations) {

        String clients[] = informations.split(";");

        String numeroClientString = clients[0];
        this.numeroClient = Integer.parseInt(numeroClientString);

        String positionXString = clients[1];
        this.positionX = Integer.parseInt(positionXString);

        String positionYString = clients[2];
        this.positionY = Integer.parseInt(positionYString);

        String quantiteString = clients[3];
        this.quantite = Integer.parseInt(quantiteString);

    }

    public int getNumeroClient() {
        return numeroClient;
    }

    public void setNumeroClient(int numeroClient) {
        this.numeroClient = numeroClient;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}
