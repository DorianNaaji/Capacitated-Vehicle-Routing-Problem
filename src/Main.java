
import algorithms.GénérateurSolutionsAléatoire;
import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import inout.Loader;
import model.*;
import model.graph.Sommet;
import model.graph.GrapheNonOrientéComplet;

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
        // affichage de la fenêtre principale
        mainGui.show();


        /* chargement de fichiers */
        List<Fichier> fichiers = chargerFichiersTest();
        Fichier f0 = fichiers.get(0);
        Fichier f0réduit = new Fichier(new ArrayList<>(f0.getClients().subList(0, 5)), f0.getNomFichier() + "_modifié", f0.getEntrepôt());

        /* tests de génération de solutions aléatoires sur le premier fichier */
        GénérateurSolutionsAléatoire générateurSolutionsAléatoire = new GénérateurSolutionsAléatoire(f0);
        Solution solution = générateurSolutionsAléatoire.générerUneSolutionAléatoire();

        /* tests de génération de solutions aléatoires sur le deuxième fichier */
        Fichier f1 = fichiers.get(1);
        GénérateurSolutionsAléatoire générateurSolutionsAléatoire2 = new GénérateurSolutionsAléatoire(f1);
        ArrayList<Solution> solutions = générateurSolutionsAléatoire2.générerXSolutionsAléatoire(3);


        /* tests d'affichage */
        LinkedList<Client> clientsTest = new LinkedList<>(f0réduit.getClients());
        Entrepôt entrepôtTest = f0réduit.getEntrepôt();
        Itinéraire i = new Itinéraire(clientsTest, entrepôtTest);
        mainGui.getController().drawItinéraire(i, Color.CORNFLOWERBLUE);

        /* tests d'affichage d'une solution aléatoire*/
        //mainGui.getController().drawSolution(solution);


        
        System.out.println("start ended");
    }

    public static void main(String[] args) throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException
    {
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
