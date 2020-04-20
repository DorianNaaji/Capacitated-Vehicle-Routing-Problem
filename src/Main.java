
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import inout.Loader;
import model.Fichier;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main extends Application
{
    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        CVRPWindow mainGui = new CVRPWindow(primaryStage);
        mainGui.show();
    }

    public static void main(String[] args)
    {
        // test chargement fichiers
        chargerFichiersTest();

        // lancement de l'interface
        launch(args);
    }

    private static void chargerFichiersTest()
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
            for(Fichier f : fichiers)
            {
                System.out.println(f.toString());
            }
        }
    }
}
