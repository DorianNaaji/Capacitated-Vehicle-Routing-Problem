import customexceptions.FileLoadException;
import inout.Loader;
import model.Client;
import model.Fichier;
import model.graph.GrapheNonOrientéComplet;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests pour la classe GrapheNonOrientéComplet.
 * @deprecated n'est pas utile.
 */
public class GrapheNonOrientéCompletTest
{
    /**
     * Teste si le nombre d'arêtes du graphe non orienté complet est correct.
     * @throws FileLoadException si erreur de chargement des fichiers.
     */
    @Test
    public void TestNbArêtesGrapheNonOrientéComplet() throws FileLoadException
    {
        Loader loader = new Loader();
        List<Fichier> fichiers = loader.chargerTousLesFichiers();
        Fichier f0 = fichiers.get(0);
        // Un premier test avec 5 Sommets : 4 Clients et 1 Entrepôt
        ArrayList<Client> subList = new ArrayList<Client>();
        subList.add(f0.getClients().get(0));
        subList.add(f0.getClients().get(1));
        subList.add(f0.getClients().get(2));
        subList.add(f0.getClients().get(3));
        //subList.add(f0.getClients().get(4));
        Fichier f = new Fichier(subList, f0.getNomFichier(), f0.getEntrepôt());
        // Test avec 5 sommets -> on doit avoir (n(n-1))/2 arêtes,
        // soit (5*4)/2 = 10
        GrapheNonOrientéComplet gnoc = new GrapheNonOrientéComplet(f);

        int nbArêtesExpected = (5*(5-1))/2;
        Assert.assertEquals(nbArêtesExpected, gnoc.getArêtes().size());



        // Un autre test avec i sommets, i étant le nombre de clients dans le fichier 0.
        GrapheNonOrientéComplet gnoc2 = new GrapheNonOrientéComplet(f0);
        int i = f0.getNbClientsRécupérés();
        // les clients + l'entrepôt = i + 1
        int nbSommets = i + 1;
        int nbArêtesExpected2 = (nbSommets*(nbSommets-1))/2;
        Assert.assertEquals(nbArêtesExpected2, gnoc2.getArêtes().size());
        System.out.println("= GrapheNonOrientéCompletTest passed ✅ =");
    }

}