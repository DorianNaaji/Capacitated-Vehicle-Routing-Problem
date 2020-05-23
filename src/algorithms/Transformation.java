package algorithms;

/**
 * Cette enum contient l'ensemble des différents types de transformations, elle pourrait être par exemple utile
 * pour passer en paramètre à nos algorithmes le type de transformation que l'on souhaite
 */
public enum Transformation
{
    /**
     * Transforme localement une solution, en échangeant de place deux clients choisis arbitrairement parmi itinéraire.
     */
    TransformationÉchange,
    /**
     * Effectue une opération d'insertion décalage d'un client sur un itinéraire.
     * Un client à une position i est pioché aléatoirement dans itinéraire, retiré de itinéraire, puis ajouté
     * à une position i+delta ou i-delta, delta étant aléatoirement généré.
     */
    InsertionDécalage,
    /**
     * Inverse une portion entière de k éléments appartenant à l'itinéraire.
     * Par exemple, soit ABCDEFG un itinéraire, et les k éléments = BCDE, alors k inversé = EDCB et
     * l'itinéraire inversé devient AEDCBFG.
     */
    Inversion,
    /**
     * Effectue une transformation 2-opt sur un itinéraire donné.
     * La transformation 2-opt échange deux arêtes disjointes.
     */
    Transformation2Opt,
}
