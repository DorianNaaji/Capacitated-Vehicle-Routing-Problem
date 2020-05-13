package model;

import java.util.Set;

public class Solution
{
    /**
     * Un ensemble d'itinéraires . Il n'existe pas deux itinéraires similaires, d'où l'utilisation d'un java.util.Set.
     */
    private Set<Itinéraire> itinéraires;

    /**
     * La somme de la longueur totale de tous les itinéraires.
     */
    private double optimisationGlobale;


    /**
     * Initialise un objet de type Solution
     * @param _itinéraires L'ensemble des itinéraires pour une solution
     */
    public Solution(Set<Itinéraire> _itinéraires)
    {
        // initialise l'attribut
        this.itinéraires = _itinéraires;
        // calcul de la longueur globale de la tournée
        this.optimisationGlobale = this.calculerLongueurGlobale();
    }

    /**
     * Ajoute une tournée à l'ensemble des solutions
     * @param t la tournée à ajouter
     */
    public void ajouterTournée(Itinéraire t)
    {
        // si l'ensemble ne contient pas la tournée à ajouter
        if(!this.itinéraires.contains(t))
        {
            // on ajoute la tournée à l'ensemble
            this.itinéraires.add(t);
            // on ajoute la longueur totale de la tournée à la longueur globale de la solution.
            this.optimisationGlobale += t.getLongueurTotale();
        }
        // si elle est déjà dans la tournée, exception car cas unexpected.
        else
        {
            throw new IllegalArgumentException("La tournée est déjà présente dans l'ensemble des itinéraires");
        }
        //todo : voir si d'autres opérations sont nécessaires lors de l'ajout d'une tournée
    }


    public void retirnerTournée(Itinéraire t)
    {
        // si l'ensemble contient bien la tournée à retirer
        if(this.itinéraires.contains(t))
        {
            // on la retire
            this.itinéraires.remove(t);
            // et on soustrait la longueur totale de la tournée à la longueur globale de la solution.
            this.optimisationGlobale -= t.getLongueurTotale();
        }
        // todo : voir si d'autres opérations sont nécessaires lorsqu'on retire une tournée.
    }

    /**
     * Calcule la longueur globale de l'ensemble des itinéraires
     * @return la longueur totale des itinéraires de l'ensemble.
     */
    private double calculerLongueurGlobale()
    {
        double optimisationGlobale = 0.00;
        for(Itinéraire t : this.itinéraires)
        {
            optimisationGlobale += t.getLongueurTotale();
        }
        return optimisationGlobale;
    }

    /**
     * Récupère l'ensemble des itinéraires de la solution courante.
     * @return les itinéraires.
     */
    public Set<Itinéraire> getItinéraires()
    {
        return this.itinéraires;
    }

    /**
     * Récupère la distance à parcourir pour effectuer l'ensemble des itinéraires.
     * @return la somme de la longueur des itinéraires.
     */
    public double getOptimisationGlobale()
    {
        return this.optimisationGlobale;
    }
}
