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
     * Constructeur permettant de cloner un itinéraire.
     * @param itinéraire l'itinéraire à cloner.
     */
    public Itinéraire(Itinéraire itinéraire) {
        this.listeClientsÀLivrer = new LinkedList<Client>();
        this.entrepôt = new Entrepôt(itinéraire.getEntrepôt().getPositionX(), itinéraire.getEntrepôt().getPositionY());
        for (Client c : itinéraire.getListeClientsÀLivrer()) {
            this.listeClientsÀLivrer.add(new Client(c.getNumeroClient(), c.getPositionX(), c.getPositionY(), c.getNbMarchandisesÀLivrer()));
        }
        this.recalculerDistanceEtNbMarchandises();
        this.véhicule = new Véhicule(itinéraire.getVéhicule());
        //this.longueurTotale = itinéraire.getLongueurTotale();
        //this.nbMarchandisesALivrer = itinéraire.getNbMarchandisesALivrer();

        //this.recalculerDistanceEtNbMarchandises();
        //this.longueurTotale = itinéraire.longueurTotale;
    }

    /**
     * Constructeur d'un itinéraire avec véhicule personnalisé.
     * @param v le véhicule.
     * @param e l'entrepôt.
     */
    public Itinéraire(Véhicule v, Entrepôt e)
    {
        this(e);
        this.véhicule = v;
    }

    /**
     * Constructeur d'un itinéraire. Il prend en paramètre une liste chaînée de clients, qui correspond à l'ordre
     * des clients à livrer. L'entrepôt doit également être spécifié. Il sera le point de départ et l'arrivée de l'itinéraire
     * @param clients la liste chaînée des clients à livrer.
     * @param e l'entrepôt, point de départ et point d'arrivée de notre itinéraire.
     * @throws ListOfClientsIsEmptyException dans le cas où la liste de clients est vide.
     * @throws VehiculeCapacityOutOfBoundsException dans le cas où le nombre de marchandises à livrer pour l'itinéraire
     * dépasse la capacité totale du véhicule.
     */
    public Itinéraire(LinkedList<Client> clients, Entrepôt e, boolean hasVehicleInfiniteCapacity) throws ListOfClientsIsEmptyException, VehiculeCapacityOutOfBoundsException
    {
        if(clients.isEmpty())
        {
            throw new ListOfClientsIsEmptyException("La collection de sommets est vide.");
        }

        // on initialise le véhicule
        if(hasVehicleInfiniteCapacity)
        {
            this.véhicule = new Véhicule(true);
        }
        else
        {
            this.véhicule = new Véhicule();
        }
        this.listeClientsÀLivrer = clients;
        this.entrepôt = e;

        int quantiteDeMarchandisesTotale  = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();
        if(!this.véhicule.isInfinite())
        {
            if (quantiteDeMarchandisesTotale > véhicule.getCapacité()) {
                throw new VehiculeCapacityOutOfBoundsException("La capacité du véhicule est dépassée. (" + quantiteDeMarchandisesTotale + ")");
            }
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
        this.recalculerDistanceEtNbMarchandises();
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
      if (this.getListeClientsÀLivrer().size() == 0) {
          return this.ajouterClientAUnIndex(c, 0);
      }
      else {
          return this.ajouterClientAUnIndex(c, this.getListeClientsÀLivrer().size());
      }

    }

    /**
     * Méthode permettant d'ajouter un client à un itinéraire et à un index donné.
     * Elle permet également de recalculer la longueur totale de l'itinérair et le nombre total de marchandises à livrer.
     * @param c le client à ajouter.
     * @param i l'index où nous voulons ajouter le client
     * @return true si la quantité de marchandises totale ne dépasse pas la capacité maximum d'un véhicule, false sinon
     * @throws VehiculeCapacityOutOfBoundsException
     */
    public boolean ajouterClientAUnIndex(Client c, int i) throws VehiculeCapacityOutOfBoundsException {

        // quantité de marchandises totale à livrer avant l'ajout du nouveau client
        int quantiteDeMarchandisesTotale  = listeClientsÀLivrer.stream().mapToInt(Client::getNbMarchandisesÀLivrer).sum();

        // si la quantité de marchandises totale à livrer en tenant également compte de celle du nouveau client
        // est inférieure ou égale à la capacité totale d'un véhicule

        // si on respecte les règles métiers (car véhicule n'est pas à capacité infinie)
        if(!this.véhicule.isInfinite())
        {
            if (quantiteDeMarchandisesTotale + c.getNbMarchandisesÀLivrer() <= véhicule.getCapacité())
            {
                // on ajoute alors le client à la liste chaînée à un index i
                this.listeClientsÀLivrer.add(i, c);
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
        else
        {
            // on ajoute alors le client à la liste chaînée à un index i
            this.listeClientsÀLivrer.add(i, c);
            // on recalcul alors la longueur d'un itinéraire et le nombre de marchandises à livrer
            this.recalculerDistanceEtNbMarchandises();
            return true;
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
     * Récupère la liste des clients à livrer. Attention, l'ajout sur cette liste ne fera aucune vérification métier.
     * Passer par la méthode "ajouterClient" plutôt. Sinon, les règles métier peuvent potentiellement être violées !
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

    /**
     *
     * ATTENTION, À UTILISER AVEC PRÉCAUTION. Ce setter force directement une LinkedList de clients dans
     * l'attribut clients, sans aucune vérification préalable des règles métier (notamment du dépassement de la
     * capacité du véhicule pour l'itinéraire courant).
     *
     * Cette méthode est utile pour générer des itinéraires uniques pour une solution, mais elle ne doit absolument
     * pas être utilisée à un autre endroit.
     * @param clients les clients à force set.
     */
    public void setForceListeDeClients(LinkedList<Client> clients)
    {
        this.listeClientsÀLivrer = clients;
        this.recalculerDistanceEtNbMarchandises();
    }

    /**
     * 
     * @return le véhicule de l'itinéraire
     */
    public Véhicule getVéhicule()
    {
        return véhicule;
    }
}