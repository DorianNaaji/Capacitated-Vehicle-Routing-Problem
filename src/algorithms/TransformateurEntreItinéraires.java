package algorithms;

import customexceptions.VehiculeCapacityOutOfBoundsException;
import javafx.util.Pair;
import model.Client;
import model.Itinéraire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Classe permettant de réaliser des transformations inter-itinéraires.
 * Uniquement la transformation échange entre itinéraires est possible pour l'instant.
 */
public class TransformateurEntreItinéraires {


    private static Random r = new Random();

    /**
     * Échange deux clients de deux itinéraires différents
     * @param i1 premier itinéraire
     * @param i2 second itinéraire
     */
    public static void métaTransformationÉchange(Itinéraire i1, Itinéraire i2, int nbÉchanges)
    {
        for(int i = 0; i < nbÉchanges; i++)
        {
            Pair<Integer, Integer> indexes = TransformateurEntreItinéraires.piocheIndexes(i1.getListeClientsÀLivrer().size(), i2.getListeClientsÀLivrer().size());;
            // on vérifie si le changement est possible
            int breakIfTooManyAttemps = 100;
            int cpt = 0;

            boolean canSwap = true;
            boolean stillTry = true;

            boolean i1IsOk = ( (i1.getNbMarchandisesALivrer()
                    - i1.getListeClientsÀLivrer().get(indexes.getKey()).getNbMarchandisesÀLivrer()
                    + i2.getListeClientsÀLivrer().get(indexes.getValue()).getNbMarchandisesÀLivrer()) < (i1.getVéhicule().getCapacité()) );

            boolean i2IsOk =( (i2.getNbMarchandisesALivrer()
                    + i1.getListeClientsÀLivrer().get(indexes.getKey()).getNbMarchandisesÀLivrer()
                    - i2.getListeClientsÀLivrer().get(indexes.getValue()).getNbMarchandisesÀLivrer()) < (i2.getVéhicule().getCapacité()) );


            while(! (i1IsOk) & !(i2IsOk) && (stillTry)
            )
            {
                cpt++;
                indexes = TransformateurEntreItinéraires.piocheIndexes(i1.getListeClientsÀLivrer().size(), i2.getListeClientsÀLivrer().size());
                if(breakIfTooManyAttemps == cpt)
                {
                    stillTry = false;
                    canSwap = false;
                }
            }

            // une fois qu'il est possible de swap

            if(canSwap)
            {
                Client c1 = i1.getListeClientsÀLivrer().get(indexes.getKey());
                Client c2 = i2.getListeClientsÀLivrer().get(indexes.getValue());

                Client c1Copie = new Client(c1.getNumeroClient(), c1.getPositionX(), c1.getPositionY(), c1.getNbMarchandisesÀLivrer());
                Client c2Copie = new Client(c2.getNumeroClient(), c2.getPositionX(), c2.getPositionY(), c2.getNbMarchandisesÀLivrer());


                i1.getListeClientsÀLivrer().set(indexes.getKey(), c1Copie);
                i2.getListeClientsÀLivrer().set(indexes.getValue(), c2Copie);
            }
        }
    }

    private static Pair<Integer, Integer> piocheIndexes(int size_i1, int size_i2)
    {
        return new Pair<Integer, Integer>(r.nextInt(size_i1), r.nextInt(size_i2));
    }
}
