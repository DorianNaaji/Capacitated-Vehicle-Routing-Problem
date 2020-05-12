package model;

import customexceptions.EntrepôtNotFoundException;
import customexceptions.SetOfSommetsIsEmptyException;
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
     * Constructeur d'un Itinéraire.
     * @param sommets la liste des sommets concernés par la tournée
     */
    public Itinéraire(Set<Sommet> sommets) throws VehiculeCapacityOutOfBoundsException, EntrepôtNotFoundException, SetOfSommetsIsEmptyException {
        // on initialise le véhicule
        this.véhicule = new Véhicule();

        //Si la collection de sommets est vide
        if(sommets.isEmpty()) {
            throw new SetOfSommetsIsEmptyException("La collection de sommets est vide.");
        }
        // Le sommet en position 0 est normalement l'entrepôt
        else if (sommets.toArray()[0].getClass() == Entrepôt.class)
        {
            this.départEtArrivée = (Entrepôt)sommets.toArray()[0];
        }
        // si on n'a pas trouvé l'entrepôt en position 0...
        else
        {
            throw new EntrepôtNotFoundException("L'entrepôt n'a pas été trouvé en position 0 de l'ensemble des sommets.");
        }

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
        }

        int quantiteDeMarchandisesTotale  = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();
        if (quantiteDeMarchandisesTotale > véhicule.getCapacité()) {
            throw new VehiculeCapacityOutOfBoundsException("La capacité du véhicule est dépassée. (" + quantiteDeMarchandisesTotale + ")");
        }

        //Calcul de la longueur totale et du nombre de marchandises.
        this.recalculerDistanceEtNbMarchandises();
    }

    public Itinéraire()  {


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

    /**
     * Méthode permettant d'ajouter un client à un itinéraire. Elle permet également de recalculer la longueur totale de l'itinéraire
     * et le nombre total de marchandises à livrer.
     * @param c
     * @return true si la quantité de marchandises totale ne dépasse pas la capacité maximum d'un véhicule, false sinon
     * @throws VehiculeCapacityOutOfBoundsException
     */
    public boolean ajouterClient(Client c) throws VehiculeCapacityOutOfBoundsException {

        // quantité de marchandises totale à livrer avant l'ajout du nouveau client
        int quantiteDeMarchandisesTotale  = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();

        // si la quantité de marchandises totale à livrer en tenant également compte de celle du nouveau client
        // est inférieure ou égale à la capacité totale d'un véhicule
        if (quantiteDeMarchandisesTotale + c.getNbMarchandisesÀLivrer() <= véhicule.getCapacité())
        {
            // on ajoute alors le client à la liste chaînée
            this.listeClientsÀLivrer.add(c);
            // on recalcul alors la longueur d'un itinéraire et le nombre de marchandises à livrer
            this.recalculerDistanceEtNbMarchandises();
            return true;
        }
        // sinon...
        else
        {
            return false;
        }
    }

    /**
     * Méthode permettant de retirer un client à un itinéraire. Elle permet également de recalculer la longueur totale de l'itinéraire
     * et le nombre total de marchandises à livrer.
     * @param c
     */
    public void retirerClient(Client c)
    {
        this.listeClientsÀLivrer.remove(c);
        this.recalculerDistanceEtNbMarchandises();
    }

    /**
     * Méthode permettant de calculer la longueur totale de l'itinéraire et le nombre total de marchandises à livrer.
     */
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
            this.longueurTotale = distanceEuclidienne(
                    this.départEtArrivée.getPositionX(),
                    this.départEtArrivée.getPositionY(),
                    this.listeClientsÀLivrer.get(0).getPositionX(),
                    this.listeClientsÀLivrer.get(0).getPositionY());

            //calcul de la distance entre chaque clients de la liste
            for (int i = 0; i < listeClientsÀLivrer.size() - 1; i++) {
                longueurTotale += distanceEuclidienne(listeClientsÀLivrer.get(i).getPositionX(), listeClientsÀLivrer.get(i).getPositionY(), listeClientsÀLivrer.get(i+1).getPositionX(), listeClientsÀLivrer.get(i+1).getPositionY());
            }

            // calcul de la distance entre le dernier client et l'entrepôt.
            this.longueurTotale += distanceEuclidienne(
                    this.listeClientsÀLivrer.get(this.listeClientsÀLivrer.size() - 1).getPositionX(),
                    this.listeClientsÀLivrer.get(this.listeClientsÀLivrer.size() - 1).getPositionY(),
                    this.départEtArrivée.getPositionX(),
                    this.départEtArrivée.getPositionY());

            this.nbMarchandisesALivrer = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();
        }

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


