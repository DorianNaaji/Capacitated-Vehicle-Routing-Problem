package algorithms;

import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.*;
import model.graph.Sommet;
import sun.management.snmp.jvminstr.JvmRTBootClassPathEntryImpl;

import java.util.*;

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
        Solution solution = new Solution(itinéraires);

        return solution;
    }

    /**
     * Génère X solutions aléatoire, X étant défini par l'utilisateur.
     * @param X le nombre de solutions aléatoires à créer.
     * @return une liste de X solutions générées aléatoirement.
     */
    public ArrayList<Solution> générerXSolutionsAléatoire(int X) throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException {
        ArrayList<Solution> solutionsAléatoires = new ArrayList<Solution>();

        // tant que l'index est inférieur à X, on boucle sur la méthode générerUneSolutionAléatoire()
        for(int i = 0; i < X; i++)
        {
            solutionsAléatoires.add(this.générerUneSolutionAléatoire());
        }
        return solutionsAléatoires;
    }
}
