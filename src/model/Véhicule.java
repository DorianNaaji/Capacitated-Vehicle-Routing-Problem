package model;

import java.util.List;
import java.util.Set;

/**
 * Classe représentant un véhicule qui voyagera entre les différents clients...
 */
public class Véhicule {

    // Capacité maximale d'un véhicule.
    private int capacité = 100;

    private int capacitéNormale = 100;

    private boolean isInfinite;

    /**
     * Constructeur d'un véhicule ave capacité infinie si passé à true,
     * pour passer outre les règles métier lors de la génération de solution aléatoires avec itinéraire unique
     * et recherche tabou puis découpe de solution en itinéraires.
     * @see algorithms.Génération
     * @see algorithms.Tabou
     * @param _capacitéInfinie si true, passe la capacité à INTEGER.MAX_VALUE.
     */
    public Véhicule(boolean _capacitéInfinie) {
        if(_capacitéInfinie)
        {
            this.capacité = Integer.MAX_VALUE;
            this.isInfinite = true;
        }
    }

    public Véhicule(Véhicule v)
    {
        this.isInfinite = v.isInfinite;
        this.capacité = v.capacité;
    }

    /**
     * Constructeur vide.
     */
    public Véhicule() {

        this.isInfinite = false;
    }

    public boolean isInfinite()
    {
        return isInfinite;
    }

    /**
     * Remet la capacité du véhicule à la normale (100)
     */
    public void switchCapacitéNormale()
    {
        this.capacité = capacitéNormale;
        this.isInfinite = false;
    }

    /**
     * Remet la capacité du véhicule à l'infini
     */
    public void switchCapacitéInfinie()
    {
        this.capacité = Integer.MAX_VALUE;
        this.isInfinite = true;
    }

    /**
     * Méthode permetant de récupérer la capacité du véhicule.
     * @return Retourne la capacité du véhicule.
     */
    public int getCapacité() {
        return capacité;
    }

}
