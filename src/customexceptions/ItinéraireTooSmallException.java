package customexceptions;

import model.Itinéraire;

public class ItinéraireTooSmallException extends Exception
{
    private Itinéraire itinéraire;

    public Itinéraire getItinéraire()
    {
        return itinéraire;
    }

    public ItinéraireTooSmallException(Itinéraire it)
    {
        super("L'itinéraire fourni est trop petit (" + it.getListeClientsÀLivrer().size() + " clients).");
        this.itinéraire = it;
    }
}
