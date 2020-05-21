package algorithms;

import model.Itinéraire;
import model.Solution;

import java.util.Random;

public class OptimisateurDeSolutions implements IOptimisateur {
    //todo:le rôle de l'optimisateur est d'optimiser des solutions. Il implémentera par exemple Tabou ou recuit.
    // Il effectuera des transformations sur les solutions jusqu'à amélioration des solutions.

    private Solution solution;

    public OptimisateurDeSolutions(Solution s) {
        this.solution = s;
    }

    public Solution recuitSimulé(Solution solutionInitiale, double températureInitiale, double nombreVoisinsParTempérature, double coefficientDeDiminuationTempérature) {

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


                int indexAléatoire = random.nextInt(solutionBase.getItinéraires().size());
                // création d'une solution voisine à partir de solutionBase
                TransformateurDeSolutions transformateurDeSolutions = new TransformateurDeSolutions(solutionBase);
                transformateurDeSolutions.transformationLocale(solutionBase.getItinéraires().get(indexAléatoire));
                //transformateurDeSolutions.transformationLocale(solutionBase.getItinéraires().get(1));
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
        System.out.println(meilleureSolution.getOptimisationGlobale());
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

                TransformateurDeSolutions transformateurDeSolutions = new TransformateurDeSolutions(solution);
                transformateurDeSolutions.transformationLocale(itinéraireBase);


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
