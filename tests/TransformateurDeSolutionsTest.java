import algorithms.TransformateurItinéraire;
import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe de test du transformateur de solutions.
 */
public class TransformateurDeSolutionsTest extends Application {

    private Itinéraire itinéraire;

    /**
     * Méthode permettant de charger les données de test
     * @return une liste d'itinéraires
     * @throws VehiculeCapacityOutOfBoundsException lorsque la capacité maximale des véhicules est dépassée pendant les transformations.
     * @throws ListOfClientsIsEmptyException en cas de génération de liste de clients vide lors des transformations.
     */
    public ArrayList<Itinéraire> donnéesDeTest() throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException {

        LinkedList<Client> clients = new LinkedList<Client>();
        Entrepôt entrepôt = new Entrepôt(44,44);
        Client premierClient = new Client(1, 63, 72, 4);
        Client deuxièmeClient = new Client(2, 83, 52, 9);
        Client troisiemeClient = new Client(3, 72, 22, 12);
        Client quatrièmeClient = new Client(4, 55, 22, 20);
        clients.add(premierClient);
        clients.add(deuxièmeClient);
        clients.add(troisiemeClient);
        clients.add(quatrièmeClient);
        this.itinéraire = new Itinéraire(clients, entrepôt, false);

        ArrayList<Itinéraire> itinéraires = new ArrayList<>();
        itinéraires.add(itinéraire);

        return itinéraires;

    }

    /**
     * Méthode pour tester la transformation d'échange entre deux clients d'un itinéraire
     * @throws VehiculeCapacityOutOfBoundsException
     * @throws ListOfClientsIsEmptyException
     * @throws EntrepôtNotFoundException
     */
    @Test
    public void testTransformationÉchangeClients() throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, EntrepôtNotFoundException {

        donnéesDeTest();

        // On récupère la taille de l'itinéraire avant la transformation
        int nbClientsItinéraireAvantTransformation = this.itinéraire.getListeClientsÀLivrer().size();

        // Création d'une liste chaînée contenant les clients à livrer de l'itinéraire avant la transformation
        LinkedList<Client> clientsItinéraireAvantTransformation = new LinkedList<>(this.itinéraire.getListeClientsÀLivrer());

        /* tests de transformation d'itinéraire dans une solution (transformation locale) */
        TransformateurItinéraire.transformationÉchange(this.itinéraire);
        // On récupère la taille de l'itinéraire après la transformation locale
        int nbClientsItinéraireAprèsTransformation = this.itinéraire.getListeClientsÀLivrer().size();
        // Liste chaînée contenant les clients à livrer de l'itinéraire après la transformation
        LinkedList<Client> clientsItinéraireAprèsTransformation = this.itinéraire.getListeClientsÀLivrer();
        //On vérifie si le nombre de clients de l'itinéraire est le même avant et après la transformation
        Assert.assertEquals(nbClientsItinéraireAvantTransformation, nbClientsItinéraireAprèsTransformation);
        System.out.println("= Test passed (méthode de transformation locale) ✅ Le nombre de client de l'itinéraire reste identique =");
        //On vérifie si l'itinéraire avant et après la transformation sont différents
        Assert.assertNotEquals(clientsItinéraireAvantTransformation, clientsItinéraireAprèsTransformation);
        System.out.println("= Test passed (méthode de transformation locale) ✅ L'itinéraire est bien changé =");

    }

    /**
     * Méthode pour tester la transformation d'insertion décalage dans un itinéraire
     * @throws VehiculeCapacityOutOfBoundsException
     * @throws ListOfClientsIsEmptyException
     * @throws EntrepôtNotFoundException
     */
    @Test
    public void testInsertionDécalageClients() throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, EntrepôtNotFoundException {

        donnéesDeTest();

        // On récupère la taille de l'itinéraire avant la transformation
        int nbClientsItinéraireAvantTransformation = this.itinéraire.getListeClientsÀLivrer().size();

        // Création d'une liste chaînée contenant les clients à livrer de l'itinéraire avant la transformation
        LinkedList<Client> clientsItinéraireAvantTransformation = new LinkedList<>(this.itinéraire.getListeClientsÀLivrer());

        /* tests de transformation d'itinéraire dans une solution (insertion décalage) */
        TransformateurItinéraire.insertionDécalage(this.itinéraire);
        // On récupère la taille de l'itinéraire après la transformation locale
        int nbClientsItinéraireAprèsTransformation = this.itinéraire.getListeClientsÀLivrer().size();
        // Liste chaînée contenant les clients à livrer de l'itinéraire après la transformation
        LinkedList<Client> clientsItinéraireAprèsTransformation = this.itinéraire.getListeClientsÀLivrer();
        //On vérifie si le nombre de clients de l'itinéraire est le même avant et après la transformation
        Assert.assertEquals(nbClientsItinéraireAvantTransformation, nbClientsItinéraireAprèsTransformation);
        System.out.println("= Test passed (méthode d'insertion décalage) ✅ Le nombre de client de l'itinéraire reste identique =");
        //On vérifie si l'itinéraire avant et après la transformation sont différents
        Assert.assertNotEquals(clientsItinéraireAvantTransformation, clientsItinéraireAprèsTransformation);
        System.out.println("= Test passed (méthode d'insertion décalage) ✅ L'itinéraire est bien changé =");

    }

    /**
     * Méthode pour tester la transformation d'inversion dans un itinéraire
     * @throws VehiculeCapacityOutOfBoundsException
     * @throws ListOfClientsIsEmptyException
     * @throws EntrepôtNotFoundException
     */
    @Test
    public void testInversionClients() throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, EntrepôtNotFoundException {

        donnéesDeTest();

        // On récupère la taille de l'itinéraire avant la transformation
        int nbClientsItinéraireAvantTransformation = this.itinéraire.getListeClientsÀLivrer().size();

        // Création d'une liste chaînée contenant les clients à livrer de l'itinéraire 3 avant la transformation
        LinkedList<Client> clientsItinéraireAvantTransformation = new LinkedList<>(this.itinéraire.getListeClientsÀLivrer());

        /* tests de transformation de d'itinéraire dans une solution (inversion) */
        TransformateurItinéraire.inversion(this.itinéraire);
        // On récupère la taille de l'itinéraire après la transformation locale
        int nbClientsItinéraireAprèsTransformation = this.itinéraire.getListeClientsÀLivrer().size();
        // Liste chaînée contenant les clients à livrer de l'itinéraire après la transformation
        LinkedList<Client> clientsItinéraireAprèsTransformation = this.itinéraire.getListeClientsÀLivrer();
        // On vérifie si le nombre de clients de l'itinéraire est le même avant et après la transformation
        Assert.assertEquals(nbClientsItinéraireAvantTransformation, nbClientsItinéraireAprèsTransformation);
        System.out.println("= Test passed (méthode d'inversion) ✅ Le nombre de client de l'itinéraire reste identique =");
        // On vérifie si l'itinéraire avant et après la transformation sont différents
        Assert.assertNotEquals(clientsItinéraireAvantTransformation, clientsItinéraireAprèsTransformation);
        System.out.println("= Test passed (méthode d'inversion) ✅ L'itinéraire est bien changé =");

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        CVRPWindow mainGui = new CVRPWindow(primaryStage);
        // affichage de la fenêtre principale
        mainGui.show();
        Itinéraire i = donnéesDeTest().get(0);
        mainGui.getController().drawItinéraire(i, Color.CORAL);

        CVRPWindow secondGui = new CVRPWindow(primaryStage);
        // affichage de la fenêtre principale
        secondGui.show();
        System.out.println();
        // Décommenter la ligne suivante pour tester la transformation d'échange
        testTransformationÉchangeClients();
        // Décommenter la ligne suivante pour tester la transformation d'insertion décalage
        //testInsertionDécalageClients();
        // Décommenter la ligne suivante pour tester la transformation d'inversion
        //testInversionClients();
        Itinéraire newItinéraire = this.itinéraire;
        System.out.println();
        secondGui.getController().drawItinéraire(newItinéraire, Color.CORAL);

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
