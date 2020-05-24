package algorithms;

/**
 * Les différents type de génération de solution :
 * Aléatoire,
 * Aléatoire_Seuil,
 * Aléatoire_Unique,
 * Proche_En_Proche.
 * @see algorithms.Génération
 */
public enum Génération
{

    /**
     * La génération la plus basique, totalement aléatoire.
     * On prend un client aléatoirement, on le met dans un itinéraire,
     * puis on recommence, jusqu'à remplir tous nos itinéraires à la capacité maximale ou presque.
     *
     * On s'arrête une fois que l'ensemble des clients sont traités.
     */
    ALÉATOIRE,

    /**
     * Comme la génération aléatoire, sauf que l'on se fixe une valeur maximale à ne pas dépasser pour la quantité
     * de marchandises à livrer pour chaque itinéraire, au lieu de se fixer la capacité du véhicule en valeur maximale.
     */
    ALÉATOIRE_SEUIL,

    /**
     * La génération aléatoire unique consiste à prendre chacun des clients d'un fichier aléatoirement, puis de tous
     * les ajouter au sein d'un seul et unique itinéraire.
     */
    ALÉATOIRE_UNIQUE,

    /**
     * La génération proche en proche consiste à prendre un client aléatoirement, le placer dans un itinéraire, puis
     * prendre tous les clients le plus proche de ce client choisi aléatoirement, et de les placer dans un itinéraire.
     *
     * On recommence jusqu'à avoir rempli tous nos itinéraires et traité tous nos clients.
     */
    PROCHE_EN_PROCHE
}
