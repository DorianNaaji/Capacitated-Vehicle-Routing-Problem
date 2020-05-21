
import algorithms.GénérateurSolutionsAléatoire;
import algorithms.TransformateurItinéraire;
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import inout.Loader;
import model.*;

import java.util.ArrayList;
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
        GénérateurSolutionsAléatoire générateurSolutionsAléatoire = new GénérateurSolutionsAléatoire(f0);
        Solution solution = générateurSolutionsAléatoire.générerUneSolutionAléatoire();

        /* tests de génération de solutions aléatoires sur le deuxième fichier */
        Fichier f1 = fichiers.get(1);
        GénérateurSolutionsAléatoire générateurSolutionsAléatoire2 = new GénérateurSolutionsAléatoire(f1);
        ArrayList<Solution> solutions = générateurSolutionsAléatoire2.générerXSolutionsAléatoire(3);

        /* tests de transformation de d'itinéraire dans une solution (transformation locale) */
        TransformateurItinéraire transformateurItinéraire = new TransformateurItinéraire();
        //TransformateurItinéraire.transformationLocale(solution.getItinéraires().get(0));

        /* tests de transformation de d'itinéraire dans une solution (insertion décalage) */
        transformateurItinéraire.insertionDécalage(solution.getItinéraires().get(0));

        /* tests de transformation de d'itinéraire dans une solution (inversion) */
        transformateurItinéraire.inversion(solution.getItinéraires().get(0));

        /* tests d'affichage d'une solution aléatoire*/
        mainGui.getController().drawSolution(solution);
        //System.out.println("start ended");
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private static List<Fichier> chargerFichiers()
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
        return fichiers;
    }
}
