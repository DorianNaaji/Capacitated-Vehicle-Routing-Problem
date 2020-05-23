import algorithms.TransformateurItinéraire;
import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

public class TransformateurDeSolutionsTest {

    @Test
    public void testTransformateurDeSolutions() throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, EntrepôtNotFoundException {

        LinkedList<Client> clients1 = new LinkedList<Client>();
        Entrepôt entrepôt = new Entrepôt(3,3);
        Client premierClient1 = new Client(1, 1, 2, 4);
        Client deuxièmeClient1 = new Client(2, 4, 5, 9);
        Client troisiemeClient1 = new Client(3, 1, 8, 12);
        clients1.add(premierClient1);
        clients1.add(deuxièmeClient1);
        clients1.add(troisiemeClient1);
        Itinéraire itinéraire1 = new Itinéraire(clients1, entrepôt);

        LinkedList<Client> clients2 = new LinkedList<Client>();
        Client premierClient2 = new Client(1, 3, 4, 5);
        Client deuxièmeClient2 = new Client(2, 1, 3, 2);
        Client troisièmeClient2 = new Client(3, 2, 7, 3);
        clients2.add(premierClient2);
        clients2.add(deuxièmeClient2);
        clients2.add(troisièmeClient2);
        Itinéraire itinéraire2 = new Itinéraire(clients2, entrepôt);

        LinkedList<Client> clients3 = new LinkedList<Client>();
        Client premierClient3 = new Client(1, 4, 0, 1);
        Client deuxièmeClient3 = new Client(2, 7, 5, 6);
        Client troisièmeClient3 = new Client(3, 6, 8, 14);
        clients3.add(premierClient3);
        clients3.add(deuxièmeClient3);
        clients3.add(troisièmeClient3);
        Itinéraire itinéraire3 = new Itinéraire(clients3, entrepôt);

        ArrayList<Itinéraire> itinéraires = new ArrayList<>();
        itinéraires.add(itinéraire1);
        itinéraires.add(itinéraire2);
        itinéraires.add(itinéraire3);

        Solution solution = new Solution(itinéraires);
        // On récupère la taille de l'itinéraire 1 avant la transformation
        int nbClientsItinéraire1AvantTransformation = itinéraire1.getListeClientsÀLivrer().size();
        // On récupère la taille de l'itinéraire 2 avant la transformation
        int nbClientsItinéraire2AvantTransformation = itinéraire2.getListeClientsÀLivrer().size();
        // On récupère la taille de l'itinéraire 3 avant la transformation
        int nbClientsItinéraire3AvantTransformation = itinéraire3.getListeClientsÀLivrer().size();
        // Création d'une liste chaînée contenant les clients à livrer de l'itinéraire 1 avant la transformation
        LinkedList<Client> clientsItinéraire1AvantTransformation = new LinkedList<>(itinéraire1.getListeClientsÀLivrer());
        // Création d'une liste chaînée contenant les clients à livrer de l'itinéraire 2 avant la transformation
        LinkedList<Client> clientsItinéraire2AvantTransformation = new LinkedList<>(itinéraire2.getListeClientsÀLivrer());
        // Création d'une liste chaînée contenant les clients à livrer de l'itinéraire 3 avant la transformation
        LinkedList<Client> clientsItinéraire3AvantTransformation = new LinkedList<>(itinéraire3.getListeClientsÀLivrer());

        /* tests de transformationd'itinéraire dans une solution (transformation locale) */
        TransformateurItinéraire.transformationLocale(itinéraire1);
        // On récupère la taille de l'itinéraire 1 après la transformation locale
        int nbClientsItinéraire1AprèsTransformation = itinéraire1.getListeClientsÀLivrer().size();
        // Liste chaînée contenant les clients à livrer de l'itinéraire 1 après la transformation
        LinkedList<Client> clientsItinéraire1AprèsTransformation = itinéraire1.getListeClientsÀLivrer();
        //On vérifie si le nombre de clients de l'itinéraire 1 est le même avant et après la transformation
        Assert.assertEquals(nbClientsItinéraire1AvantTransformation, nbClientsItinéraire1AprèsTransformation);
        System.out.println("= Test passed (méthode de transformation locale) ✅ Le nombre de client de l'itinéraire 1 reste identique =");
        //On vérifie si l'itinéraire 1 avant et après la transformation sont différents
        Assert.assertNotEquals(clientsItinéraire1AvantTransformation, clientsItinéraire1AprèsTransformation);
        System.out.println("= Test passed (méthode de transformation locale) ✅ L'itinéraire 1 est bien changé =");

        /* tests de transformation d'itinéraire dans une solution (insertion décalage) */
        TransformateurItinéraire.insertionDécalage(itinéraire2);
        // On récupère la taille de l'itinéraire 2 après la transformation locale
        int nbClientsItinéraire2AprèsTransformation = itinéraire2.getListeClientsÀLivrer().size();
        // Liste chaînée contenant les clients à livrer de l'itinéraire 2 après la transformation
        LinkedList<Client> clientsItinéraire2AprèsTransformation = itinéraire2.getListeClientsÀLivrer();
        //On vérifie si le nombre de clients de l'itinéraire 2 est le même avant et après la transformation
        Assert.assertEquals(nbClientsItinéraire2AvantTransformation, nbClientsItinéraire2AprèsTransformation);
        System.out.println("= Test passed (méthode d'insertion décalage) ✅ Le nombre de client de l'itinéraire 2 reste identique =");
        //On vérifie si l'itinéraire 2 avant et après la transformation sont différents
        Assert.assertNotEquals(clientsItinéraire2AvantTransformation, clientsItinéraire2AprèsTransformation);
        System.out.println("= Test passed (méthode d'insertion décalage) ✅ L'itinéraire 2 est bien changé =");

        /* tests de transformation de d'itinéraire dans une solution (inversion) */
        TransformateurItinéraire.inversion(itinéraire3);
        // On récupère la taille de l'itinéraire 3 après la transformation locale
        int nbClientsItinéraire3AprèsTransformation = itinéraire3.getListeClientsÀLivrer().size();
        // Liste chaînée contenant les clients à livrer de l'itinéraire 3 après la transformation
        LinkedList<Client> clientsItinéraire3AprèsTransformation = itinéraire3.getListeClientsÀLivrer();
        // On vérifie si le nombre de clients de l'itinéraire 3 est le même avant et après la transformation
        Assert.assertEquals(nbClientsItinéraire3AvantTransformation, nbClientsItinéraire3AprèsTransformation);
        System.out.println("= Test passed (méthode d'inversion) ✅ Le nombre de client de l'itinéraire 3 reste identique =");
        // On vérifie si l'itinéraire 3 avant et après la transformation sont différents
        Assert.assertNotEquals(clientsItinéraire3AvantTransformation, clientsItinéraire3AprèsTransformation);
        System.out.println("= Test passed (méthode d'inversion) ✅ L'itinéraire 3 est bien changé =");

    }
}
