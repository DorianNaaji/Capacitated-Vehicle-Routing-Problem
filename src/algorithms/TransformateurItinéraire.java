package algorithms;

import customexceptions.*;
import javafx.util.Pair;
import model.Client;
import model.Entrepôt;
import model.Itinéraire;
import model.Solution;
import model.graph.Sommet;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.management.relation.RelationNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Un transformateur de solution est une classe permettant d'appliquer des transformations à une solution.
 */
public class TransformateurItinéraire
{
    /**
     * Transforme localement une solution, en échangeant de place deux clients choisis arbitrairement parmi itinéraire.
     * @param itinéraire l'itinéraire au sein duquel la transformation sera effectuée.
     */
    public static void transformationÉchange(Itinéraire itinéraire)
    {

        // nombre de clients dans l'itinéraire
        int nbClients = itinéraire.getListeClientsÀLivrer().size();
        Random random = new Random();
        // génération d'un premier index aléatoire (de 0 à nbClients-1)
        int premierIndexAléatoire = random.nextInt(nbClients);
        // génération d'un deuxième index aléatoire (de 0 à nbClients-1)
        int deuxièmeIndexAléatoire = random.nextInt(nbClients);

        // si la liste contient un unique client, on n'apporte aucune modification.
        if(itinéraire.getListeClientsÀLivrer().size() != 1)
        {
            // tant que les deux index aléatoires sont identiques...
            while (premierIndexAléatoire == deuxièmeIndexAléatoire) {
                // on regénère le deuxième index aléatoire
                deuxièmeIndexAléatoire = random.nextInt((nbClients));
            }
        }
        // on échange le client positionné au premierIndexAléatoire avec celui positionné au deuxièmeIndexAléatoire
        Collections.swap(itinéraire.getListeClientsÀLivrer(), premierIndexAléatoire, deuxièmeIndexAléatoire);
    }

    /**
     * Effectue une opération d'insertion décalage d'un client sur un itinéraire.
     * Un client à une position i est pioché aléatoirement dans itinéraire, retiré de itinéraire, puis ajouté
     * à une position i+delta ou i-delta, delta étant aléatoirement généré.
     * @param itinéraire l'itinéraire sur lequel sera effectué l'insertion décalage.
     */
    public static void insertionDécalage(Itinéraire itinéraire)
    {
        // nombre de clients dans l'itinéraire
        int nbClients = itinéraire.getListeClientsÀLivrer().size();
        Random random = new Random();
        // génération d'un premier index aléatoire (de 0 à nbClients-1)
        int premierIndexAléatoire = random.nextInt(nbClients);
        // récupération du client positionné au premierIndexAléatoire
        Client clientAReplacer = itinéraire.getListeClientsÀLivrer().get(premierIndexAléatoire);
        // on retire le client pioché aléatoirement de l'itinéraire
        itinéraire.retirerClient(clientAReplacer);
        // génération d'un deuxième index aléatoire (de 0 à nbClients-1)
        int deuxièmeIndexAléatoire = random.nextInt(nbClients);
        // si la liste contient un unique client, on n'apporte aucune modification.
        if(itinéraire.getListeClientsÀLivrer().size() != 1)
        {
            // tant que les deux index aléatoires sont identiques...
            while (premierIndexAléatoire == deuxièmeIndexAléatoire)
            {
                // on regénère le deuxième index aléatoire
                deuxièmeIndexAléatoire = random.nextInt((nbClients));
            }
        }
        // on ajoute le client pioché aléatoirement à une position aléatoire et les éléments après le deuxièmeIndexAléatoire se décalent
        itinéraire.getListeClientsÀLivrer().add(deuxièmeIndexAléatoire, clientAReplacer);
    }

    /**
     * Inverse une portion entière de k éléments appartenant à l'itinéraire.
     * Par exemple, soit ABCDEFG un itinéraire, et les k éléments = BCDE, alors k inversé = EDCB et
     * l'itinéraire inversé devient AEDCBFG.
     * @param itinéraire l'itinéraire sur lequel effectuer l'inversion
     */
    public static void inversion(Itinéraire itinéraire)
    {
        // nombre de clients dans l'itinéraire
        int nbClients = itinéraire.getListeClientsÀLivrer().size();
        Random random = new Random();
        // génération d'un premier index aléatoire (de 0 à nbClients-1)
        int premierIndexAléatoire = random.nextInt(nbClients);
        // génération d'un deuxième index aléatoire (de 0 à nbClients-1)
        int deuxièmeIndexAléatoire = random.nextInt(nbClients);


        // si la liste contient un unique client, on n'apporte aucune modification.
        if(itinéraire.getListeClientsÀLivrer().size() != 1)
        {
            // tant que les deux index aléatoires sont identiques...
            while (premierIndexAléatoire == deuxièmeIndexAléatoire) {
                // on regénère un deuxième index aléatoire
                deuxièmeIndexAléatoire = random.nextInt(nbClients);
            }
        }

        LinkedList<Client> listeDeClientsÀInverser;
        // si le premierIndexAléatoire est inférieur au deuxièmeIndexAléatoire
        if (premierIndexAléatoire < deuxièmeIndexAléatoire) {
            // on crée une nouvelle liste chaînée contenant la liste de clients à inverser (entre le premierIndexAléatoire et le deuxièmeIndexAléatoire)
            listeDeClientsÀInverser = new LinkedList<>(itinéraire.getListeClientsÀLivrer().subList(premierIndexAléatoire, deuxièmeIndexAléatoire + 1));
            // on enlève la liste de clients à enlever de la liste des clients de l'itinéraire
            itinéraire.getListeClientsÀLivrer().removeAll(listeDeClientsÀInverser);
        // sinon...
        } else {
            // on crée une nouvelle liste chaînée contenant la liste de clients à inverser (entre le deuxièmeIndexAléatoire et le premierIndexAléatoire)
            listeDeClientsÀInverser = new LinkedList<>(itinéraire.getListeClientsÀLivrer().subList(deuxièmeIndexAléatoire, premierIndexAléatoire + 1));
            // on enlève la liste de clients à enlever de la liste des clients de l'itinéraire
            itinéraire.getListeClientsÀLivrer().removeAll(listeDeClientsÀInverser);
        }
        // on inverse tous les éléments contenus dans la liste de clients à inverser
        Collections.reverse(listeDeClientsÀInverser);

        // si le premierIndexAléatoire est inférieur au deuxièmeIndexAléatoire
        if (premierIndexAléatoire < deuxièmeIndexAléatoire) {
            // on ajoute la liste inversée à la liste de client de l'itinéraire au premierIndexAléatoire
            itinéraire.getListeClientsÀLivrer().addAll(premierIndexAléatoire, listeDeClientsÀInverser);
        // sinon...
        } else {
            // on ajoute la liste inversée à la liste de client de l'itinéraire au deuxièmeIndexAléatoire
            itinéraire.getListeClientsÀLivrer().addAll(deuxièmeIndexAléatoire, listeDeClientsÀInverser);
        }
    }

     /**
     * Effectue une transformation 2-opt sur un itinéraire donné.
     * La transformation 2-opt échange deux arêtes disjointes.
     * @param _itinéraire l'itinéraire sur lequel effectuer la transformation 2-opt.
     * @param backUpTransformation La transformation à effectuer si le 2-opt  est impossible sur l'itinéraire "_itinéraire"
     * @throws VehiculeCapacityOutOfBoundsException si la capacité des véhicules est dépassée.
     * @throws ListOfClientsIsEmptyException si un itinéraire est créé avec une liste vide.
     * @return l'itinéraire transformé.
     */
    public static Itinéraire transformation2opt(Itinéraire _itinéraire, Transformation backUpTransformation) throws ItinéraireTooSmallException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException
    {
        //todo : enlever les bouts de code commentés
        // s'il y a 3 clients ou moins dans la liste, ce ne sera pas possible
        if(_itinéraire.getListeClientsÀLivrer().size() < 4)
        {
            //throw new ItinéraireTooSmallException(_itinéraire);
            switch(backUpTransformation)
            {
                case Inversion:
                    TransformateurItinéraire.inversion(_itinéraire);
                    return _itinéraire;
                case InsertionDécalage:
                    TransformateurItinéraire.insertionDécalage(_itinéraire);
                    return _itinéraire;
                case TransformationÉchange:
                    TransformateurItinéraire.transformationÉchange(_itinéraire);
                    return _itinéraire;
                default:
                    throw new ItinéraireTooSmallException(_itinéraire);
            }
        }

        LinkedList<Client> nouvelleListeClients = new LinkedList<>();
        Sommet entrepôt = _itinéraire.getEntrepôt();
        //Itinéraire nouvelItinéraire = new Itinéraire(nouvelleListeClients , (Entrepôt) entrepôt);

        int nbClients = _itinéraire.getListeClientsÀLivrer().size();
        Random random = new Random();
        // génération d'un premier index aléatoire (de 0 à nbClients-1)
        int premierIndexAléatoire = random.nextInt(nbClients);
        // génération d'un deuxième index aléatoire (de 0 à nbClients-1)
        int deuxièmeIndexAléatoire = random.nextInt(nbClients);
        // tant que les deux index aléatoires sont identiques...
        while (premierIndexAléatoire == deuxièmeIndexAléatoire) {
            // on regénère un deuxième index aléatoire
            deuxièmeIndexAléatoire = random.nextInt(nbClients);
        }

        if(premierIndexAléatoire < deuxièmeIndexAléatoire)
        {
            for (int i = 0; i <= premierIndexAléatoire - 1; i++) {
                nouvelleListeClients.add(_itinéraire.getListeClientsÀLivrer().get(i));
            }

            int décalage = 0;
            for (int c = premierIndexAléatoire; c <= deuxièmeIndexAléatoire; c++) {
                nouvelleListeClients.add(_itinéraire.getListeClientsÀLivrer().get(deuxièmeIndexAléatoire - décalage));
                décalage++;
            }

            for (int j = deuxièmeIndexAléatoire + 1; j < nbClients; j++) {
                nouvelleListeClients.add(_itinéraire.getListeClientsÀLivrer().get(j));
                //nouvelItinéraire.ajouterClient(i1.getListeClientsÀLivrer().get(k));
            }
        }
        else
        {
            for (int i = 0; i <= deuxièmeIndexAléatoire - 1; i++) {
                nouvelleListeClients.add(_itinéraire.getListeClientsÀLivrer().get(i));
            }

            int décalage = 0;
            for (int c = deuxièmeIndexAléatoire; c <= premierIndexAléatoire; c++) {

                nouvelleListeClients.add(_itinéraire.getListeClientsÀLivrer().get(premierIndexAléatoire - décalage));
                décalage++;
            }

            for (int j = premierIndexAléatoire + 1; j < nbClients; j++) {
                nouvelleListeClients.add(_itinéraire.getListeClientsÀLivrer().get(j));
            }
        }
        return new Itinéraire(nouvelleListeClients , (Entrepôt) entrepôt);
    }
}
