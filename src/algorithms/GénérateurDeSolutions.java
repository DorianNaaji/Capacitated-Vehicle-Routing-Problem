package algorithms;

import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.*;
import model.graph.Sommet;
import sun.management.snmp.jvminstr.JvmRTBootClassPathEntryImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import static algorithms.Génération.ALÉATOIRE;
import static algorithms.Génération.ALÉATOIRE_SEUIL;

/**
 * Classe dont le but est de générer des solutions aléatoires.
 * Une solution aléatoire est une solution qui comprend des itinéraires construits au hasard,
 * à partir des clients et de l'entrepôt d'un fichier donné.
 * Les solutions générées aléatoirement par ce générateur sont donc propres à un fichier donné.
 * La génération respecte les règles métiers, notamment le fait que :
 * — chaque itinéraire généré par la solution n'a pas un nombre de marchandises à livrer supérieure à la capacité de véhicule.
 * — tous les clients du fichier concerné par le générateur de solution aléatoire sont livrés grâce à la solution générée.
 */
public class GénérateurDeSolutions
{
    private Fichier fichierConcerné;

    public GénérateurDeSolutions(Fichier fichier)
    {
        this.fichierConcerné = fichier;
    }

    /**
     * Génère une solution aléatoire à partir du fichier ayant permis la construction du présent générateur.
     * @return une solution générée aléatoirement.
     */
    public Solution générerUneSolutionAléatoire() throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException {

        Random random = new Random();

        // création d'une liste contenant les clients présents dans le fichier
        ArrayList<Client> clients = new ArrayList<>(this.fichierConcerné.getClients());
        // récupération de l'entrêpot du fichier
        Entrepôt entrepôt = this.fichierConcerné.getEntrepôt();
        // création d'un premier itinéraire sans client (il y a seulement l'entrepôt dans l'itinéraire)
        Itinéraire itinéraire = new Itinéraire(entrepôt);
        // on crée une liste d'Itinéraire
        ArrayList<Itinéraire> itinéraires = new ArrayList<>();
        // on ajoute itinéraire à la liste d'Itinéraire
        itinéraires.add(itinéraire);

        // tant qu'il reste des clients dans le fichier
        while (clients.size() > 0) {

            // on crée un index aléatoire
            int indexAléatoire = random.nextInt(clients.size());

            // si nous pouvons ajouter un client choisi aléatoirement dans la liste de clients au dernier Itinéraire ajouté à la liste d'itinéraires
            // (si en ajoutant le client la capacité du véhicule ne dépasse pas 100)
            if (itinéraires.get(itinéraires.size() - 1).ajouterClient(clients.get(indexAléatoire))) {
                // on supprime le client de la liste pour s'occuper des clients restants
                clients.remove(indexAléatoire);
            }
            // sinon...
            else {
                // on crée un nouvel itinéraire sans client (il y a seulement l'entrepôt dans l'itinéraire)
                Itinéraire nouvelItinéraire = new Itinéraire(entrepôt);
                // on ajoute le client au nouvel itinéraire
                nouvelItinéraire.getListeClientsÀLivrer().add(clients.get(indexAléatoire));
                // on supprime le client de la liste pour s'occuper des clients restants
                clients.remove(indexAléatoire);
                // on ajoute le nouvel itinéraire à la liste d'itinéraires
                itinéraires.add(nouvelItinéraire);
            }

        }

        // création d'une solution générée aléatoirement contenant les itinéraires
        return new Solution(itinéraires);
    }

    /**
     * Génère une solution aléatoire qui ne dépasse pas une limite fixée pour le nombre de marchandises à livrer
     * de chaque itinériare.
     * @param limite le seuil au-délà duquel l'ajout de clients dans un itinéraire sera stoppé.
     * @return une solution
     */
    public Solution générerUneSolutionAléatoire(int limite)
    {
        //todo
        throw new NotImplementedException();
    }

    /**
     *
     * @return
     */
    public Solution générerUneSolutionProcheEnProche()
    {
        //todo
        throw new NotImplementedException();
    }

    /**
     * Génère X solutions aléatoire, X étant défini par l'utilisateur.
     * @param X le nombre de solutions aléatoires à créer.
     * @param typeGénération le type de génération à suivre.
     * @param seuil le sueil fixé pour la génération de solution aléatoire avec seuil.
     * @return une liste de X solutions générées aléatoirement.
     */
    public ArrayList<Solution> générerXSolutionsAléatoire(int X, Génération typeGénération, int seuil) throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException {
        ArrayList<Solution> solutionsAléatoires = new ArrayList<Solution>();

        // tant que l'index est inférieur à X, on boucle sur la méthode générerUneSolutionAléatoire()
        for(int i = 0; i < X; i++)
        {
            switch(typeGénération)
            {
                case ALÉATOIRE:
                    solutionsAléatoires.add(this.générerUneSolutionAléatoire());
                    break;
                case ALÉATOIRE_SEUIL:
                    solutionsAléatoires.add(this.générerUneSolutionAléatoire(seuil));
                    break;
                case PROCHE_EN_PROCHE:
                    solutionsAléatoires.add(this.générerUneSolutionProcheEnProche());
                    break;
            }
        }
        return solutionsAléatoires;
    }
}
