package algorithms;

import model.Fichier;
import model.Solution;

import java.util.ArrayList;

/**
 * Classe dont le but est de générer des solutions aléatoires.
 * Une solution aléatoire est une solution qui comprend des itinéraires construits au hasard,
 * à partir des clients et de l'entrepôt d'un fichier donné.
 * Les solutions générées aléatoirement par ce générateur sont donc propres à un fichier donné.
 * La génération respecte les règles métiers, notamment le fait que :
 * — chaque itinéraire généré par la solution n'a pas un nombre de marchandises à livrer supérieure à la capacité de véhicule.
 * — tous les clients du fichier concerné par le générateur de solution aléatoire sont livrés grâce à la solution générée.
 */
public class GénérateurSolutionsAléatoire
{
    private Fichier fichierConcerné;

    public GénérateurSolutionsAléatoire(Fichier fichier)
    {
        this.fichierConcerné = fichier;
    }

    /**
     * Génère une solution aléatoire à partir du fichier ayant permis la construction du présent générateur.
     * @return une solution générée aléatoirement.
     */
    public Solution générerUneSolutionAléatoire()
    {
        //todo : générer une solution aléatoire.
        // Il faut se mettre d'accord sur ce qu'est une solution aléatoire ?
        return null;
    }

    /**
     * Génère X solutions aléatoire, X étant défini par l'utilisateur.
     * @param X le nombre de solutions aléatoires à créer.
     * @return une liste de X solutions générées aléatoirement.
     */
    public ArrayList<Solution> générerXSolutionsAléatoire(int X)
    {
        ArrayList<Solution> solutionsAléatoires = new ArrayList<Solution>();
        for(int i = 0; i < X; i++)
        {
            solutionsAléatoires.add(this.générerUneSolutionAléatoire());
        }
        return solutionsAléatoires;
    }
}
