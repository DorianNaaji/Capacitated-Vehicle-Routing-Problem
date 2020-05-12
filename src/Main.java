
import customexceptions.VehiculeCapacityOutOfBoundsException;
import gui.CVRPWindow;
import gui.CVRPWindowController;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import inout.Loader;
import model.*;
import model.graph.GrapheNonOrientéComplet;

import java.io.File;
import java.util.ArrayList;
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
        GrapheNonOrientéComplet gnoc = new GrapheNonOrientéComplet(f0réduit);
        Itinéraire i = new Itinéraire(gnoc.getSommets());
        mainGui.getController().drawItinéraire(i, Color.CORNFLOWERBLUE);
        System.out.println("start ended");
    }

    public static void main(String[] args) throws VehiculeCapacityOutOfBoundsException {
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

        }

        Itinéraire t = new Itinéraire(new ArrayList<Client>());

            t.ajouterClient(new Client(0, 0, 0, 50));
            t.ajouterClient(new Client(0, 0, 0, 24));
            t.ajouterClient(new Client(0, 0, 0, 1));

*/

        // lancement de l'interface
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
