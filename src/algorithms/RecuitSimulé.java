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

    public static Solution recuitSimulé(Solution solutionInitiale, double températureInitiale, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature, Transformation transformation) throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, ItinéraireTooSmallException, UnhandledTransformationException
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
                int indexAléatoire = random.nextInt(solutionBase.getItinéraires().size());
                switch(transformation)
               {
                   case TransformationLocale:
                       TransformateurItinéraire.transformationLocale(solutionBase.getItinéraires().get(indexAléatoire));
                       break;
                   case InsertionDécalage:
                       TransformateurItinéraire.insertionDécalage(solutionBase.getItinéraires().get(indexAléatoire));
                       break;
                   case Inversion:
                       TransformateurItinéraire.inversion(solutionBase.getItinéraires().get(indexAléatoire));
                       break;
                   case Transformation2Opt:
                       // En backup du 2-opt, on utilise une insertion décalage
                       //todo : décider de la meilleure transfo après le 2-opt
                       solutionBase.getItinéraires().set(indexAléatoire,
                               TransformateurItinéraire.transformation2opt(solutionBase.getItinéraires().get(indexAléatoire), Transformation.TransformationLocale));
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

    public static Itinéraire recuitSimuléIt(Itinéraire itinéraireInitial, double températureInitiale, int changementDeTempérature, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature, Transformation transformation) throws VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, ItinéraireTooSmallException, UnhandledTransformationException
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
                    case TransformationLocale:
                        TransformateurItinéraire.transformationLocale(itinéraireBase);

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
                        itinéraireBase = TransformateurItinéraire.transformation2opt(itinéraireBase, Transformation.TransformationLocale);
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
