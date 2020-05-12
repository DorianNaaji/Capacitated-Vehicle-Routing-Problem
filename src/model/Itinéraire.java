package model;

import customexceptions.EntrepôtNotFoundException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.graph.Sommet;

import java.util.*;

import static utilitaires.Utilitaire.distanceEuclidienne;


/**
 * Un Itinéraire représente une liste de clients à fournir + le lieu de départ et le lieu d'arrivée, et donc un parcours.
 */
public class Itinéraire
{
    /**
     * La liste chaînée des clients
     */
    private LinkedList<Client> listeClientsÀLivrer;

    private Entrepôt départEtArrivée;

    //!\ TODO ATTENTION : rajouter l'entrepot pour le calcul de l'itinéraire / changer en SOMMET les clients

    /**
     * La longueur totale du parcours
     */
    private double longueurTotale;

    /**
     * La nombre de marchandises total à livrer
     */
    private int nbMarchandisesALivrer;

    private Véhicule véhicule;

    /**
     * Constructeur d'un Itinéraire. Les sommets peuvent être récupérés dans l'attribut "sommets" de la classe
     * GrapheNonOrientéComplet.
     * @param sommets les sommets de l'itinéraire, comprenant l'entrepôt et l'ensemble des clients à livrer.
     * @throws VehiculeCapacityOutOfBoundsException
     * @throws EntrepôtNotFoundException
     * @see model.graph.GrapheNonOrientéComplet
     */
    public Itinéraire(Set<Sommet> sommets) throws VehiculeCapacityOutOfBoundsException, EntrepôtNotFoundException
    {
        // on initialise le véhicule
        this.véhicule = new Véhicule();
        this.listeClientsÀLivrer = new LinkedList<Client>();

        // pour chaque sommet de l'ensemble
        for (Sommet s:sommets)
        {
            // si c'est bien un client,
            if(s.getClass() == Client.class)
            {
                // on l'ajoute à notre set de clients.
                listeClientsÀLivrer.add((Client)s);
            }
            if(s.getClass() == Entrepôt.class)
            {
                this.départEtArrivée = (Entrepôt)s;
            }
        }
        // si l'on n'a pas trouvé l'entrepôt...
        if(this.départEtArrivée == null)
        {
            {
                throw new EntrepôtNotFoundException("L'entrepôt n'a pas été trouvé en position 0 de l'ensemble des sommets");
            }
        }

        int quantiteDeMarchandisesTotale  = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();
        if (quantiteDeMarchandisesTotale > véhicule.getCapacité()) {
            throw new VehiculeCapacityOutOfBoundsException("La capacité du véhicule est dépassée. (" + quantiteDeMarchandisesTotale + ")");
        }

        //Calcul de la longueur totale et du nombre de marchandises.
        this.recalculerDistanceEtNbMarchandises();
    }


    /**
     *
     * @return la longueur totale de la tournée
     */
    public double getLongueurTotale()
    {
        return this.longueurTotale;
    }

    /**
     *
     * @return le nombre total de marchandises de la tournée
     */
    public int getNbMarchandisesALivrer() {
        return nbMarchandisesALivrer;
    }

    public void setNbMarchandisesALivrer(int nbMarchandisesALivrer) {
        this.nbMarchandisesALivrer = nbMarchandisesALivrer;
    }

    public boolean ajouterClient(Client c) throws VehiculeCapacityOutOfBoundsException {

        int quantiteDeMarchandisesTotale  = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();

        if (quantiteDeMarchandisesTotale + c.getNbMarchandisesÀLivrer() < véhicule.getCapacité())
        {
            this.listeClientsÀLivrer.add(c);
            this.recalculerDistanceEtNbMarchandises();
            return true;
        }
        else
        {
            return false;
        }
    }

    public void retirerClient(Client c)
    {
        this.listeClientsÀLivrer.remove(c);
        this.recalculerDistanceEtNbMarchandises();
    }

    private void recalculerDistanceEtNbMarchandises() {

        // si la liste contient un seul élément
        if(listeClientsÀLivrer.size() == 0) {
            this.longueurTotale = 0;
            this.nbMarchandisesALivrer = 0;
        }
        // si elle en contient 1
        else if(listeClientsÀLivrer.size() == 1)
        {

            // distance entre l'entrepôt et le client, puis entre le client et l'entrepôt.
            this.longueurTotale = distanceEuclidienne(
                    this.départEtArrivée.getPositionX(),
                    this.départEtArrivée.getPositionY(),
                    this.listeClientsÀLivrer.get(0).getPositionX(),
                    this.listeClientsÀLivrer.get(0).getPositionY()) * 2;

            this.nbMarchandisesALivrer = this.listeClientsÀLivrer.get(0).getNbMarchandisesÀLivrer();
        }
        // sinon...
        else
        {
            // calcul de la distance entre le premier client et l'entrepôt
            this.longueurTotale += distanceEuclidienne(
                    this.départEtArrivée.getPositionX(),
                    this.départEtArrivée.getPositionY(),
                    this.listeClientsÀLivrer.get(0).getPositionX(),
                    this.listeClientsÀLivrer.get(0).getPositionY());

            //calcul de la distance entre chaque clients de la liste
            for (int i = 0; i < listeClientsÀLivrer.size() - 1; i++) {
                longueurTotale += distanceEuclidienne(listeClientsÀLivrer.get(i).getPositionX(), listeClientsÀLivrer.get(i).getPositionY(), listeClientsÀLivrer.get(i+1).getPositionX(), listeClientsÀLivrer.get(i+1).getPositionY());
                this.nbMarchandisesALivrer += listeClientsÀLivrer.get(i).getNbMarchandisesÀLivrer();
            }


            // calcul de la distance entre le dernier client et l'entrepôt.
            this.longueurTotale += distanceEuclidienne(
                    this.listeClientsÀLivrer.get(this.listeClientsÀLivrer.size() - 1).getPositionX(),
                    this.listeClientsÀLivrer.get(this.listeClientsÀLivrer.size() - 1).getPositionY(),
                    this.départEtArrivée.getPositionX(),
                    this.départEtArrivée.getPositionY());
        }




        //On ajoute le nombre de marchandises du dernier client car la boucle précédente s'arrête à l'avant dernier client
        //todo : a enlever puisque dernier sommet correspond à l'entrepôt
        //this.nbMarchandisesALivrer += listeClientsÀLivrer.get(listeClientsÀLivrer.size() - 1).getNbMarchandisesÀLivrer();

    }

    /**
     * Récupère l'entrepôt de départ de l'itinéraire.
     * @return le sommet de départ.
     */
    public Sommet getEntrepôt()
    {
        return this.départEtArrivée;
    }

    /**
     * Récupère la liste des clients à livrer.
     * @return la liste chaînée des clients à livrer.
     */
    public LinkedList<Client> getListeClientsÀLivrer()
    {
        return this.listeClientsÀLivrer;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinéraire that = (Itinéraire) o;
        return Double.compare(that.longueurTotale, longueurTotale) == 0 &&
                nbMarchandisesALivrer == that.nbMarchandisesALivrer &&
                Objects.equals(listeClientsÀLivrer, that.listeClientsÀLivrer) &&
                Objects.equals(départEtArrivée, that.départEtArrivée) &&
                Objects.equals(véhicule, that.véhicule);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(listeClientsÀLivrer, départEtArrivée, longueurTotale, nbMarchandisesALivrer, véhicule);
    }
}


