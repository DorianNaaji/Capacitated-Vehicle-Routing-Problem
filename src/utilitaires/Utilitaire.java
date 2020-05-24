package utilitaires;

import customexceptions.SubdivisionAlgorithmException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.Client;
import model.Entrepôt;
import model.Itinéraire;
import model.Solution;

import java.util.ArrayList;

public class Utilitaire
{


    /**
     * Méthode permettant de calculer la distance euclidienne entre deux éléments
     *
     * @param positionX1
     * @param positionX2
     * @param positionY1
     * @param positionY2
     * @return la distance euclidienne entre deux éléments
     */
    public static double distanceEuclidienne(int positionX1, int positionY1, int positionX2, int positionY2)
    {

        double distanceEuclidienne = Math.sqrt(Math.pow(positionX2 - positionX1, 2) + Math.pow(positionY2 - positionY1, 2));

        return distanceEuclidienne;
    }


    /**
     * Méthode prenant une solution en paramètre ne respectant pas les règles métier et composée
     * d'un itinéraire unique, pour la subdiviser en itinéraires plus petits.
     *
     * @param s la solution dont l'itinéraire unique ne respecte pas les règles métiers.
     * @return la solution de base avec désormais plusieurs itinéraires qui respectent les règles métier (capacité du véhicule).
     * @see algorithms.Tabou
     */
    public static Solution subdiviserSolutionItinéraireUniqueEnPlusieursItinéraires(Solution s) throws SubdivisionAlgorithmException, VehiculeCapacityOutOfBoundsException
    {
        try
        {
            Itinéraire itinéraireUniqueSolutionBase = s.getItinéraires().get(0);
            Entrepôt entrepôt = (Entrepôt) itinéraireUniqueSolutionBase.getEntrepôt();
            ArrayList<Client> clients = new ArrayList<Client>(itinéraireUniqueSolutionBase.getListeClientsÀLivrer());
            Itinéraire itinéraire = new Itinéraire(entrepôt);
            ArrayList<Itinéraire> itinéraires = new ArrayList<>();
            itinéraires.add(itinéraire);

            Solution nouvelleSolution = new Solution();


            while (clients.size() > 0)
            {
                // on ajoute le premier client à chaque fois au dernier itinéraire.
                // On retire snuite ce client de la liste
                if (itinéraires.get(itinéraires.size() - 1).ajouterClient(clients.get(0)))
                {
                    clients.remove(0);
                }
                else
                {
                    Itinéraire nouvelItinéraire = new Itinéraire(entrepôt);
                    nouvelItinéraire.getListeClientsÀLivrer().add(clients.get(0));
                    clients.remove(0);
                    itinéraires.add(nouvelItinéraire);
                }
            }
            return new Solution(itinéraires);
        }
        catch(Exception e)
        {
            throw new SubdivisionAlgorithmException(e);
        }
    }
}
