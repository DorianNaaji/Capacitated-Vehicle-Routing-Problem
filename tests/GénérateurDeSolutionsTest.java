import algorithms.GénérateurDeSolutions;
import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.Fichier;
import model.Itinéraire;
import model.Solution;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Classe de test pour la génération de solutions.
 */
public class GénérateurDeSolutionsTest
{

    /**
     * Méthode permettant de tester la génération de solutions aléatoires.
     * @throws EntrepôtNotFoundException lorsqu'il n'y a pas d'entrepôt dans l'itinéraire.
     * @throws VehiculeCapacityOutOfBoundsException lorsque la capacité maximale des véhicules est dépassée pendant les transformations.
     * @throws ListOfClientsIsEmptyException en cas de génération de liste de clients vide lors des transformations.
     */
    @Test
    public void testGénérateurSolutionsAléatoire() throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException {

        /* chargement des fichiers */
        List<Fichier> fichiers = Main.chargerFichiers();
        Fichier f0 = fichiers.get(0);

        int nbClientsDansLeFichierf0 = f0.getNbClientsRécupérés();
        int sommeNbClientsDansChaqueItinéraire = 0;

        /* tests de génération de solutions aléatoires sur le premier fichier */
        GénérateurDeSolutions générateurDeSolutions = new GénérateurDeSolutions(f0);
        Solution solution = générateurDeSolutions.générerUneSolutionAléatoire();

        // pour chaque itinéraire de la solution, on vérifie si le nombre de marchandises à livrer est bien inférieur ou égal à 100
        for (int i = 0; i < solution.getItinéraires().size(); i++) {
            Itinéraire itinéraire = solution.getItinéraires().get(i);
            Assert.assertTrue(itinéraire.getNbMarchandisesALivrer() <= 100);
            System.out.println("= Test passed ✅ L'itinéraire " + i + " contient " + itinéraire.getNbMarchandisesALivrer() + " marchandises à livrer =");
            sommeNbClientsDansChaqueItinéraire += itinéraire.getListeClientsÀLivrer().size();
        }

        // on vérifie si tous les clients du premier fichier ont été placés dans un itinéraire
        Assert.assertEquals(nbClientsDansLeFichierf0, sommeNbClientsDansChaqueItinéraire);
        System.out.println("= Test passed ✅ Tous les clients du premier fichier ont été placés dans un itinéraire =");

    }


    /**
     * Permet de tester la génération d'une solution aléatoire avec un seuil fixé sur la quantité maximale de marchandises
     * à transporter pour chaque itinéraire.
     * Ici, on teste avec 80.
     *
     * @throws EntrepôtNotFoundException lorsqu'il n'y a pas d'entrepôt dans l'itinéraire.
     * @throws VehiculeCapacityOutOfBoundsException lorsque la capacité maximale des véhicules est dépassée pendant les transformations.
     * @throws ListOfClientsIsEmptyException en cas de génération de liste de clients vide lors des transformations.
     */
    @Test
    public void testGénérerUneSolutionAléatoireLimite() throws EntrepôtNotFoundException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException
    {
        int limite = 80;
        System.out.println();
        System.out.println("= Test Génération avec limite (" + limite + ")");

        List<Fichier> fichiers = Main.chargerFichiers();
        Fichier f = fichiers.get(14);

        int nbClientsDansLeFichierf0 = f.getNbClientsRécupérés();
        int sommeNbClientsDansChaqueItinéraire = 0;

        GénérateurDeSolutions générateurDeSolutions = new GénérateurDeSolutions(f);
        Solution solution = générateurDeSolutions.générerUneSolutionAléatoire(limite);


        // pour chaque itinéraire de la solution, on vérifie si le nombre de marchandises à livrer est bien inférieur ou égal à limite
        for (int i = 0; i < solution.getItinéraires().size(); i++) {
            Itinéraire itinéraire = solution.getItinéraires().get(i);
            Assert.assertTrue(itinéraire.getNbMarchandisesALivrer() <= limite);
            System.out.println("= Test passed ✅ L'itinéraire " + i + " contient " + itinéraire.getNbMarchandisesALivrer() + " marchandises à livrer =");
            sommeNbClientsDansChaqueItinéraire += itinéraire.getListeClientsÀLivrer().size();
        }

        // on vérifie si tous les clients du premier fichier ont été placés dans un itinéraire
        Assert.assertEquals(nbClientsDansLeFichierf0, sommeNbClientsDansChaqueItinéraire);
        System.out.println("= Test passed ✅ Tous les clients du premier fichier ont tous été placés dans un itinéraire =");
        System.out.println();
    }

    /**
     * Teste la génération de solution proche en proche : on prend un itinéraire aléatoire, puis on récupère les plus proches,
     * jusqu'à remplir les itinéraires au max ou presque.
     *
     * @throws VehiculeCapacityOutOfBoundsException lorsque la capacité maximale des véhicules est dépassée pendant les transformations.
     */
    @Test
    public void testGénérerUneSolutionProcheEnProche() throws VehiculeCapacityOutOfBoundsException
    {
        System.out.println();
        System.out.println("= Test Génération Proche en proche");

        List<Fichier> fichiers = Main.chargerFichiers();
        Fichier f = fichiers.get(0);



        int nbClientsDansLeFichierf0 = f.getNbClientsRécupérés();
        int sommeNbClientsDansChaqueItinéraire = 0;

        GénérateurDeSolutions générateurDeSolutions = new GénérateurDeSolutions(f);
        Solution solution = générateurDeSolutions.générerUneSolutionProcheEnProche();
        // pour chaque itinéraire de la solution, on vérifie si le nombre de marchandises à livrer est bien inférieur ou égal à limite
        for (int i = 0; i < solution.getItinéraires().size(); i++) {
            Itinéraire itinéraire = solution.getItinéraires().get(i);
            Assert.assertTrue(itinéraire.getNbMarchandisesALivrer() <= 100);
            System.out.println("= Test passed ✅ L'itinéraire " + i + " contient " + itinéraire.getNbMarchandisesALivrer() + " marchandises à livrer =");
            sommeNbClientsDansChaqueItinéraire += itinéraire.getListeClientsÀLivrer().size();
        }

        // on vérifie si tous les clients du premier fichier ont été placés dans un itinéraire
        Assert.assertEquals(nbClientsDansLeFichierf0, sommeNbClientsDansChaqueItinéraire);
        System.out.println("= Test passed ✅ Tous les clients du fichier ont tous été placés dans un itinéraire =");

        System.out.println();
    }
}
