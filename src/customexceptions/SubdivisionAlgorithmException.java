package customexceptions;

public class SubdivisionAlgorithmException extends Exception
{
    public SubdivisionAlgorithmException(Exception innerException)
    {
        super("Erreur lors de la subdivision de l'itinéraire unique d'une solution : " + innerException.getMessage());
    }
}
