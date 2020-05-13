package model;

import customexceptions.EntrepôtNotFoundException;
import customexceptions.ListOfClientsIsEmptyException;
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

    /**
     * L'entrepôt est à la fois le départ et l'arrivée de l'itinéraire.
     */
    private Entrepôt entrepôt;

    /**
     * La longueur totale du parcours
     */
    private double longueurTotale;

    /**
     * La nombre de marchandises total à livrer
     */
    private int nbMarchandisesALivrer;

    /**
     * Le véhicule utilisé pour l'itinéraire courant.
     */
    private Véhicule véhicule;

    /**
     * Constructeur d'un itinéraire. Il prend en paramètre une liste chaînée de clients, qui correspond à l'ordre
     * des clients à livrer. L'entrepôt doit également être spécifié. Il sera le point de départ et l'arrivée de l'itinéraire
     * @param clients la liste chaînée des clients à livrer.
     * @param e l'entrepôt, point de départ et point d'arrivée de notre itinéraire.
     * @throws ListOfClientsIsEmptyException dans le cas où la liste de clients est vide.
     * @throws VehiculeCapacityOutOfBoundsException dans le cas où le nombre de marchandises à livrer pour l'itinéraire
     * dépasse la capacité totale du véhicule.
     */
    public Itinéraire(LinkedList<Client> clients, Entrepôt e) throws ListOfClientsIsEmptyException, VehiculeCapacityOutOfBoundsException
    {
        if(clients.isEmpty())
        {
            throw new ListOfClientsIsEmptyException("La collection de sommets est vide.");
        }

        // on initialise le véhicule
        this.véhicule = new Véhicule();
        this.listeClientsÀLivrer = clients;
        this.entrepôt = e;

        int quantiteDeMarchandisesTotale  = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();
        if (quantiteDeMarchandisesTotale > véhicule.getCapacité()) {
            throw new VehiculeCapacityOutOfBoundsException("La capacité du véhicule est dépassée. (" + quantiteDeMarchandisesTotale + ")");
        }

        //Calcul de la longueur totale et du nombre de marchandises.
        this.recalculerDistanceEtNbMarchandises();
    }

    /**
     * Constructeur d'un itinéraire. Initialise un Itinéraire possédant une liste chaînée de Clients vide.
     * @param e l'entrepôt, point de départ et point d'arrivée de notre itinéraire.
     */
    public Itinéraire(Entrepôt e) {
        // on initialise le véhicule
        this.véhicule = new Véhicule();
        this.entrepôt = e;
        this.listeClientsÀLivrer = new LinkedList<>();

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

    /**
     * Méthode permettant d'ajouter un client à un itinéraire. Elle permet également de recalculer la longueur totale de l'itinéraire
     * et le nombre total de marchandises à livrer.
     * @param c le client à ajouter.
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
     * @param c le client à retirer.
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
                    this.entrepôt.getPositionX(),
                    this.entrepôt.getPositionY(),
                    this.listeClientsÀLivrer.get(0).getPositionX(),
                    this.listeClientsÀLivrer.get(0).getPositionY()) * 2;

            this.nbMarchandisesALivrer = this.listeClientsÀLivrer.get(0).getNbMarchandisesÀLivrer();
        }
        // sinon...
        else
        {
            // calcul de la distance entre le premier client et l'entrepôt
            this.longueurTotale = distanceEuclidienne(
                    this.entrepôt.getPositionX(),
                    this.entrepôt.getPositionY(),
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
                    this.entrepôt.getPositionX(),
                    this.entrepôt.getPositionY());

            this.nbMarchandisesALivrer = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();
        }

    }

    /**
     * Récupère l'entrepôt de départ de l'itinéraire.
     * @return le sommet de départ.
     */
    public Sommet getEntrepôt()
    {
        return this.entrepôt;
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
                Objects.equals(entrepôt, that.entrepôt) &&
                Objects.equals(véhicule, that.véhicule);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(listeClientsÀLivrer, entrepôt, longueurTotale, nbMarchandisesALivrer, véhicule);
    }
}


