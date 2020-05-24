import algorithms.*;
import customexceptions.UnhandledGénérationException;
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import inout.Loader;
import model.*;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


/**
 * Point d'entrée de notre programme.
 */
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


        /* * * * * * * * ------------------------------ PARAMÈTRES ------------------------------ * * * * * * * */
        /* * * * ---- Le fichier ---- * * * */
        // On a 28 fichiers au total, situé dans le répertoire "data".
        // On peut choisir de travailler sur l'un des fichiers allant de l'index 0 à l'index 27.
        // Plus les fichiers ont un index élevé, plus le nombre de clients à livrer est élevé. Notamment, les index
        // 25, 26 et 27 sont des fichiers contenant 100 clients chacun.
        Fichier fx = fichiers.get(27);

        /* * * * ---- Le nombre de solutions aléatoires initiales ---- * * * */
        // Le nombre de solutions aléatoire correspond au nombre de solutions aléatoires qui seront générées et traitées
        // par nos algorithmes. Plus on augmente ce nombre, plus on augmente la chance de tomber sur une "bonne" solution
        // aléatoire de base et donc d'avoir de bons résultats. Mais plus on augmente ce nombre, et plus les algorithmes
        // de recherche vont prendre du temps.
        int nbSolutionsAléatoiresInitiales = 2;

        /* * * * ---- Le type de génération ---- * * * */
        // Permet de définir le type de génération qui sera utilisé :
        // 1 - ALÉATOIRE : La génération la plus basique, totalement aléatoire. On prend un client aléatoirement, on le met dans un itinéraire,
        //     puis on recommence, jusqu'à remplir tous nos itinéraires à la capacité maximale ou presque.
        //     On s'arrête une fois que l'ensemble des clients sont traités. Utilisable avec toutes les méthodes.

        // 2 - ALÉATOIRE_SEUIL : Comme la génération aléatoire, sauf que l'on se fixe une valeur maximale à ne pas dépasser pour la quantité
        //     de marchandises à livrer pour chaque itinéraire, au lieu de se fixer la capacité du véhicule en valeur maximale.
        //     Utilisable avec toutes les méthodes.

        // 3 - ALÉATOIRE_UNIQUE : La plus efficace notamment avec le Tabou : La génération aléatoire unique consiste à prendre chacun
        //     des clients d'un fichier aléatoirement, puis de tous les ajouter au sein d'un seul et unique itinéraire.
        //     Avec Tabou, si cette génération est utilisé, la solution à itinéraire unique est optimisée, puis ensuite
        //     l'itinéraire unique est découpé en petits itinéraires respectant les règles métier (pas de dépassement de la capacité
        //     des véhicules). Ensuite, si le paramètre "doubleTabou" est passé à True, on réeffectue un tabou sur les nouveaux itinéraires
        //     obtenus, pour encore + optimiser la solution.
        //     Ce mode est utilisable uniquement avec Tabou et Recuit (Impossible avec Recuit itinéraires). Si cette condition n'est pas respectée,
        //     Une exception personnalisée est levée.

        // 4 - PROCHE_EN_PROCHE : Très efficace sur tous les algorithmes puisque à la limite de l'aléatoire, une mécanique gloutonne est en
        //     en effet utilisé pour cette génération : La génération proche en proche consiste à prendre un client aléatoirement,
        //     de le placer dans un itinéraire, puis prendre tous les clients le plus proche de ce client choisi aléatoirement,
        //     et de les placer dans un itinéraire. On recommence jusqu'à avoir rempli tous nos itinéraires et traité tous nos clients.
        Génération typeDeGénération = Génération.ALÉATOIRE_UNIQUE;

        /* * * * ---- Le seuil maximal ---- * * * */
        // le seuil maximal qui ne devra pas être dépassé pour le nb de marchandises par véhicule en cas de
        // génération aléatoire par SEUIL(Génération.ALEATOIRE_SEUIL)
        // ne pas tenir compte de ce paramètre si la Génération est différente de ALÉATOIRE_SEUIL.
        int seuilCapacitéMaxItinéraireGénération = 80;

        /* * * * ---- Le type de transformation ---- * * * */
        // Permet de définir le type de transformation qui sera utilisé :
        // 1 - TRANSFORMATION_ÉCHANGE : Effectue un échange entre deux clients d'un itinéraire.
        //     /!\ COMPATIBLE /!\ avec des méta-transformations si "utilisationDeMétaTransformation" est passé à true.
        //
        // 2 - INSERTION_DÉCALAGE : Un client à une position i est pioché aléatoirement dans itinéraire, retiré de itinéraire, puis ajouté
        //     à une position i+delta ou i-delta, delta étant aléatoirement généré.
        //     /!\ INCOMPATIBLE /!\ avec les méta transformations. Lèvera une exceptiojn si le paramètre utilisationDeMétaTransformations est true.
        //
        // 3 - INVERSION : Inverse une portion entière de k éléments appartenant à l'itinéraire.
        //     /!\ INCOMPATIBLE /!\ avec les méta transformations. Lèvera une exception si le paramètre utilisationDeMétaTransformation est true.
        //
        // 4 - TRANSFORMATION_2_OPT : La transformation la plus efficace. Effectue une transformation 2-opt.
        //     /!\ INCOMPATIBLE /!\ avec les méta transformations. Lèvera une exception si le paramètre utilisationDeMétaTransformation est true.
        //
        Transformation typeDeTransformation = Transformation.TRANSFORMATION_ÉCHANGE;

        /* * * * ---- L'utilisation de méta transformations (true) ou non (false) ---- * * * */
        // Changer ce paramètre UNIQUEMENT si l'on souhaite utiliser la méta transformation TRANSFORMATION_ÉCHANGE.
        // Si ce paramètre doit être passé à true dans deux cas uniquement :
        // 1 - Si la transformation est TRANSFORMATION_ÉCHANGE et que l'algorithme utilisé est tabou
        // 2 - Si la transformation est TRANSFORMATION_ÉCHANGE et que l'algorithme utilisé est recuit.
        //
        // /!\ Si ce paramètre est passé à true dans ces deux cas (et uniquement ces deux cas), alors le tabou ou le recuit
        // effectueront avec une probabilité 1/2 une méta transformation de type échange, et avec une probabilité 1/2
        // une méta transformation de type 2-opt pour une efficacité maximale. /!\
        // Sinon, une exception sera levée, ou bien aucune méta transformation ne sera effectuée (notamment avec le recuit itinéraire,
        // qui ne prend pas du tout en compte ce paramètre)
        boolean utilisationDeMétaTransformations = true;

        /* * * * ---- double tabou ---- * * * */
        // ce paramètre très spécifique permet, dans le cas d'une génération ALÉATOIRE_UNIQUE  et de l'utilisation
        // de l'algorithme de tabou dans les tests plus bas (et seulement dans ce cas, sinon le paramètre n'est pas pris en compte)
        // de suivre le processus suivant :
        //
        // 1) génération solution aléatoire avec itinéraire unique
        // -> 2) tabou sur la solution aléatoire avec itinéraire unique pour l'optimiser
        //  -> 3) découpage de l'itinéraire unique en plus petits itinéraires pour respecter les contraintes métier (capacité de véhicule)
        //   -> 4) application d'un second tabou sur la nouvelle solution composée désormais de plus petits itinéraires.
        // NE PAS TENIR COMPTE DE CE PARAMÈTRE SI Génération != ALÉATOIRE_UNIQUE et pas d'utilisation de tabou.
        doubleTabou = false;
        /* * * * * * * * ------------------------------ PARAMÈTRES ------------------------------ * * * * * * * */



        /* * * * * * * * ------------------------------ ZONE DE TEST ------------------------------ * * * * * * * */
        /* -----------------------------------------------------------------------------------------------------------*/
        /* Usage : décommenter le test voulu et modifier éventuellement les paramètres */
        System.out.println("---DÉBUT DU TEST---\n");
        ZonedDateTime now = ZonedDateTime.now();

        /* ---------------- test avec recuitSimulé  ---------------- */
/*
        Solution best = testRecuit(fx,
                        nbSolutionsAléatoiresInitiales,
                        typeDeGénération,
                        seuilCapacitéMaxItinéraireGénération,
                        1000, // la température initiale.
                        1000, // le nombre de voisins par température.
                        0.99, // le coefficient de diminution de la température.
                        typeDeTransformation,
                        utilisationDeMétaTransformations);
*/

        /* ---------- test avec recuitSimuléItinéraires -----------  */

/*
        Solution best = testRecuitItinéraire(fx,
                        nbSolutionsAléatoiresInitiales,
                        typeDeGénération,
                        seuilCapacitéMaxItinéraireGénération,
                        100, // la température initiale
                        1000, // le nombre de voisins par température
                        0.99, // le coefficient de diminution de la température.
                        typeDeTransformation);
*/

        /* ------------------- test avec tabou ------------------- */

        TypeDeRechercheVoisinage typeDeRechercheVoisinage = TypeDeRechercheVoisinage.BASIQUE;
        if(utilisationDeMétaTransformations)
        {
            typeDeRechercheVoisinage = TypeDeRechercheVoisinage.COMPLEXE;
        }
        Solution best = testTabou(fx,
                        nbSolutionsAléatoiresInitiales,
                        typeDeGénération,
                        seuilCapacitéMaxItinéraireGénération,
                        400, // la taille maximale de la liste de tabou.
                        1000, // le nombre d'itérations de l'algo.
                        100, // le nombre de solutions voisines à chaque itération.
                        typeDeTransformation,
                        typeDeRechercheVoisinage);

        /* -----------------------------------------------------------------------------------------------------------*/
        /* * * * * * * * ------------------------------ FIN ZONE DE TEST ------------------------------ * * * * * * * */

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
     * @param températureInitiale la température initiale, de départ.
     * @param nombreDeVoisinsParTempérature nombre de solutions voisines généré par température.
     * @param coefficientDeDiminutionTempérature le coefficient de diminution de la température.
     * @param isMétaTransformations Indique si des méta-transformations sont utilisées ou non
     * @param typeDeTransformation le type de transformation à utiliser pour le recuit.
     * @return la meilleure solution trouvée
     * @throws Exception en cas d'erreurs diverses...
     */
    private static Solution testRecuit(Fichier fichier, int nbSolutionsInitiales, Génération typeGénération, int seuil, double températureInitiale, double nombreDeVoisinsParTempérature, double coefficientDeDiminutionTempérature, Transformation typeDeTransformation,  boolean isMétaTransformations) throws Exception
    {
        System.out.println("-RECUIT en cours-");
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

            System.out.println("Solution optimisée n° " + solutionsAléatoires.indexOf(solutionCourante) + " : " + solutionOptimisée.getOptimisationGlobale());
            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
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
     * @param températureInitiale la température initiale, de départ.
     * @param nombreDeVoisinsParTempérature nombre de solutions voisines généré par température.
     * @param typeDeTransformation le type de transformation à utiliser pour le recuit.
     * @param coefficientDeDiminutionTempérature le coefficient de diminution de la température.
     * @return la meilleure solution trouvée
     * @throws Exception en cas d'erreurs diverses...
     */
    private static Solution testRecuitItinéraire(Fichier fichier, int nbSolutionsInitiales, Génération typeGénération, int seuil, double températureInitiale, double nombreDeVoisinsParTempérature, double coefficientDeDiminutionTempérature, Transformation typeDeTransformation) throws Exception
    {
        if(typeGénération == Génération.ALÉATOIRE_UNIQUE)
        {
            throw new UnhandledGénérationException("La génération ALÉATOIRE_UNIQUE n'est pas gérée avec le recuit itinéraire.");
        }
        System.out.println("-RECUIT SUR ITINÉRAIRES en cours-");

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
            System.out.println("Solution optimisée n° " + solutionsAléatoires.indexOf(solutionCourante) + " : " + solutionOptimisée.getOptimisationGlobale());
            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
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

        System.out.println("-TABOU SEARCH en cours-");

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

            System.out.println("Solution optimisée n° " + solutionsAléatoires.indexOf(s) + " : " + solutionOptimisée.getOptimisationGlobale());
            if(solutionOptimisée.getOptimisationGlobale() < best.getOptimisationGlobale())
            {
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
