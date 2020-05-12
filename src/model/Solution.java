package model;

import java.util.Set;

public class Solution
{
    /**
     * Un ensemble de tournées. Il n'existe pas deux tournées similaires, d'où l'utilisation d'un java.util.Set.
     * L'idée sera de fusionner des tournées entres elles pour optimiser la solution finale.
     */
    private Set<Itinéraire> tournées;

    /**
     * La somme de la longueur totale de toute les tournées.
     */
    private double optimisationGlobale;

    /**
     * Le nombre de véhicules nécessaires pour réaliser l'ensemble des tournées.
     * todo : calculer le nombre de véhicule nécessaire pour la solution donnée (lorsqu'on aura les algos)
     */
    private int nbVéhicules;

    /**
     * Initialise un objet de type Solution
     * @param _tournées L'ensemble des tournées pour une solution
     */
    public Solution(Set<Itinéraire> _tournées)
    {
        // initialise l'attribut
        this.tournées = _tournées;
        // calcul de la longueur globale de la tournée
        this.calculerLongueurGlobale();
        //todo : initialiser les véhicules (à faire lorsqu'on aura les algos)
    }

    /**
     * Ajoute une tournée à l'ensemble des solutions
     * @param t la tournée à ajouter
     */
    public void ajouterTournée(Itinéraire t)
    {
        // si l'ensemble ne contient pas la tournée à ajouter
        if(!this.tournées.contains(t))
        {
            // on ajoute la tournée à l'ensemble
            this.tournées.add(t);
            // on ajoute la longueur totale de la tournée à la longueur globale de la solution.
            this.optimisationGlobale += t.getLongueurTotale();
        }
        // si elle est déjà dans la tournée, exception car cas unexpected.
        else
        {
            throw new IllegalArgumentException("La tournée est déjà présente dans l'ensemble des tournées");
        }
        //todo : voir si d'autres opérations sont nécessaires lors de l'ajout d'une tournée
    }


    public void retirnerTournée(Itinéraire t)
    {
        // si l'ensemble contient bien la tournée à retirer
        if(this.tournées.contains(t))
        {
            // on la retire
            this.tournées.remove(t);
            // et on soustrait la longueur totale de la tournée à la longueur globale de la solution.
            this.optimisationGlobale -= t.getLongueurTotale();
        }
        // todo : voir si d'autres opérations sont nécessaires lorsqu'on retire une tournée.
    }

    /**
     * Calcule la longueur globale de l'ensemble des tournées
     * @return la longueur totale des tournées de l'ensemble.
     */
    private double calculerLongueurGlobale()
    {
        double optimisationGlobale = 0.00;
        for(Itinéraire t : this.tournées)
        {
            optimisationGlobale += t.getLongueurTotale();
        }
        return optimisationGlobale;
    }
}
