package algorithms;

import model.Solution;

public class OptimisateurDeSolutions implements IOptimisateur
{
    //todo:le rôle de l'optimisateur est d'optimiser des solutions. Il implémentera par exemple Tabou ou recuit.
    // Il effectuera des transformations sur les solutions jusqu'à amélioration des solutions.

    private Solution solution;

    private OptimisateurDeSolutions(Solution s)
    {
        this.solution = s;
    }
}
