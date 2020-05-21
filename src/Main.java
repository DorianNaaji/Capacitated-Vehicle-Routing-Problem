
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
        Fichier f0 = fichiers.get(16);

        /* tests de génération de solutions aléatoires sur le premier fichier */
        GénérateurSolutionsAléatoire générateurSolutionsAléatoire = new GénérateurSolutionsAléatoire(f0);
        ArrayList<Solution> solutionsAléatoires = générateurSolutionsAléatoire.générerXSolutionsAléatoire(1);



        Solution best = new Solution();
        best.setOptimisationGlobale(Double.MAX_VALUE);

        // ###### TEST AVEC recuitSimulé ###### //
        /*
        for(int i = 0; i < solutionsAléatoires.size(); i++)
        {
            Solution solutionCourante = solutionsAléatoires.get(i);
            Solution solutionOptimisée = RecuitSimulé.recuitSimulé(solutionCourante,
                    1000,
                    1000,
                    0.99,
                    Transformation.Transformation2Opt);

            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
                best = solutionOptimisée;
            }
        }
        */

        // ###### TEST AVEC recuitSimuléIt ###### //
        for(int i = 0; i < solutionsAléatoires.size(); i++)
        {
            Solution solutionOptimisée = new Solution();
            for (int j = 0; j < solutionsAléatoires.get(i).getItinéraires().size(); j++) {

                Itinéraire itinéraireOptimisé = RecuitSimulé.recuitSimuléIt(solutionsAléatoires.get(i).getItinéraires().get(j),
                        200,
                        100,
                        500,
                        0.95,
                        Transformation.Transformation2Opt);
                solutionOptimisée.ajouterTournée(itinéraireOptimisé);
            }
            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
                best = solutionOptimisée;
            }
        }

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
