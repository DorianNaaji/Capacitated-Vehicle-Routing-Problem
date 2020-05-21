
import algorithms.GénérateurSolutionsAléatoire;
import algorithms.RecuitSimulé;
import algorithms.Transformation;
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
        ArrayList<Solution> solutionsAléatoires = générateurSolutionsAléatoire.générerXSolutionsAléatoire(10);

//        //Solution s = générateurSolutionsAléatoire.générerUneSolutionAléatoire();
//        RecuitSimulé ods = new RecuitSimulé(solutionsAléatoires.get(0));
//        Solution optimisée = ods.recuitSimulé(solutionsAléatoires.get(0), 200, 1000, 0.95);
//        mainGui.getController().drawSolution(optimisée);

        Solution best = new Solution();
        best.setOptimisationGlobale(Double.MAX_VALUE);
        for(int i = 0; i < solutionsAléatoires.size(); i++)
        {
            Solution solutionCourante = solutionsAléatoires.get(i);
            Solution solutionOptimisée = RecuitSimulé.recuitSimulé(solutionCourante,
                    100,
                    100,
                    0.99,
                    Transformation.Inversion);

            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
                best = solutionOptimisée;
            }
        }

        /*Solution solutionOptimisée = new Solution();
        for (int i = 0; i < solution.getItinéraires().size(); i++) {
            Itinéraire itinéraireOptimisé = optimisateurDeSolutions.recuitSimuléIt(solution.getItinéraires().get(i), 200, 100, 10000, 0.95);
            solutionOptimisée.ajouterTournée(itinéraireOptimisé);
        }*/
        mainGui.getController().drawSolution(best);
        System.out.println(best.getOptimisationGlobale());

    }

    public static void main(String[] args)
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
        return fichiers;
    }
}
