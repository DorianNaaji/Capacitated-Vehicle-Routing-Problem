package customexceptions;

import algorithms.Transformation;

public class UnhandledTransformationException extends Exception
{

    public UnhandledTransformationException(Transformation transfo, Class<?> classeConcernée)
    {
        super("La transformation " + transfo.toString() + " n'est pas gérée dans la classe " + classeConcernée.toString() + ".");
        this.transformation = transfo;
        this.classeConcernée = classeConcernée;
    }

    public UnhandledTransformationException(Transformation transfo, Class<?> classeConcernée, String err)
    {
        super("La transformation " + transfo.toString() + " n'est pas gérée dans la classe " + classeConcernée.toString() + ". Rapport complet : " + err);
        this.transformation = transfo;
        this.classeConcernée = classeConcernée;
    }

    public UnhandledTransformationException(Transformation transfo)
    {
        super("La transformation " + transfo.toString() + " n'est pas gérée.");
        this.transformation = transfo;
        this.classeConcernée = null;
    }

    private Transformation transformation;

    private Class<?> classeConcernée;
}
