package algorithms;

import model.Itinéraire;

/**
 * Interface contenant un certain nombre de méthodes de transformations, agissant sur des itinéraires.
 * Il faut bien comprendre que les transformations agissent au niveau des itinéraires d'une solution, et non
 * pas entre les solutions.
 */
public interface ITransformateur
{
    /**
     * Transforme localement une solution, en échangeant de place deux clients choisis arbitrairement parmi i1.
     * @param i1 l'itinéraire au sein duquel la transformation sera effectué
     */
    public void transformationLocale(Itinéraire i1);

    /**
     * Effectue une opération d'insertion décalage d'un client sur un itinéraire.
     * Un client à une position i est pioché aléatoirement dans i1, retiré de i1, puis ajouté
     * à une position i+delta ou i-delta, delta étant aléatoirement généré.
     * @param i1 l'itinéraire sur lequel sera effectué l'insertion décalage.
     */
    public void insertionDécalage(Itinéraire i1);

    /**
     * Inverse une portion entière de k éléments appartenant à l'itinéraire i1.
     * Par exemple, soit ABCDEFG un itinéraire, et les k éléments = BCDE, alors k inversé = EDCB et
     * l'itinéraire inversé devient AEDCBFG.
     * @param i1 l'itinéraire sur lequel effectuer l'inversion
     */
    public void inversion(Itinéraire i1);

    /**
     * Effectue une transformation 2-opt sur un itinéraire donné.
     * La transformation 2-opt échange deux arêtes disjointes. Voir trello en ligne.
     * @param i1 l'itinéraire sur lequel effectuer la transformation 2-opt
     */
    public void transformation2opt(Itinéraire i1);
}
