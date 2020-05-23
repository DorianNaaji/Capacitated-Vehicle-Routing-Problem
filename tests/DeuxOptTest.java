import algorithms.TransformateurItinéraire;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Client;
import model.Entrepôt;
import model.Itinéraire;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class DeuxOptTest extends Application
{

    /* poule de clients de test, construit un itinéraire en hexagone */
    private final Entrepôt e = new Entrepôt(50, 50 - 10);
    private final Client sB = new Client(0, 50 + 20, 50, 1);
    private final Client sC = new Client(1, 50 + 20, 50 + 10, 1);
    private final Client sD = new Client(2, 50, 50 + 20, 1);
    private final Client sE = new Client(3, 50 - 20, 50 + 10, 1);
    private final Client sF = new Client(4, 50 - 20, 50, 1);

    private final String[] cheminsPossibles = new String[]
    {
            "ABFEDC", "ABCFED", "ABCDFE", "ACBDEF", "ADCBEF", "AEDCBF", "ABDCEF", "ABEDCF", "ABCEDF", "AFEDCB"
    };



    /**
     * On va placer un itinéraire en hexagone comme dans l'exemple du cours,
     * afin de tester nos résultats.
     */
    @Test
    public void transformation2OptTest() throws Exception
    {
        int bound = 5000;
        for(int i = 0; i < bound; i ++)
        {
            Itinéraire itinéraire = this.constructItinéraire();
            Itinéraire newItinéraire = TransformateurItinéraire.transformation2opt(itinéraire, null);
            String itinéraireString = this.convertItinéraireToString(newItinéraire);
            Assert.assertTrue(Stream.of(this.cheminsPossibles).anyMatch(s -> s.equals(itinéraireString)));
        }
        System.out.println("= transformation2OptTest passed ✅ =");
    }


    private String convertItinéraireToString(Itinéraire itinéraire)
    {
        String res = "A";
        for(int i = 0; i < itinéraire.getListeClientsÀLivrer().size(); i++)
        {
            switch(itinéraire.getListeClientsÀLivrer().get(i).getNumeroClient())
            {
                case 0 :
                    res += "B";
                    break;
                case 1 :
                    res += "C";
                    break;
                case 2 :
                    res += "D";
                    break;
                case 3 :
                    res += "E";
                    break;
                case 4 :
                    res += "F";
                    break;
            }
        }
        return res;
    }

    /**
     * Construit l'itinéraire à partir des clients et de l'entrepôt (attributs privés).
     * @return l'itinéraire initialisé.
     * @throws VehiculeCapacityOutOfBoundsException non applicable.
     */
    private Itinéraire constructItinéraire() throws VehiculeCapacityOutOfBoundsException
    {
        Itinéraire itinéraire = new Itinéraire(e);
        itinéraire.ajouterClient(sB);
        itinéraire.ajouterClient(sC);
        itinéraire.ajouterClient(sD);
        itinéraire.ajouterClient(sE);
        itinéraire.ajouterClient(sF);
        return itinéraire;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        CVRPWindow mainGui = new CVRPWindow(primaryStage);
        // affichage de la fenêtre principale
        mainGui.show();
        Itinéraire i = this.constructItinéraire();
        mainGui.getController().drawItinéraire(i, Color.CORAL);

        CVRPWindow secondGui = new CVRPWindow(primaryStage);
        // affichage de la fenêtre principale
        secondGui.show();
        System.out.println();
        Itinéraire newItinéraire = TransformateurItinéraire.transformation2opt(i, null);
        System.out.println();
        secondGui.getController().drawItinéraire(newItinéraire, Color.CORAL);

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
