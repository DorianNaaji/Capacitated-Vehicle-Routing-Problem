package algorithms;

import customexceptions.ItinéraireTooSmallException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.Itinéraire;
import model.Solution;

import java.util.Random;

public class RecuitSimulé
{

    public static Solution recuitSimulé(Solution solutionInitiale, double températureInitiale, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature, Transformation transformation) throws VehiculeCapacityOutOfBoundsException, ItinéraireTooSmallException, ListOfClientsIsEmptyException
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
                       solutionBase.getItinéraires().set(indexAléatoire, TransformateurItinéraire.transformation2opt(solutionBase.getItinéraires().get(indexAléatoire)));
                       break;
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

    public Itinéraire recuitSimuléIt(Itinéraire itinéraireInitial, double températureInitiale, int changementDeTempérature, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature) throws CloneNotSupportedException {

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

                TransformateurItinéraire.transformationLocale(itinéraireBase);


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
        System.out.println(meilleurItinéraire.getLongueurTotale());
        return meilleurItinéraire;
    }
}
