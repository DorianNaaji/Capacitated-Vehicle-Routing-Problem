package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Une tournée représente une liste de clients à fournir, et donc un parcours.
 */
public class Tournée
{
    /**
     * La liste chaînée des clients
     */
    private LinkedList<Client> tournée;

    /**
     * La longueur totale du parcours
     */
    private double longueurTotale;

    /**
     * Constructeur d'une tournée
     * @param clients la liste des clients concernés par la tournée
     */
    public Tournée(LinkedList<Client> clients)
    {
        this.tournée = clients;
        //todo : calculer la longueur totale (dans cette classe) et l'initialiser.
        this.longueurTotale = 0.00;
    }

    /**
     *
     * @return la longueur totale de la tournée //todo : unité à définir (mètres je suppose)
     */
    public double getLongueurTotale()
    {
        return this.longueurTotale;
    }

    public void ajouterClient(Client c)
    {
        this.tournée.add(c);
        // todo : recalculer la longueur totale du parcours
        // **** A FAIRE
        this.longueurTotale = 0.00;
    }

    public void retirerClient(Client c)
    {
        this.tournée.remove(c);
        // todo : recalculer la longueur totale du parcours
        // **** A FAIRE
        this.longueurTotale = 0.00;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournée tournée1 = (Tournée) o;
        return Double.compare(tournée1.longueurTotale, longueurTotale) == 0 &&
                Objects.equals(tournée, tournée1.tournée);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(tournée, longueurTotale);
    }
}
