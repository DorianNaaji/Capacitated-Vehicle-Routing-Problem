package algorithms;

import customexceptions.ItinéraireTooSmallException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.UnhandledTransformationException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.Itinéraire;
import model.Solution;

import java.util.Random;

/**
 * Classe contenant les algorithmes liés à la méthode du recuit simulé.
 */
public class RecuitSimulé
{

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
    public static Solution recuitSimulé(Solution solutionInitiale, double températureInitiale, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature, Transformation transformation, boolean isMétaTransformation) throws VehiculeCapacityOutOfBoundsException, ItinéraireTooSmallException, ListOfClientsIsEmptyException, UnhandledTransformationException
    {
        Random random = new Random();

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

                // On choisit un itinéraire aléatoirement.
                int indexAléatoire1 = random.nextInt(solutionBase.getItinéraires().size());
                // On choisit un deuxième itinéraire aléatoirement.
                int indexAléatoire2 = random.nextInt(solutionBase.getItinéraires().size());

                // condition sur la transformation à opérer
                switch(transformation)
               {
                   // dans le cas où la transformation est une transformation échange...
                   case TransformationÉchange:
                       // s'il s'agit d'une transformation entre deux itinéraires...
                       if (isMétaTransformation) {
                           // Du méta couplé à du 2-opt pour optimiser au maximum (une chance sur 2)
                           if(random.nextBoolean())
                           {
                               TransformateurEntreItinéraires.métaTransformationÉchange(solutionBase.getItinéraires().get(indexAléatoire1),
                                       solutionBase.getItinéraires().get(indexAléatoire2), 40);
                           }
                           else
                           {
                               solutionBase.getItinéraires().set(indexAléatoire1,
                                       TransformateurItinéraire.transformation2opt(solutionBase.getItinéraires().get(indexAléatoire1), Transformation.TransformationÉchange));
                           }

                       }
                       // sinon (s'il s'agit d'une transformation sur un itinéraire)...
                       else {
                           TransformateurItinéraire.transformationÉchange(solutionBase.getItinéraires().get(indexAléatoire1));
                       }

                       break;

                   // dans le cas où la transformation est une insertion décalage...
                   case InsertionDécalage:
                       // s'il s'agit d'une transformation entre deux itinéraires...
                       if (isMétaTransformation) {
                           // Du méta couplé à du 2-opt pour optimiser au maximum (une chance sur 2)
                           if(random.nextBoolean())
                           {
                               TransformateurEntreItinéraires.insertionDécalage(solutionBase.getItinéraires().get(indexAléatoire1),
                                       solutionBase.getItinéraires().get(indexAléatoire2));
                           }
                           else
                           {
                               solutionBase.getItinéraires().set(indexAléatoire1,
                                       TransformateurItinéraire.transformation2opt(solutionBase.getItinéraires().get(indexAléatoire1), Transformation.TransformationÉchange));
                           }
                       }
                       // sinon (s'il s'agit d'une transformation sur un itinéraire)...
                       else {
                           TransformateurItinéraire.insertionDécalage(solutionBase.getItinéraires().get(indexAléatoire1));
                       }

                       break;

                    // dans le cas où la transformation est une inversion...
                   case Inversion:
                       // s'il s'agit d'une transformation sur itinéraire
                       if (!isMétaTransformation) {
                           TransformateurItinéraire.inversion(solutionBase.getItinéraires().get(indexAléatoire1));

                       }
                       // sinon (s'agit d'une transformation entre deux itinéraires) : opération pas possible entre deux itinéraires...
                       else
                       {
                           throw new UnsupportedOperationException();
                       }
                       break;

                       // dans le cas où la transformation est une transformation 2-opt
                       case Transformation2Opt:
                       // En backup du 2-opt, on utilise une transformation échange
                       if(!isMétaTransformation)
                       {
                           solutionBase.getItinéraires().set(indexAléatoire1,
                                   TransformateurItinéraire.transformation2opt(solutionBase.getItinéraires().get(indexAléatoire1), Transformation.TransformationÉchange));
                       }
                       // sinon (s'agit d'une transformation entre deux itinéraires) : opération pas possible entre deux itinéraires...
                       else
                       {
                           throw new UnsupportedOperationException();
                       }
                       break;

                   // si la transformation n'est aucune des précédentes...
                   default:
                       throw new UnhandledTransformationException(transformation, RecuitSimulé.class);

               }

                // création d'une solution voisine à partir de solutionBase
                solutionBase.recalculerLongueurGlobale();

                // on copie le contenu de solutionBase dans solutionVoisine
                Solution solutionVoisine = new Solution(solutionBase);
                solutionVoisine.recalculerLongueurGlobale();
                double fitnessSolutionVoisine = solutionVoisine.getOptimisationGlobale();

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
        return meilleureSolution;
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
        Random random = new Random();

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

                // condition sur la transformation à opérer
                switch(transformation)
                {
                    // dans le cas où la transformation est une transformation échange...
                    case TransformationÉchange:
                        TransformateurItinéraire.transformationÉchange(itinéraireBase);
                        break;

                    // dans le cas où la transformation est une insertion décalage..
                    case InsertionDécalage:
                        TransformateurItinéraire.insertionDécalage(itinéraireBase);
                        break;

                    // dans le cas où la transformation est une inversion...
                    case Inversion:
                        TransformateurItinéraire.inversion(itinéraireBase);
                        break;

                    // dans le cas où la transformation est une transformation 2-opt...
                    case Transformation2Opt:
                        // En backup du 2-opt, on utilise une insertion décalage
                        //todo : décider de la meilleure transfo après le 2-opt
                        itinéraireBase = TransformateurItinéraire.transformation2opt(itinéraireBase, Transformation.TransformationÉchange);
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
}
