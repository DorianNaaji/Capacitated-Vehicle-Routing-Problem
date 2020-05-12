package algorithms;

import model.Itinéraire;
import model.Solution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Un transformateur de solution, implémentant l'interface ITransformateur,
 * est une classe permettant d'appliquer des transformations à une solution.
 */
public class TransformateurDeSolutions implements ITransformateur
{
    private Solution solution;

    private TransformateurDeSolutions(Solution s)
    {
        this.solution = s;
    }

    @Override
    public void transformationLocale(Itinéraire i1)
    {
        //todo (voir doc de la méthode)
        throw new NotImplementedException();
    }

    @Override
    public void insertionDécalage(Itinéraire i1)
    {
        //todo (voir doc de la méthode)
        throw new NotImplementedException();
    }

    @Override
    public void inversion(Itinéraire i1)
    {
        //todo (voir doc de la méthode)
        throw new NotImplementedException();
    }

    @Override
    public void transformation2opt(Itinéraire i1)
    {
        //todo (voir doc de la méthode)
        throw new NotImplementedException();
    }
}
