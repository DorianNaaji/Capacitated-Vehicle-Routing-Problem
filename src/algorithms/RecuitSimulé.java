package algorithms;

import customexceptions.ItinéraireTooSmallException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.UnhandledTransformationException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.Itinéraire;
import model.Solution;

import java.util.Random;

public class RecuitSimulé
{

    /**
     * Méthode de recuit simulé permettant de sortir des minima locaux en acceptant des solutions moins bonnes
     * @param solutionInitiale solution initiale
     * @param températureInitiale température iniale
     * @param nombreVoisinsParTempérature nombre de voisins généré par température
     * @param coefficientDeDiminuationTempérature coefficient de diminution de la température
     * @param transformation
     * @param isMétaTransformation booléen indiquant si des méthodes de transformations entre itinéraires d'une solution
     *                             sont opérées (true) ou non (false)
     * @return une solution optimisée
     * @throws VehiculeCapacityOutOfBoundsException
     * @throws ItinéraireTooSmallException
     * @throws ListOfClientsIsEmptyException
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

        //for (int k = 0; k <= changementDeTempérature; k++) {
        for(double k = température; k > 1; k *= coefficientDeDiminuationTempérature) {

            for (int l = 0; l < nombreVoisinsParTempérature; l++) {

               /* for (int a = 0; a < solutionBase.getItinéraires().size(); a++) {
                    TransformateurDeSolutions transformateurDeSolutions = new TransformateurDeSolutions(solutionBase);
                    transformateurDeSolutions.inversion(solutionBase.getItinéraires().get(a));
                }*/

                // On choisit un itinéraire aléatoirement.
                int indexAléatoire1 = random.nextInt(solutionBase.getItinéraires().size());
                // On choisit un deuxième itinéraire aléatoirement.
                int indexAléatoire2 = random.nextInt(solutionBase.getItinéraires().size());
                switch(transformation)
               {
                   case TransformationÉchange:
                       if (isMétaTransformation) {
                           // Du méta couplé à du 2-opt pour optimiser au maximum (une chance sur 2)
                           if(random.nextBoolean())
                           {
//                               TransformateurEntreItinéraires.transformationÉchange(solutionBase.getItinéraires().get(indexAléatoire1),
//                                   solutionBase.getItinéraires().get(indexAléatoire2));
                               TransformateurEntreItinéraires.métaTransformationÉchange(solutionBase.getItinéraires().get(indexAléatoire1),
                                       solutionBase.getItinéraires().get(indexAléatoire2), 40);
                           }
                           else
                           {
                               solutionBase.getItinéraires().set(indexAléatoire1,
                                       TransformateurItinéraire.transformation2opt(solutionBase.getItinéraires().get(indexAléatoire1), Transformation.TransformationÉchange));
                           }

                       }
                       else {
                           TransformateurItinéraire.transformationÉchange(solutionBase.getItinéraires().get(indexAléatoire1));
                       }

                       break;
                   case InsertionDécalage:
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
                       else {
                           TransformateurItinéraire.insertionDécalage(solutionBase.getItinéraires().get(indexAléatoire1));
                       }

                       break;
                   case Inversion:
                       if (!isMétaTransformation) {
                           TransformateurItinéraire.inversion(solutionBase.getItinéraires().get(indexAléatoire1));

                       }
                       else
                       {
                           throw new UnsupportedOperationException();
                       }
                       break;
                   case Transformation2Opt:
                       // En backup du 2-opt, on utilise une transformation échange
                       if(!isMétaTransformation)
                       {
                           solutionBase.getItinéraires().set(indexAléatoire1,
                                   TransformateurItinéraire.transformation2opt(solutionBase.getItinéraires().get(indexAléatoire1), Transformation.TransformationÉchange));
                       }
                       else
                       {
                           throw new UnsupportedOperationException();
                       }
                       break;
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

                if (différenceFitness <= 0) {
                    solutionBase = solutionVoisine;
                    fitnessSolution = fitnessSolutionVoisine;

                    if (fitnessSolutionVoisine < fitnessMinimale) {
                        meilleureSolution = solutionVoisine;
                        fitnessMinimale = fitnessSolutionVoisine;
                    }
                }
                else {
                    double p = random.nextDouble();
                    if (p <= Math.exp(-différenceFitness/température)) {
                        solutionBase = solutionVoisine;
                        fitnessSolution = fitnessSolutionVoisine;
                    }
                }
            }
          //  température *= coefficientDeDiminuationTempérature;
        }
        //System.out.println(meilleureSolution.getOptimisationGlobale());
        return meilleureSolution;
    }


    public static Itinéraire recuitSimuléItinéraire(Itinéraire itinéraireInitial, double températureInitiale, int changementDeTempérature, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature, Transformation transformation) throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, ItinéraireTooSmallException, UnhandledTransformationException
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

        //for (int k = 0; k <= changementDeTempérature; k++) {
        for(double k = température; k > 1; k *= coefficientDeDiminuationTempérature) {

            for (int l = 0; l < nombreVoisinsParTempérature; l++) {

               /* for (int a = 0; a < solutionBase.getItinéraires().size(); a++) {
                    TransformateurDeSolutions transformateurDeSolutions = new TransformateurDeSolutions(solutionBase);
                    transformateurDeSolutions.inversion(solutionBase.getItinéraires().get(a));
                }*/

                switch(transformation)
                {
                    case TransformationÉchange:
                        TransformateurItinéraire.transformationÉchange(itinéraireBase);
                        break;
                    case InsertionDécalage:
                        TransformateurItinéraire.insertionDécalage(itinéraireBase);
                        break;
                    case Inversion:
                        TransformateurItinéraire.inversion(itinéraireBase);
                        break;
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

                if (différenceFitness <= 0) {
                    itinéraireBase = itinéraireVoisin;
                    fitnessItinéraire = fitnessItinéraireVoisin;

                    if (fitnessItinéraireVoisin < fitnessMinimale) {
                        meilleurItinéraire = itinéraireVoisin;
                        fitnessMinimale = fitnessItinéraireVoisin;
                    }
                }
                else {
                    double p = random.nextDouble();
                    if (p <= Math.exp(-différenceFitness/température)) {
                        itinéraireBase = itinéraireVoisin;
                        fitnessItinéraire = fitnessItinéraireVoisin;
                    }

                }
            }
            //  température *= coefficientDeDiminuationTempérature;

        }
        //System.out.println(meilleurItinéraire.getLongueurTotale());
        return meilleurItinéraire;
    }
}
