package algorithms;

import customexceptions.VehiculeCapacityOutOfBoundsException;
import model.Client;
import model.Itinéraire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TransformateurEntreItinéraires {


    /**
     * Transforme une solution, en échangeant de place un client choisis arbitrairement parmi itinéraire1 et
     * un client choisis arbitrairement parmi itinéraire2.
     * @param itinéraire1 l'itinéraire au sein duquel la transformation sera effectuée
     * @param itinéraire2 l'itinéraire au sein duquel la transformation sera effectuée
     * @throws VehiculeCapacityOutOfBoundsException
     */
    public static void transformationÉchange(Itinéraire itinéraire1, Itinéraire itinéraire2) throws VehiculeCapacityOutOfBoundsException {
        // nombre de clients dans l'itinéraire1
        int nbClientsItinéraire1 = itinéraire1.getListeClientsÀLivrer().size();
        // nombre de clients dans l'itinéraire2
        int nbClientsItinéraire2 = itinéraire2.getListeClientsÀLivrer().size();

        Random random = new Random();

        boolean possibilitéAjoutClientDansItinéraire1 = false;
        boolean possibilitéAjoutClientDansItinéraire2 = false;
        int premierIndexAléatoire = 0;
        int deuxièmeIndexAléatoire = 0;
        // création d'une liste à 2 dimensions qui contiendra les couples d'index aléatoire
        ArrayList<ArrayList<Integer>> listeDeCouplesIndexAléatoire = new ArrayList<>();

        Client clientAInsérerDansItinéraire1 = new Client();
        Client clientAInsérerDansItinéraire2 = new Client();

        //tant qu'on ne peut pas ajouter un client dans un autre itinéraire (le nombre de marchandises > 0)...
        while (possibilitéAjoutClientDansItinéraire1 == false || possibilitéAjoutClientDansItinéraire2 == false) {
            // génération d'un premier index aléatoire (de 0 à nbClients1-1)
            premierIndexAléatoire = random.nextInt(nbClientsItinéraire1);
            // génération d'un deuxième index aléatoire (de 0 à nbClients2-1)
            deuxièmeIndexAléatoire = random.nextInt(nbClientsItinéraire2);
            // on crée une liste contenant les deux index aléatoires générés précédemment
            ArrayList<Integer> coupleIndexAléatoire = new ArrayList<>();
            coupleIndexAléatoire.add(premierIndexAléatoire);
            coupleIndexAléatoire.add(deuxièmeIndexAléatoire);

            // tant que notre liste listeDeCouplesIndexAléatoire contient le couple d'index aléatoire générés précédemment...
            while(listeDeCouplesIndexAléatoire.contains(coupleIndexAléatoire)) {
                // regénération d'un premier index aléatoire (de 0 à nbClients1-1)
                premierIndexAléatoire = random.nextInt(nbClientsItinéraire1);
                // regénération d'un deuxième index aléatoire (de 0 à nbClients2-1)
                deuxièmeIndexAléatoire = random.nextInt(nbClientsItinéraire2);
                // on crée une liste contenant les deux index aléatoires générés précédemment
                coupleIndexAléatoire = new ArrayList<>();
                coupleIndexAléatoire.add(premierIndexAléatoire);
                coupleIndexAléatoire.add(deuxièmeIndexAléatoire);
            }

            // on ajoute le couple d'index aléatoire à notre liste à 2 dimensions
            listeDeCouplesIndexAléatoire.add(coupleIndexAléatoire);

            // on crée une copie de notre itinéraire1
            Itinéraire itinéraire1Copie = new Itinéraire(itinéraire1);
            // on crée une copie de notre itinéraire2
            Itinéraire itinéraire2Copie = new Itinéraire(itinéraire2);
            // on récupère le client positionné au premierIndexAléatoire de la copie de notre itinéraire1
            clientAInsérerDansItinéraire2 = itinéraire1Copie.getListeClientsÀLivrer().get(premierIndexAléatoire);
            // on retire ce client
            itinéraire1Copie.retirerClient(clientAInsérerDansItinéraire2);
            // on récupère le client positionné au deuxièmeIndexAléatoire de la copie de notre itinéraire2
            clientAInsérerDansItinéraire1 = itinéraire2Copie.getListeClientsÀLivrer().get(deuxièmeIndexAléatoire);
            // on retire ce client
            itinéraire2Copie.retirerClient(clientAInsérerDansItinéraire1);

            // on vérifie s'il est possible d'ajouter le client retiré de l'itinéraire 2 à la prosition premierIndexAléatoire
            possibilitéAjoutClientDansItinéraire1 = itinéraire1Copie.ajouterClientAUnIndex(clientAInsérerDansItinéraire1, premierIndexAléatoire);
            // on vérifie s'il est possible d'ajouter le client retiré de l'itinéraire 1 à la prosition deuxièmeIndexAléatoire
            possibilitéAjoutClientDansItinéraire2 = itinéraire2Copie.ajouterClientAUnIndex(clientAInsérerDansItinéraire2, deuxièmeIndexAléatoire);
        }

        //si les deux clients peuvent être échangés sans que le nombre total de marchandise de chaque itinéraire ne dépasse 100
        // alors on réalise les opérations précédentes sur itinéraire1 et itinéraire2 (et non plus les copies)
        // Les copies servaient à vérifier si nous pouvons échanger les deux clients tirés aléatoirement.
        itinéraire1.retirerClient(clientAInsérerDansItinéraire2);
        itinéraire2.retirerClient(clientAInsérerDansItinéraire1);
        if (premierIndexAléatoire > itinéraire1.getListeClientsÀLivrer().size()) {
            itinéraire1.ajouterClientAUnIndex(clientAInsérerDansItinéraire1, premierIndexAléatoire - 1);
        }
        else {
            itinéraire1.ajouterClientAUnIndex(clientAInsérerDansItinéraire1, premierIndexAléatoire);
        }
        if (deuxièmeIndexAléatoire > itinéraire2.getListeClientsÀLivrer().size()) {
            itinéraire2.ajouterClientAUnIndex(clientAInsérerDansItinéraire2, deuxièmeIndexAléatoire - 1);
        }
        else {
            itinéraire2.ajouterClientAUnIndex(clientAInsérerDansItinéraire2, deuxièmeIndexAléatoire);
        }

        }

    /**
     * Effectue une opération d'insertion décalage d'un client entre deux itinéraires.
     * Un client à une position i est pioché aléatoirement dans itinéraire1, retiré de itinéraire1, puis ajouté
     * à une position i+delta ou i-delta, delta étant aléatoirement généré dans l'itinéraire2 et vice-versa.
     * @param itinéraire1 l'itinéraire sur lequel sera effectué l'insertion décalage.
     * @param itinéraire2 l'itinéraire sur lequel sera effectué l'insertion décalage.
     * @throws VehiculeCapacityOutOfBoundsException
     */
    public static void insertionDécalage(Itinéraire itinéraire1, Itinéraire itinéraire2) throws VehiculeCapacityOutOfBoundsException {
        // nombre de clients dans l'itinéraire1
        int nbClientsItinéraire1 = itinéraire1.getListeClientsÀLivrer().size();
        // nombre de clients dans l'itinéraire2
        int nbClientsItinéraire2 = itinéraire2.getListeClientsÀLivrer().size();

        Random random = new Random();

        boolean possibilitéAjoutClientDansItinéraire1 = false;
        boolean possibilitéAjoutClientDansItinéraire2 = false;
        int premierIndexAléatoire = 0;
        int deuxièmeIndexAléatoire = 0;
        int troisièmeIndexAléatoire = 0;
        int quatrièmeIndexAléatoire = 0;
        Client clientAInsérerDansItinéraire1 = new Client();
        Client clientAInsérerDansItinéraire2 = new Client();

        while (possibilitéAjoutClientDansItinéraire1 == false || possibilitéAjoutClientDansItinéraire2 == false) {
            // génération d'un premier index aléatoire (de 0 à nbClients1-1)
            premierIndexAléatoire = random.nextInt(nbClientsItinéraire1);
            // génération d'un deuxième index aléatoire (de 0 à nbClients2-1)
            deuxièmeIndexAléatoire = random.nextInt(nbClientsItinéraire2);
            // génération d'un troisième index aléatoire (de 0 à nbClients1-1)
            troisièmeIndexAléatoire = random.nextInt(nbClientsItinéraire1);
            // génération d'un quatrième index aléatoire (de 0 à nbClients2-1)
            quatrièmeIndexAléatoire = random.nextInt(nbClientsItinéraire2);

            if (premierIndexAléatoire == troisièmeIndexAléatoire || deuxièmeIndexAléatoire == quatrièmeIndexAléatoire) {
                // regénération d'un troisième index aléatoire (de 0 à nbClients1-1)
                troisièmeIndexAléatoire = random.nextInt(nbClientsItinéraire1);
                //regénération d'un quatrième index aléatoire (de 0 à nbClients2-1)
                quatrièmeIndexAléatoire = random.nextInt(nbClientsItinéraire2);
            }
            Itinéraire itinéraire1Copie = new Itinéraire(itinéraire1);
            Itinéraire itinéraire2Copie = new Itinéraire(itinéraire2);
            clientAInsérerDansItinéraire2 = itinéraire1Copie.getListeClientsÀLivrer().get(premierIndexAléatoire);
            itinéraire1Copie.retirerClient(clientAInsérerDansItinéraire2);
            clientAInsérerDansItinéraire1 = itinéraire2Copie.getListeClientsÀLivrer().get(deuxièmeIndexAléatoire);
            itinéraire2Copie.retirerClient(clientAInsérerDansItinéraire1);
            possibilitéAjoutClientDansItinéraire1 = itinéraire1Copie.ajouterClientAUnIndex(clientAInsérerDansItinéraire1, troisièmeIndexAléatoire);
            possibilitéAjoutClientDansItinéraire2 = itinéraire2Copie.ajouterClientAUnIndex(clientAInsérerDansItinéraire2, quatrièmeIndexAléatoire);
        }

        itinéraire1.retirerClient(clientAInsérerDansItinéraire2);
        itinéraire2.retirerClient(clientAInsérerDansItinéraire1);
        itinéraire1.ajouterClientAUnIndex(clientAInsérerDansItinéraire1, troisièmeIndexAléatoire);
        itinéraire2.ajouterClientAUnIndex(clientAInsérerDansItinéraire2, quatrièmeIndexAléatoire);


    }


    }
