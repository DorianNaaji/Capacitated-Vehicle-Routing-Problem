
import algorithms.GénérateurSolutionsAléatoire;
import algorithms.OptimisateurDeSolutions;
import algorithms.TransformateurDeSolutions;
import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import inout.Loader;
import model.*;

import java.util.ArrayList;
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
        List<Fichier> fichiers = chargerFichiers();
        Fichier f0 = fichiers.get(0);

        /* tests de génération de solutions aléatoires sur le premier fichier */
        //GénérateurSolutionsAléatoire générateurSolutionsAléatoire = new GénérateurSolutionsAléatoire(f0);
        //Solution solution = générateurSolutionsAléatoire.générerUneSolutionAléatoire();

        GénérateurSolutionsAléatoire générateurSolutionsAléatoire = new GénérateurSolutionsAléatoire(f0);
        Solution solution = générateurSolutionsAléatoire.générerUneSolutionAléatoire();

        OptimisateurDeSolutions optimisateurDeSolutions = new OptimisateurDeSolutions(solution);
        Solution solutionOptimisée = optimisateurDeSolutions.recuitSimulé(solution, 20, 1000, 0.95);
        /*Solution solutionOptimisée = new Solution();
        for (int i = 0; i < solution.getItinéraires().size(); i++) {
            Itinéraire itinéraireOptimisé = optimisateurDeSolutions.recuitSimuléIt(solution.getItinéraires().get(i), 200, 100, 10000, 0.95);
            solutionOptimisée.ajouterTournée(itinéraireOptimisé);
        }*/


        /* tests d'affichage d'une solution aléatoire*/
        mainGui.getController().drawSolution(solutionOptimisée);
        System.out.println("start ended");
    }

    public static void main(String[] args) throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException
    {
        launch(args);
    }

    public static List<Fichier> chargerFichiers()
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
