package model.graph;

import java.util.Objects;

/**
 * Représente une arête d'un graphe non orienté, avec un sommet1 et un sommet2.
 */
@Deprecated
public class Arête
{
    /**
     * Le sommet1 d'un côté de l'arête
     */
    private Sommet sommet1;

    /**
     * Le sommet2, de l'autre côté de l'arête
     */
    private Sommet sommet2;

    private double coût;

    /**
     * Constructeur à deux paramètres pour une Arête.
     * @param s1 le sommet d'où part l'arête.
     * @param s2 le sommet où arrive l'arête.
     */
    public Arête(Sommet s1, Sommet s2)
    {
        this.sommet1 = s1;
        this.sommet2 = s2;
        // Le coût est la distance entre les deux arêtes
        this.coût = utilitaires.Utilitaire.distanceEuclidienne
        (
            this.sommet1.getPositionX(),
            this.sommet1.getPositionY(),
            this.sommet2.getPositionX(),
            this.sommet2.getPositionY()
        );
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arête arête = (Arête) o;
        return Objects.equals(sommet1, arête.sommet1) &&
                Objects.equals(sommet2, arête.sommet2);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(sommet1, sommet2);
    }

    /**
     * Récupère le sommet1 de l'arête.
     * @return le sommet1 de l'arête.
     */
    public Sommet getSommet1()
    {
        return sommet1;
    }

    /**
     * Modifie la sommet1 de l'arête.
     * @param sommet1 le nouveau sommet1 de l'arête.
     */
    public void setSommet1(Sommet sommet1)
    {
        this.sommet1 = sommet1;
    }

    /**
     * Récupère le sommet2 de l'arête.
     * @return le sommet2 de l'arête.
     */
    public Sommet getSommet2()
    {
        return sommet2;
    }

    /**
     * Modifie la sommet2 de l'arête.
     * @param sommet2 le nouveau sommet2 de l'arête.
     */
    public void setSommet2(Sommet sommet2)
    {
        this.sommet2 = sommet2;
    }

    @Override
    public String toString()
    {
        return "Arête{ [" + this.sommet1 + "] <=> [" + this.sommet2 + "]}";
    }
}
