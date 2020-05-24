package algorithms;

import customexceptions.*;
import model.Itinéraire;
import model.Solution;
import utilitaires.Utilitaire;

import java.util.Random;

/**
 * Classe contenant les algorithmes liés à la méthode du recuit simulé.
 */
public class RecuitSimulé
{

    private static Random random = new Random();

    /**
     * Méthode de recuit simulé permettant de sortir des minima locaux en acceptant des solutions moins bonnes.
     * @param solutionInitiale la solution initiale, de départ, qui sera optimisée puis retournée.
     * @param températureInitiale la température iniatiale, de départ.
     * @param nombreVoisinsParTempérature nombre de solutions voisines généré par température.
     * @param coefficientDeDiminuationTempérature le coefficient de diminution de la température.
     * @param transformation le type de transformation à appliquer lors de la recherche de nouveaux voisins.
     * @param isMétaTransformation booléen indiquant si des méthodes de transformations entre itinéraires d'une solution
     *                             sont opérées (true) ou non (false).
     * @return la solution initiale optimisée.
     * @throws VehiculeCapacityOutOfBoundsException lorsque la capacité maximale des véhicules est dépassée pendant les transformations.
     * @throws ItinéraireTooSmallException en cas d'itinéraire trop petit pour du 2-opt.
     * @throws ListOfClientsIsEmptyException en cas de génération de liste de clients vide lors des transformations.
     */
    public static Solution recuitSimulé(Solution solutionInitiale, double températureInitiale, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature, Transformation transformation, boolean isMétaTransformation, Génération typeDeGénération) throws VehiculeCapacityOutOfBoundsException, ItinéraireTooSmallException, ListOfClientsIsEmptyException, UnhandledTransformationException, SubdivisionAlgorithmException
    {

        double température = températureInitiale;

        // copie de la solution initiale
        Solution meilleureSolution = new Solution(solutionInitiale);
        // longueur totale de la solution
        double fitnessMinimale = meilleureSolution.getOptimisationGlobale();

        // autre copie de la solution initiale
        Solution solutionBase = new Solution(solutionInitiale);
        double fitnessSolution = solutionBase.getOptimisationGlobale();

        double différenceFitness = 0;

        // on boucle sur la température qui diminue (elle est multipliée par le coefficient de diminution de la température)
        // à chaque itération
        for(double k = température; k > 1; k *= coefficientDeDiminuationTempérature) {

            // on boucle sur le nombre de voisins par température que l'on veut générer
            for (int l = 0; l < nombreVoisinsParTempérature; l++) {

                // on crée une copie de solutionBase
                Solution solutionVoisine = new Solution(solutionBase);

                // on transforme la solution
                RecuitSimulé.transformeRecuit(solutionVoisine, transformation, isMétaTransformation);

                // on recalcule la longeure totale de la solution
                solutionVoisine.recalculerLongueurGlobale();
                double fitnessSolutionVoisine = solutionVoisine.getOptimisationGlobale();

                //on calcule la différence entre la différence de fitness
                différenceFitness = fitnessSolutionVoisine - fitnessSolution;

                // si la différence de fitness entre la solution voisine et la solution courante est inférieur ou
                // égale à 0...
                if (différenceFitness <= 0) {
                    solutionBase = solutionVoisine;
                    fitnessSolution = fitnessSolutionVoisine;

                    // si la fitness de la solution voisine est inférieur à la fitness minimale...
                    if (fitnessSolutionVoisine < fitnessMinimale) {
                        // alors la solution voisine devient la meilleure solution
                        meilleureSolution = solutionVoisine;
                        fitnessMinimale = fitnessSolutionVoisine;
                        //display
                        //System.out.println("Nouvelle fitness recuit : " + fitnessMinimale);
                    }
                }
                //sinon...
                else {
                    // on choisi aléatoirement p entre 0 et 1
                    double p = random.nextDouble();
                    // si p est inférieur ou égal à exp(-différenceFitness/température)...
                    if (p <= Math.exp(-différenceFitness/température)) {
                        solutionBase = solutionVoisine;
                        fitnessSolution = fitnessSolutionVoisine;
                    }
                }
            }
        }
        if(typeDeGénération == Génération.ALÉATOIRE_UNIQUE)
        {
            return Utilitaire.subdiviserSolutionItinéraireUniqueEnPlusieursItinéraires(meilleureSolution);
        }
        else
        {
            return meilleureSolution;
        }
    }


    /**
     * Méthode de recuit simulé sur chaque itinéraire permettant de sortir des minima locaux en acceptant des solutions moins bonnes
     * @param itinéraireInitial l'itinéraire initial, de départ, qui sera optimisé puis retourné.
     * @param températureInitiale la température initiale, de départ.
     * @param nombreVoisinsParTempérature nombre de solutions voisines généré par température.
     * @param coefficientDeDiminuationTempérature le coefficient de diminution de la température.
     * @param transformation le type de transformation à appliquer lors de la recherche de nouveaux voisins.
     * @return l'itinéraire initial optimisé.
     * @throws VehiculeCapacityOutOfBoundsException lorsque la capacité maximale des véhicules est dépassée pendant les transformations.
     * @throws ListOfClientsIsEmptyException en cas de génération de liste de clients vide lors des transformations.
     * @throws ItinéraireTooSmallException en cas d'itinéraire trop petit pour du 2-opt.
     * @throws UnhandledTransformationException en cas de transformation non gérée.
     */
    public static Itinéraire recuitSimuléItinéraire(Itinéraire itinéraireInitial, double températureInitiale, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature, Transformation transformation) throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, ItinéraireTooSmallException, UnhandledTransformationException
    {

        double température = températureInitiale;

        // copie de la solution initiale
        Itinéraire meilleurItinéraire = new Itinéraire(itinéraireInitial);

        // longueur totale de la solution
        double fitnessMinimale = meilleurItinéraire.getLongueurTotale();

        // autre copie de la solution initiale
        Itinéraire itinéraireBase = new Itinéraire(itinéraireInitial);
        double fitnessItinéraire = itinéraireBase.getLongueurTotale();

        double différenceFitness = 0;

        // on boucle sur la température qui diminue (elle est multipliée par le coefficient de diminution de la température)
        // à chaque itération
        for(double k = température; k > 1; k *= coefficientDeDiminuationTempérature) {

            // on boucle sur le nombre de voisins par température que l'on veut générer
            for (int l = 0; l < nombreVoisinsParTempérature; l++) {

                //RecuitSimulé.transformeRecuitItinéraire(transformation, itinéraireBase);
                // condition sur la transformation à opérer
                switch(transformation)
                {
                    // dans le cas où la transformation est une transformation échange...
                    case TRANSFORMATION_ÉCHANGE:
                        TransformateurItinéraire.transformationÉchange(itinéraireBase);
                        break;

                    // dans le cas où la transformation est une insertion décalage..
                    case INSERTION_DÉCALAGE:
                        TransformateurItinéraire.insertionDécalage(itinéraireBase);
                        break;

                    // dans le cas où la transformation est une inversion...
                    case INVERSION:
                        TransformateurItinéraire.inversion(itinéraireBase);
                        break;

                    // dans le cas où la transformation est une transformation 2-opt...
                    case TRANSFORMATION_2_OPT:
                        // En backup du 2-opt, on utilise une insertion décalage
                        itinéraireBase = TransformateurItinéraire.transformation2opt(itinéraireBase, Transformation.TRANSFORMATION_ÉCHANGE);
                        break;
                    default:
                        throw new UnhandledTransformationException(transformation, RecuitSimulé.class);
                }

                // on copie le contenu de solutionBase dans solutionVoisine
                Itinéraire itinéraireVoisin = new Itinéraire(itinéraireBase);
                double fitnessItinéraireVoisin = itinéraireVoisin.getLongueurTotale();

                différenceFitness = fitnessItinéraireVoisin - fitnessItinéraire;

                // si la différence de fitness entre l'itinéraire voisin et l'itinéraire courant est inférieur ou
                // égale à 0...
                if (différenceFitness <= 0) {
                    itinéraireBase = itinéraireVoisin;
                    fitnessItinéraire = fitnessItinéraireVoisin;

                    // si la fitness de l'itinéraire voisin est inférieur à la fitness minimale...
                    if (fitnessItinéraireVoisin < fitnessMinimale) {
                        // alors l'itinéraire voisin devient le meilleur itinéraire
                        meilleurItinéraire = itinéraireVoisin;
                        fitnessMinimale = fitnessItinéraireVoisin;
                        //System.out.println("Nouvelle fitness locale recuit itinéraires : " + fitnessMinimale);
                    }
                }
                // sinon...
                else {
                    // on choisit aléatoirement p entre 0 et 1
                    double p = random.nextDouble();
                    // si p est inférieur ou égal à exp(-différenceFitness/température)...
                    if (p <= Math.exp(-différenceFitness/température)) {
                        itinéraireBase = itinéraireVoisin;
                        fitnessItinéraire = fitnessItinéraireVoisin;
                    }

                }
            }

        }
        return meilleurItinéraire;
    }

    /**
     * Transforme une solution en gérant les différents cas possibles, en fonction de la transformation donnée en paramètre.
     * @param base la solution sur laquelle effectuer la transformation.
     * @param transformation la transformation à effectuer.
     * @param isMéta si méta transformation ou pas.
     * @throws VehiculeCapacityOutOfBoundsException exception 2opt.
     * @throws ItinéraireTooSmallException exception 2opt.
     * @throws ListOfClientsIsEmptyException exception 2opt.
     * @throws UnhandledTransformationException exception 2opt.
     */
    private static void transformeRecuit(Solution base, Transformation transformation, boolean isMéta) throws VehiculeCapacityOutOfBoundsException, ItinéraireTooSmallException, ListOfClientsIsEmptyException, UnhandledTransformationException
    {
        // On choisit un itinéraire aléatoirement.
        int indexAléatoire1 = random.nextInt(base.getItinéraires().size());
        // On choisit un deuxième itinéraire aléatoirement.
        int indexAléatoire2 = random.nextInt(base.getItinéraires().size());

        // condition sur la transformation à opérer
        switch(transformation)
        {
            // dans le cas où la transformation est une transformation échange...
            case TRANSFORMATION_ÉCHANGE:
                // s'il s'agit d'une transformation entre deux itinéraires...
                if (isMéta) {
                    // Du méta couplé à du 2-opt pour optimiser au maximum (une chance sur 2)
                    if(random.nextBoolean())
                    {
                        TransformateurEntreItinéraires.métaTransformationÉchange(base.getItinéraires().get(indexAléatoire1),
                                base.getItinéraires().get(indexAléatoire2), 40);
                    }
                    else
                    {
                        base.getItinéraires().set(indexAléatoire1,
                                TransformateurItinéraire.transformation2opt(base.getItinéraires().get(indexAléatoire1), Transformation.TRANSFORMATION_ÉCHANGE));
                    }

                }
                // sinon (s'il s'agit d'une transformation sur un itinéraire)...
                else {
                    TransformateurItinéraire.transformationÉchange(base.getItinéraires().get(indexAléatoire1));
                }

                break;

            // dans le cas où la transformation est une insertion décalage...
            case INSERTION_DÉCALAGE:
                // s'il s'agit d'une transformation entre deux itinéraires...
                if (isMéta) {
                    throw new UnsupportedOperationException();
                }
                // sinon (s'il s'agit d'une transformation sur un itinéraire)...
                else {
                    TransformateurItinéraire.insertionDécalage(base.getItinéraires().get(indexAléatoire1));
                }

                break;

            // dans le cas où la transformation est une inversion...
            case INVERSION:
                // (s'agit d'une transformation entre deux itinéraires) : opération pas possible entre deux itinéraires...
                if (isMéta) {
                    throw new UnsupportedOperationException();
                }
                // s'il s'agit d'une transformation sur itinéraire
                else
                {
                    TransformateurItinéraire.inversion(base.getItinéraires().get(indexAléatoire1));
                }
                break;

            // dans le cas où la transformation est une transformation 2-opt
            case TRANSFORMATION_2_OPT:
                //s'agit d'une transformation entre deux itinéraires) : opération pas possible entre deux itinéraires..
                if(isMéta)
                {
                    throw new UnsupportedOperationException();

                }
                else
                {
                    // En backup du 2-opt, on utilise une transformation échange
                    base.getItinéraires().set(indexAléatoire1,
                            TransformateurItinéraire.transformation2opt(base.getItinéraires().get(indexAléatoire1), Transformation.TRANSFORMATION_ÉCHANGE));
                }
                break;

            // si la transformation n'est aucune des précédentes...
            default:
                throw new UnhandledTransformationException(transformation, RecuitSimulé.class);

        }
    }
}
