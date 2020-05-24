package algorithms;

/**
 * Enum  qui décrit les différents types de recherche de voisinage possibles :
 * Basique ou complexe.
 */
public enum TypeDeRechercheVoisinage
{
    /**
     * En mode basique, on recherche les voisins en appliquant une transformation (la même) à tous les voisins
     * du voisinage, c'est-à-dire à tous les itinéraires d'une solution
     */
    BASIQUE,

    /**
     * En mode complexe, on recherche les voisins en mode basique mais également en appliquant une méta-transformation
     * entre solutions.
     */
    COMPLEXE
}
