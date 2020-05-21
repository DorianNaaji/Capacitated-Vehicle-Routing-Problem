package customexceptions;

import model.Itinéraire;

public class DeuxOptAlgorithmException extends Exception
{
    public DeuxOptAlgorithmException(Itinéraire itinéraire, VehiculeCapacityOutOfBoundsException _innerException)
    {
        super("Quelque chose n'a pas fonctionné dans la transformation 2-opt avec l'itinéraire utilisé, provoquant" +
                " l'exception interne suivante : " + _innerException.getMessage());
        this.innerException = _innerException;
        this.itinéraireConcerné = itinéraire;
    }

    private VehiculeCapacityOutOfBoundsException innerException;
    private Itinéraire itinéraireConcerné;

    public VehiculeCapacityOutOfBoundsException getInnerException()
    {
        return innerException;
    }

    public Itinéraire getItinéraireConcerné()
    {
        return itinéraireConcerné;
    }
}
