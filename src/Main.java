
import customexceptions.VehiculeCapacityOutOfBoundsException;
import gui.CVRPWindow;
import javafx.application.Application;
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

        List<Fichier> fichiers = chargerFichiersTest();
        Fichier f0 = fichiers.get(0);
        ArrayList<Client> subList = new ArrayList<Client>();
        subList.add(f0.getClients().get(0));
        subList.add(f0.getClients().get(1));
        subList.add(f0.getClients().get(2));
        subList.add(f0.getClients().get(3));
        //subList.add(f0.getClients().get(4));

        Fichier f = new Fichier(subList, f0.getNomFichier(), f0.getEntrepôt());

        GrapheNonOrientéComplet gnoc = new GrapheNonOrientéComplet(f);
        System.out.println();

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
            for(Fichier f : fichiers)
            {
                System.out.println(f.toString());
            }
        }

        return fichiers;
    }
}
