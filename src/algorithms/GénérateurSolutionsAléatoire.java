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

      /*  Set<Sommet> sommets = new HashSet<>();
        Set<Sommet> sommets2 = new HashSet<>();
        Random random = new Random();
        Entrepôt entrepôt = this.fichierConcerné.getEntrepôt();
        int premierIndexAléatoire = random.nextInt(fichierConcerné.getClients().size());
        ArrayList<Client> clients = this.fichierConcerné.getClients();
        sommets.add(entrepôt);
        sommets2.add(entrepôt);
        //sommets.add(clients.get(premierIndexAléatoire));
        //clients.remove(premierIndexAléatoire);
        //wgile boolean : if ajouter client rien else
        Itinéraire itinéraire1 = new Itinéraire(sommets);
        Itinéraire itinéraire2 = new Itinéraire(sommets2);
        while (clients.size() > 0) {
            int indexAléatoire = random.nextInt(fichierConcerné.getClients().size());
            //sommets.add(clients.get(indexAléatoire));
            boolean ajoutClient = itinéraire1.ajouterClient(clients.get(indexAléatoire));
            //clients.remove(indexAléatoire);

            if(ajoutClient = false) {

                itinéraire1.ajouterClient(clients.get(indexAléatoire));
                clients.remove(indexAléatoire);

            } else {
                Itinéraire it = new Itinéraire(sommets);
                sommets.add(clients.get(indexAléatoire));
                clients.remove(indexAléatoire);
            }

        }

        Set<Itinéraire> itinéraires = new HashSet<>();
        itinéraires.add(itinéraire1);
        //itinéraires.add(itinéraire2);
        Solution solution = new Solution(itinéraires);
        System.out.println("oOOOOO");*/

        //wgile boolean : if ajouter client rien else

        Random random = new Random();

        ArrayList<Client> clients = new ArrayList<>(this.fichierConcerné.getClients());
        Entrepôt entrepôt = this.fichierConcerné.getEntrepôt();
        ArrayList<Itinéraire> itinéraires = new ArrayList<>();
        LinkedList<Client> sommets = new LinkedList<>();
        Itinéraire itinéraire = new Itinéraire(entrepôt);


        while (clients.size() > 0) {
            int premierIndexAléatoire = random.nextInt(fichierConcerné.getClients().size());
            boolean ajoutClient = itinéraire.ajouterClient(clients.get(premierIndexAléatoire));
            if(ajoutClient) {
                sommets.add(clients.get(premierIndexAléatoire));
                clients.remove(premierIndexAléatoire);
            }
            else {


            }

        }




        //todo : générer une solution aléatoire.
        // Il faut se mettre d'accord sur ce qu'est une solution aléatoire ?
        return null;
    }

    /**
     * Génère X solutions aléatoire, X étant défini par l'utilisateur.
     * @param X le nombre de solutions aléatoires à créer.
     * @return une liste de X solutions générées aléatoirement.
     */
 /*   public ArrayList<Solution> générerXSolutionsAléatoire(int X)
    {
        ArrayList<Solution> solutionsAléatoires = new ArrayList<Solution>();
        for(int i = 0; i < X; i++)
        {
            solutionsAléatoires.add(this.générerUneSolutionAléatoire());
        }
        return solutionsAléatoires;
    }*/
}
