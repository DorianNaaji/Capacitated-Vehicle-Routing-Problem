package model;

import customexceptions.VehiculeCapacityOutOfBoundsException;

import java.util.ArrayList;
import java.util.Objects;

import static utilitaires.Utilitaire.distanceEuclidienne;


/**
 * Un itinéraire représente une liste de clients à fournir + le lieu de départ et le lieu d'arrivée, et donc un parcours.
 */
public class Itinéraire
{
    /**
     * La liste chaînée des clients
     */
    private ArrayList<Client> itinéraire;

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
     * Constructeur d'un itinéraire.
     * @param clients la liste des clients concernés par la tournée
     */
    public Itinéraire(ArrayList<Client> clients) throws VehiculeCapacityOutOfBoundsException {

        this.véhicule = new Véhicule();

        int quantiteDeMarchandisesTotale  = clients.stream().mapToInt(c -> c.getQuantite()).sum();

        if (quantiteDeMarchandisesTotale > véhicule.getCapacité()) {
            throw new VehiculeCapacityOutOfBoundsException("La capacité du véhicule est dépassée. (" + quantiteDeMarchandisesTotale + ")");
        }
        this.itinéraire = clients;


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

    public boolean ajouterClient(Client c) throws VehiculeCapacityOutOfBoundsException {
        int capacité = this.nbMarchandisesALivrer;
        ArrayList<Client> cli = this.itinéraire;
        cli.add(c);
        Itinéraire i = new Itinéraire(cli);
        if (i.getNbMarchandisesALivrer() > véhicule.getCapacité()) {
            this.itinéraire.add(c);
            this.recalculerDistanceEtNbMarchandises();
            return true;
        }
       else {
           return false;
        }


        //todo : Ne pas oublier l'entrepôt
        // Recalcul de la longueur totale du parcours

    }

    public void retirerClient(Client c)
    {
        this.itinéraire.remove(c);
        // todo : Ne pas oublier l'entrepôt
        //  Recalculer la longueur totale du parcours

        this.recalculerDistanceEtNbMarchandises();

    }

    private void recalculerDistanceEtNbMarchandises() {

        this.longueurTotale = 0;
        this.nbMarchandisesALivrer = 0;

        for (int i = 0; i < itinéraire.size() - 1; i++) {
            longueurTotale += distanceEuclidienne(itinéraire.get(i).getPositionX(), itinéraire.get(i).getPositionY(), itinéraire.get(i+1).getPositionX(), itinéraire.get(i+1).getPositionY());
            this.nbMarchandisesALivrer += itinéraire.get(i).getQuantite();
        }

        //On ajoute le nombre de marchandises du dernier client car la boucle précédente s'arrête à l'avant dernier client
        //todo : a enlever puisque dernier sommet correspond à l'entrepôt
        this.nbMarchandisesALivrer += itinéraire.get(itinéraire.size() - 1).getQuantite();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinéraire that = (Itinéraire) o;
        return Double.compare(that.longueurTotale, longueurTotale) == 0 &&
                nbMarchandisesALivrer == that.nbMarchandisesALivrer &&
                Objects.equals(itinéraire, that.itinéraire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itinéraire, longueurTotale, nbMarchandisesALivrer);
    }
}


