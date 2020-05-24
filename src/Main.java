import algorithms.*;
import customexceptions.UnhandledGénérationException;
import customexceptions.UnhandledTypeDeRechercheVoisinageException;
import gui.CVRPWindow;
import gui.CVRPWindowController;
import javafx.application.Application;
import javafx.stage.Stage;
import inout.Loader;
import model.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    private static boolean doubleTabou = false;

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        CVRPWindow mainGui = new CVRPWindow(primaryStage);
        // affichage de la fenêtre principale
        mainGui.show();

        // chargement de fichiers
        List<Fichier> fichiers = Main.chargerFichiers();


        /* ------------------------------ PARAMÈTRES ------------------------------ */
        /* les paramètres de bases relatifs à la génération et au fichier */
        //todo : doc sur les paramètres, les valeurs qu'ils peuvent prendre

        Fichier fx = fichiers.get(16);
        int nbSolutionsAléatoiresInitiales = 1;
        Génération typeDeGénération = Génération.ALÉATOIRE_UNIQUE;
        /* le seuil maximal qui ne devra pas être dépassé pour le nb de marchandises par véhicule en cas de génération aléatoire par SEUIL
        * (Génération.ALEATOIRE_SEUIL) */
        int seuilCapacitéMaxItinéraireGénération = 80;
        /* paramètres de transformation */
        Transformation typeDeTransformation = Transformation.Transformation2Opt;
        //Transformation typeDeTransformation = Transformation.TransformationÉchange;
        boolean utilisationDeMétaTransformations = false;

        // double tabou dans le cas d'une génération aléatoire unique (ne pas tenir compte de ce paramètre si la
        // génération n'est pas aléatoire unique.
        doubleTabou = true;
        /* ------------------------------ PARAMÈTRES ------------------------------ */



        /* ------------------------------------------------------------------------------ */
        /*                                 ZONE DE TESTS                                  */

        // décommenter le test voulu et éventuellement modifier les paramètres.

        // affichage
        System.out.println("---DÉBUT DU TEST---\n");
        ZonedDateTime now = ZonedDateTime.now();

        /* ---------------- test avec recuitSimulé  ---------------- */



        Solution best = testRecuit(fx,
                        nbSolutionsAléatoiresInitiales,
                        typeDeGénération,
                        seuilCapacitéMaxItinéraireGénération,
                        1000,
                        1000,
                        0.99,
                        typeDeTransformation,
                        utilisationDeMétaTransformations);


        /* ---------- test avec recuitSimuléItinéraires -----------  */


/*
        Solution best = testRecuitItinéraire(fx,
                        nbSolutionsAléatoiresInitiales,
                        typeDeGénération,
                        seuilCapacitéMaxItinéraireGénération,
                        100,
                        1000,
                        0.99,
                        typeDeTransformation);
*/


        /* ------------------- test avec tabou ------------------- */

        TypeDeRechercheVoisinage typeDeRechercheVoisinage = TypeDeRechercheVoisinage.BASIQUE;
        if(utilisationDeMétaTransformations)
        {
            typeDeRechercheVoisinage = TypeDeRechercheVoisinage.COMPLEXE;
        }

/*
        Solution best = testTabou(fx,
                        nbSolutionsAléatoiresInitiales,
                        typeDeGénération,
                        seuilCapacitéMaxItinéraireGénération,
                        200,
                        500,
                        150,
                        typeDeTransformation,
                        typeDeRechercheVoisinage);
*/

        /*                             FIN ZONE DE TESTS                                  */
        /* ------------------------------------------------------------------------------ */


        mainGui.getController().drawSolution(best);

        long millis = now.until(ZonedDateTime.now(), ChronoUnit.MILLIS);
        double sec = (double)millis/1000;
        System.out.println("\n---FIN DU TEST en " + sec + " secondes. ---");
        System.out.println("Meilleure solution : " + best.getOptimisationGlobale());
    }

    /**
     * Permet de tester le recuit basique.
     * @param fichier le fichier sur lequel appliquer le recuit.
     * @param nbSolutionsInitiales le nombre de solutions initiales = le nombre de solutions aléatoires à générer.
     * @param typeGénération le type de génération utilisé pour la génération de solutions de base.
     * @param seuil le seuil (optionnel) qui correspond à la capacité maximale qui ne doit pas être dépassée par les itinéraires des solutions lors de leur génération.
     * @param températureInitiale todo
     * @param nombreDeVoisinsParTempérature todo
     * @param coefficientDeDiminutionTempérature todo
     * @param isMétaTransformations Indique si des méta-transformations sont utilisées ou non
     * @param typeDeTransformation le type de transformation à utiliser pour le recuit.
     * @return la meilleure solution trouvée
     * @throws Exception en cas d'erreurs diverses...
     */
    private static Solution testRecuit(Fichier fichier, int nbSolutionsInitiales, Génération typeGénération, int seuil, double températureInitiale, double nombreDeVoisinsParTempérature, double coefficientDeDiminutionTempérature, Transformation typeDeTransformation,  boolean isMétaTransformations) throws Exception
    {
        System.out.println("-RECUIT-");
        Solution best = new Solution();
        best.setOptimisationGlobale(Double.MAX_VALUE);

        GénérateurDeSolutions générateurDeSolutions = new GénérateurDeSolutions(fichier);
        ArrayList<Solution> solutionsAléatoires = générateurDeSolutions.générerXSolutionsAléatoire(nbSolutionsInitiales, typeGénération, seuil);

        for(Solution solutionCourante:solutionsAléatoires)
        {
            Solution solutionOptimisée = RecuitSimulé.recuitSimulé(solutionCourante,
                                        températureInitiale,
                                        nombreDeVoisinsParTempérature,
                                        coefficientDeDiminutionTempérature,
                                        typeDeTransformation,
                                        isMétaTransformations,
                                        typeGénération);

            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
                System.out.println("Solution optimisée n° " + solutionsAléatoires.indexOf(solutionCourante) + " : " + solutionOptimisée.getOptimisationGlobale());
                best = solutionOptimisée;
            }
        }
        return best;
    }

    /**
     * Permet de tester le recuit mais sur des itinéraires.
     * Ne gère pas la Génération de type ALÉATOIRE_UNIQUE.
     * @param fichier le fichier sur lequel appliquer le recuit.
     * @param nbSolutionsInitiales le nombre de solutions initiales = le nombre de solutions aléatoires à générer.
     * @param typeGénération le type de génération utilisé pour la génération de solutions de base.
     * @param seuil le seuil (optionnel) qui correspond à la capacité maximale qui ne doit pas être dépassée par les itinéraires des solutions lors de leur génération. Laisser à 0 si le type de génération est aléatoire ou proche en proche.
     * @param températureInitiale todo
     * @param nombreDeVoisinsParTempérature todo
     * @param typeDeTransformation le type de transformation à utiliser pour le recuit.
     * @return la meilleure solution trouvée
     * @throws Exception en cas d'erreurs diverses...
     */
    private static Solution testRecuitItinéraire(Fichier fichier, int nbSolutionsInitiales, Génération typeGénération, int seuil, double températureInitiale, double nombreDeVoisinsParTempérature, double coefficientDeDiminutionTempérature, Transformation typeDeTransformation) throws Exception
    {
        if(typeGénération == Génération.ALÉATOIRE_UNIQUE)
        {
            throw new UnhandledGénérationException("La génération ALÉATOIRE_UNIQUE n'est pas gérée avec le recuit itinéraire.");
        }
        System.out.println("-RECUIT SUR ITINÉRAIRES-");

        Solution best = new Solution();
        best.setOptimisationGlobale(Double.MAX_VALUE);

        GénérateurDeSolutions générateurDeSolutions = new GénérateurDeSolutions(fichier);
        ArrayList<Solution> solutionsAléatoires = générateurDeSolutions.générerXSolutionsAléatoire(nbSolutionsInitiales, typeGénération, seuil);

        for(Solution solutionCourante:solutionsAléatoires)
        {
            Solution solutionOptimisée = new Solution();
            for (int j = 0; j < solutionCourante.getItinéraires().size(); j++) {

                Itinéraire itinéraireOptimisé = RecuitSimulé.recuitSimuléItinéraire(solutionCourante.getItinéraires().get(j),
                                                températureInitiale,
                                                nombreDeVoisinsParTempérature,
                                                coefficientDeDiminutionTempérature,
                                                typeDeTransformation);
                                                solutionOptimisée.ajouterTournée(itinéraireOptimisé);
            }
            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
                System.out.println("Solution optimisée n° " + solutionsAléatoires.indexOf(solutionCourante) + " : " + solutionOptimisée.getOptimisationGlobale());
                best = solutionOptimisée;
            }
        }
        return best;
    }

    /**
     * Permet de tester le tabou search
     * @param fichier le fichier sur lequel appliquer le tabou.
     * @param nbSolutionsInitiales le nombre de solutions initiales = le nombre de solutions aléatoires à générer.
     * @param typeGénération le type de génération utilisé pour la génération de solutions de base.
     * @param seuil le seuil (optionnel) qui correspond à la capacité maximale qui ne doit pas être dépassée par les itinéraires des solutions lors de leur génération. Laisser à 0 si le type de génération est aléatoire ou proche en proche.
     * @param tailleMaximaleListeTabou la taille maximale que prendra la liste de tabou.
     * @param nbIterMax le nombre d'itérations maximal de l'algorithme.
     * @param nbSolutionsVoisinesChaqueIter le nombre de solutions voisines devant être trouvées à chaque itération.
     * @param typeDeTransformation le type de transformation à appliquer lors de la recherche de nouveaux voisins.
     * @param typeDeRechercheVoisinage le type de recherche de voisinage à appliquer.
     * @return
     * @throws Exception
     */
    private static Solution testTabou(Fichier fichier, int nbSolutionsInitiales, Génération typeGénération, int seuil, int tailleMaximaleListeTabou, int nbIterMax, int nbSolutionsVoisinesChaqueIter, Transformation typeDeTransformation, TypeDeRechercheVoisinage typeDeRechercheVoisinage) throws Exception
    {

        System.out.println("-TABOU SEARCH-");

        Solution best = new Solution();
        best.setOptimisationGlobale(Double.MAX_VALUE);
        GénérateurDeSolutions générateurDeSolutions = new GénérateurDeSolutions(fichier);

        ArrayList<Solution> solutionsAléatoires = générateurDeSolutions.générerXSolutionsAléatoire(nbSolutionsInitiales, typeGénération, seuil);
        for(Solution s:solutionsAléatoires)
        {
            Solution solutionOptimisée;
            // tabou en mode solution unique
            if(typeGénération == Génération.ALÉATOIRE_UNIQUE)
            {
                solutionOptimisée = Tabou.tabouSearchAvecItinéraireUnique(s, tailleMaximaleListeTabou, nbIterMax, nbSolutionsVoisinesChaqueIter, typeDeTransformation, typeDeRechercheVoisinage, doubleTabou);
            }
            // tabou en mode classique
            else
            {
                solutionOptimisée = Tabou.tabouSearch(s, tailleMaximaleListeTabou, nbIterMax, nbSolutionsVoisinesChaqueIter, typeDeTransformation, typeDeRechercheVoisinage);
            }

            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
                System.out.println("Solution optimisée n° " + solutionsAléatoires.indexOf(s) + " : " + solutionOptimisée.getOptimisationGlobale());
                best = solutionOptimisée;
            }
        }
        return best;
    }


    /**
     * Permet de charger les fichiers.
     * @return liste des fichiers chargés.
     */
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
