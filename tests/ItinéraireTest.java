import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.Client;
import model.Entrepôt;
import model.Itinéraire;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Classe de test pour la gérération d'un itinéraire
 */
public class ItinéraireTest {

    /**
     * Méthode permettant de vérifier si la longueur totale d'un itinéraire et le nombre total de marchandises sont
     * correctement calculés.
     * @throws VehiculeCapacityOutOfBoundsException lorsque la capacité maximale des véhicules est dépassée pendant les transformations.
     * @throws ListOfClientsIsEmptyException en cas de génération de liste de clients vide lors des transformations.
     */
    @Test
    public void TestLongueurTotaleEtNbMarchandises() throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException {

        LinkedList<Client> clients = new LinkedList<Client>();
        Entrepôt entrepôt = new Entrepôt(3,3);
        Client premierClient = new Client(0, 1, 2, 4);
        clients.add(premierClient);
        Itinéraire itinéraire = new Itinéraire(clients, entrepôt, false);
        Client deuxiemeClient = new Client(2, 4, 5, 9);
        itinéraire.ajouterClient(deuxiemeClient);
        Client troisiemeClient = new Client(2, 1, 8, 12);
        itinéraire.ajouterClient(troisiemeClient);

        double longueurTotaleExpected1 = utilitaires.Utilitaire.distanceEuclidienne(entrepôt.getPositionX(), entrepôt.getPositionY(), premierClient.getPositionX(), premierClient.getPositionY());
        longueurTotaleExpected1 += utilitaires.Utilitaire.distanceEuclidienne(premierClient.getPositionX(), premierClient.getPositionY(), deuxiemeClient.getPositionX(), deuxiemeClient.getPositionY());
        longueurTotaleExpected1 += utilitaires.Utilitaire.distanceEuclidienne(deuxiemeClient.getPositionX(), deuxiemeClient.getPositionY(), troisiemeClient.getPositionX(), troisiemeClient.getPositionY());
        longueurTotaleExpected1 += utilitaires.Utilitaire.distanceEuclidienne(troisiemeClient.getPositionX(), troisiemeClient.getPositionY(), entrepôt.getPositionX(), entrepôt.getPositionY());
        Assert.assertEquals(longueurTotaleExpected1,itinéraire.getLongueurTotale(),0.001);
        System.out.println("= Test 1 longueur totale passed ✅ (1 entrepôt + 3 clients) =");
        int nbMarchandisesExpected1 = premierClient.getNbMarchandisesÀLivrer() + deuxiemeClient.getNbMarchandisesÀLivrer() + troisiemeClient.getNbMarchandisesÀLivrer();
        Assert.assertEquals(nbMarchandisesExpected1, itinéraire.getNbMarchandisesALivrer());
        System.out.println("= Test 1 nombre de marchandises total passed ✅ (1 entrepôt + 3 clients) =");
        //1 Entrepôt + 3 Clients : Ce que l'on doit avoir : longueur totale = 16,1065 et nbMarchandises à livrer = 25 // OK !
        itinéraire.retirerClient(troisiemeClient);
        double longueurTotaleExpected2 = utilitaires.Utilitaire.distanceEuclidienne(entrepôt.getPositionX(), entrepôt.getPositionY(), premierClient.getPositionX(), premierClient.getPositionY());
        longueurTotaleExpected2 += utilitaires.Utilitaire.distanceEuclidienne(premierClient.getPositionX(), premierClient.getPositionY(), deuxiemeClient.getPositionX(), deuxiemeClient.getPositionY());
        longueurTotaleExpected2 += utilitaires.Utilitaire.distanceEuclidienne(deuxiemeClient.getPositionX(), deuxiemeClient.getPositionY(), entrepôt.getPositionX(), entrepôt.getPositionY());
        Assert.assertEquals(longueurTotaleExpected2,itinéraire.getLongueurTotale(),0.001);
        System.out.println("= Test 2 longueur totale passed ✅ (1 entrepôt + 2 clients) =");
        int nbMarchandisesExpected2 = premierClient.getNbMarchandisesÀLivrer() + deuxiemeClient.getNbMarchandisesÀLivrer();
        Assert.assertEquals(nbMarchandisesExpected2, itinéraire.getNbMarchandisesALivrer());
        System.out.println("= Test 2 nombre de marchandises total passed ✅ (1 entrepôt + 2 clients) =");
        //1 Entrepôt + 2 Clients : Ce que l'on doit avoir : longueur totale = 8,714776 et nbMarchandises à livrer = 13 // OK !
        itinéraire.retirerClient(deuxiemeClient);
        double longueurTotaleExpected3 = utilitaires.Utilitaire.distanceEuclidienne(entrepôt.getPositionX(), entrepôt.getPositionY(), premierClient.getPositionX(), premierClient.getPositionY()) * 2;
        Assert.assertEquals(longueurTotaleExpected3,itinéraire.getLongueurTotale(),0.001);
        System.out.println("= Test 3 longueur totale passed ✅ (1 entrepôt + 1 clients) =");
        int nbMarchandisesExpected3 = premierClient.getNbMarchandisesÀLivrer();
        Assert.assertEquals(nbMarchandisesExpected3, itinéraire.getNbMarchandisesALivrer());
        System.out.println("= Test 3 nombre de marchandises total passed ✅ (1 entrepôt + 1 clients) =");
        //1 Entrepôt + 1 Client : Ce que l'on doit avoir : longueur totale = 4.4721359 et nbMarchandises à livrer = 4 // OK !
        itinéraire.ajouterClient(deuxiemeClient);
        itinéraire.ajouterClient(troisiemeClient);
        itinéraire.retirerClient(deuxiemeClient);
        double longueurTotaleExpected4 = utilitaires.Utilitaire.distanceEuclidienne(entrepôt.getPositionX(), entrepôt.getPositionY(), premierClient.getPositionX(), premierClient.getPositionY());
        longueurTotaleExpected4 += utilitaires.Utilitaire.distanceEuclidienne(premierClient.getPositionX(), premierClient.getPositionY(), troisiemeClient.getPositionX(), troisiemeClient.getPositionY());
        longueurTotaleExpected4 += utilitaires.Utilitaire.distanceEuclidienne(troisiemeClient.getPositionX(), troisiemeClient.getPositionY(), entrepôt.getPositionX(), entrepôt.getPositionY());
        Assert.assertEquals(longueurTotaleExpected4,itinéraire.getLongueurTotale(),0.001);
        System.out.println("= Test 4 longueur totale passed ✅ (1 entrepôt + 2 clients) =");
        int nbMarchandisesExpected4 = premierClient.getNbMarchandisesÀLivrer() + troisiemeClient.getNbMarchandisesÀLivrer();
        Assert.assertEquals(nbMarchandisesExpected4, itinéraire.getNbMarchandisesALivrer());
        System.out.println("= Test 4 nombre de marchandises total passed ✅ (1 entrepôt + 2 clients) =");
        //1 Entrepôt + 2 Clients : Ce que l'on doit avoir : longueur totale = 13.62123 et nbMarchandises à livrer = 16 // OK !
        Client quatrièmeClient = new Client(4, 3, 8, 84);
        itinéraire.ajouterClient(quatrièmeClient);
        double longueurTotaleExpected5 = utilitaires.Utilitaire.distanceEuclidienne(entrepôt.getPositionX(), entrepôt.getPositionY(), premierClient.getPositionX(), premierClient.getPositionY());
        longueurTotaleExpected5 += utilitaires.Utilitaire.distanceEuclidienne(premierClient.getPositionX(), premierClient.getPositionY(), troisiemeClient.getPositionX(), troisiemeClient.getPositionY());
        longueurTotaleExpected5 += utilitaires.Utilitaire.distanceEuclidienne(troisiemeClient.getPositionX(), troisiemeClient.getPositionY(), quatrièmeClient.getPositionX(), quatrièmeClient.getPositionY());
        longueurTotaleExpected5 += utilitaires.Utilitaire.distanceEuclidienne(quatrièmeClient.getPositionX(), quatrièmeClient.getPositionY(), entrepôt.getPositionX(), entrepôt.getPositionY());
        Assert.assertEquals(longueurTotaleExpected5,itinéraire.getLongueurTotale(),0.001);
        System.out.println("= Test 5 longueur totale passed ✅ (1 entrepôt + 3 clients) =");
        int nbMarchandisesExpected5 = premierClient.getNbMarchandisesÀLivrer() + troisiemeClient.getNbMarchandisesÀLivrer() + quatrièmeClient.getNbMarchandisesÀLivrer();
        Assert.assertEquals(nbMarchandisesExpected5, itinéraire.getNbMarchandisesALivrer());
        System.out.println("= Test 5 nombre de marchandises total passed ✅ (1 entrepôt + 3 clients) =");
        //1 Entrepôt + 3 Clients : Ce que l'on doit avoir : longueur totale = 15,236067 et nbMarchandises à livrer = 100 // OK !
        Client cinquiemeClient = new Client(5, 3, 7, 2);
        itinéraire.ajouterClient(cinquiemeClient);
        Assert.assertEquals(longueurTotaleExpected5,itinéraire.getLongueurTotale(),0.001);
        System.out.println("= Test 6 longueur totale passed ✅ (1 entrepôt + 3 clients) =");
        Assert.assertEquals(nbMarchandisesExpected5, itinéraire.getNbMarchandisesALivrer());
        System.out.println("= Test 6 nombre de marchandises total passed ✅ (1 entrepôt + 3 clients) =");
        //Même résultat que précédemment car on veut ajouter un client mais nbMarchandises > 100
        // donc on ne l'ajoute pas à l'itinéraire (la liste chaînée listeClientsÀLivrer)

    }
}
