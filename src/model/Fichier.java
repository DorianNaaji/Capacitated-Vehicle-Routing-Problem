package model;

import java.util.ArrayList;

/**
 * Classe représentant un fichier chargé en mémoire
 * Il contient :
 * - Une liste de clients
 * - Un nom de fichier
 * - Le nombre de clients récupérés
 */
public class Fichier
{
    private ArrayList<Client> clients;
    private String nomFichier;
    private int nbClientsRécupérés;
    private Entrepôt départ;

    /**
     * Constructeur 2-params pour un fichier.
     * @param clients les clients chargés.
     * @param nom le nom du fichier concerné.
     */
    public Fichier(ArrayList<Client> clients, String nom, Entrepôt _départ)
    {
        this.clients = clients;
        this.nomFichier = nom;
        this.nbClientsRécupérés = this.clients.size();
        this.départ = _départ;
    }

    /**
     *
     * @return la liste des cients chargés pour un fichier donné.
     */
    public ArrayList<Client> getClients()
    {
        return clients;
    }

    /**
     *
     * @return Le nom d'un fichier.
     */
    public String getNomFichier()
    {
        return nomFichier;
    }

    /**
     *
     * @return Le nombre de clients chargés en mémoire pour le fichier courant.
     */
    public int getNbClientsRécupérés()
    {
        return nbClientsRécupérés;
    }

    public Entrepôt getEntrepôt()
    {
        return this.départ;
    }

    @Override
    public String toString()
    {
        return "Fichier{" +
                " nomFichier='" + nomFichier + '\'' +
                ", nbClientsRécupérés=" + nbClientsRécupérés +
                ", départ=" + this.départ +
                '}';
    }
}
