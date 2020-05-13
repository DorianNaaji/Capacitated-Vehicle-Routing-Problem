
import algorithms.GénérateurSolutionsAléatoire;
import customexceptions.EntrepôtNotFoundException;
import customexceptions.SetOfSommetsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import gui.CVRPWindow;
import gui.CVRPWindowController;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import inout.Loader;
import model.*;
import model.graph.Sommet;
import model.graph.GrapheNonOrientéComplet;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class Main extends Application
{
    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        CVRPWindow mainGui = new CVRPWindow(primaryStage);
        mainGui.show();

        /* tests d'affichage */
        List<Fichier> fichiers = chargerFichiersTest();
        Fichier f0 = fichiers.get(0);
        Fichier f0réduit = new Fichier(new ArrayList<>(f0.getClients().subList(0, 5)), f0.getNomFichier() + "_modifié", f0.getEntrepôt());

        //GénérateurSolutionsAléatoire générateurSolutionsAléatoire = new GénérateurSolutionsAléatoire(f0réduit);
        //Solution solution = générateurSolutionsAléatoire.générerUneSolutionAléatoire();


        GrapheNonOrientéComplet gnoc = new GrapheNonOrientéComplet(f0réduit);
        Itinéraire i = new Itinéraire(gnoc.getSommets());
        mainGui.getController().drawItinéraire(i, Color.CORNFLOWERBLUE);
        System.out.println("start ended");

    }

    public static void main(String[] args) throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, SetOfSommetsIsEmptyException {
        // test chargement fichiers
       /* List<Fichier> fichiers = chargerFichiersTest();
        int tailleFichiers = fichiers.size();
        for (int i=0; i<tailleFichiers; i++) {
            ArrayList<Client> clients = fichiers.get(i).getClients();
            Itinéraire itinéraire = new Itinéraire(clients);
            Client clicli = new Client(100, 1, 2, 3);
            System.out.println("Avant :" + itinéraire.getNbMarchandisesALivrer());
            itinéraire.ajouterClient(clicli);
            System.out.println("Après :" + itinéraire.getNbMarchandisesALivrer());
        } */

        HashSet<Sommet> sommets = new HashSet<>();
        Entrepôt entrepôt = new Entrepôt(3,3);
        Client premierClient = new Client(0, 1, 2, 4);
        sommets.add(entrepôt);
        sommets.add(premierClient);
        Itinéraire itinéraire = new Itinéraire(sommets);
        Client deuxiemeClient = new Client(2, 4, 5, 9);
        itinéraire.ajouterClient(deuxiemeClient);
        Client troisiemeClient = new Client(2, 1, 8, 12);
        itinéraire.ajouterClient(troisiemeClient);
        //1 Entrepôt + 3 Clients : Ce que l'on doit avoir : longueur totale = 16,1065 et nbMarchandises à livrer = 25 // OK !
        itinéraire.retirerClient(troisiemeClient);
        //1 Entrepôt + 2 Clients : Ce que l'on doit avoir : longueur totale = 8,714776 et nbMarchandises à livrer = 13 // OK !
        itinéraire.retirerClient(deuxiemeClient);
        //1 Entrepôt + 1 Client : Ce que l'on doit avoir : longueur totale = 4.4721359 et nbMarchandises à livrer = 4 // OK !
        itinéraire.ajouterClient(deuxiemeClient);
        itinéraire.ajouterClient(troisiemeClient);
        itinéraire.retirerClient(deuxiemeClient);
        //1 Entrepôt + 2 Clients : Ce que l'on doit avoir : longueur totale = 13.62123 et nbMarchandises à livrer = 16 // OK !
        Client quatrièmeClient = new Client(4, 3, 8, 84);
        itinéraire.ajouterClient(quatrièmeClient);
        //1 Entrepôt + 3 Clients : Ce que l'on doit avoir : longueur totale = 15,236067 et nbMarchandises à livrer = 100 // OK !
        Client cinquiemeClient = new Client(5, 3, 7, 2);
        itinéraire.ajouterClient(cinquiemeClient);
        //Même résultat que précédemment car on veut ajouter un client mais nbMarchandises > 100
        // donc on ne l'ajoute pas à l'itinéraire (la liste chaînée listeClientsÀLivrer)
        Client clientItin2 = new Client(1, 2, 4, 12);



        launch(args);

    }

    private static List<Fichier> chargerFichiersTest()
    {
        /* Chargement des fichiers */
        ArrayList<Fichier> fichiers = null;
        Loader loader = new Loader();
        try
        {
            fichiers = loader.chargerTousLesFichiers();
        }
        catch(Exception e)
        {
            System.err.println("Une erreur est survenue. Impossible de continuer l'exécution du programme : ");
            e.printStackTrace();
        }

        if(fichiers != null)
        {
            System.out.println(fichiers.size() + " fichiers ont été chargés");
            /*
            for(Fichier f : fichiers)
            {
                System.out.println(f.toString());
            }
            */
        }

        return fichiers;
    }
}
