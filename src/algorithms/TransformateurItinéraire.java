package algorithms;

import customexceptions.DeuxOptAlgorithmException;
import customexceptions.ItinéraireTooSmallException;
import customexceptions.ListOfClientsIsEmptyException;
import customexceptions.VehiculeCapacityOutOfBoundsException;
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
 * Un transformateur de solution, implémentant l'interface ITransformateur,
 * est une classe permettant d'appliquer des transformations à une solution.
 */
public class TransformateurItinéraire implements ITransformateur
{
    @Override
    public void transformationLocale(Itinéraire i1)
    {
        // nombre de clients dans l'itinéraire
        int nbClients = i1.getListeClientsÀLivrer().size();
        Random random = new Random();
        // génération d'un premier index aléatoire (de 0 à nbClients-1)
        int premierIndexAléatoire = random.nextInt(nbClients);
        // génération d'un deuxième index aléatoire (de 0 à nbClients-1)
        int deuxièmeIndexAléatoire = random.nextInt(nbClients);
        // tant que les deux index aléatoires sont identiques...
        while (premierIndexAléatoire == deuxièmeIndexAléatoire) {
            // on regénère le deuxième index aléatoire
            deuxièmeIndexAléatoire = random.nextInt((nbClients));
        }

        // on échange le client positionné au premierIndexAléatoire avec celui positionné au deuxièmeIndexAléatoire
        Collections.swap(i1.getListeClientsÀLivrer(), premierIndexAléatoire, deuxièmeIndexAléatoire);

    }

    @Override
    public void insertionDécalage(Itinéraire i1)
    {
        // nombre de clients dans l'itinéraire
        int nbClients = i1.getListeClientsÀLivrer().size();
        Random random = new Random();
        // génération d'un premier index aléatoire (de 0 à nbClients-1)
        int premierIndexAléatoire = random.nextInt(nbClients);
        // récupération du client positionné au premierIndexAléatoire
        Client clientAReplacer = i1.getListeClientsÀLivrer().get(premierIndexAléatoire);
        // on retire le client pioché aléatoirement de l'itinéraire
        i1.retirerClient(clientAReplacer);
        // génération d'un deuxième index aléatoire (de 0 à nbClients-1)
        int deuxièmeIndexAléatoire = random.nextInt(nbClients);
        // tant que les deux index aléatoires sont identiques...
        while (premierIndexAléatoire == deuxièmeIndexAléatoire) {
            // on regénère le deuxième index aléatoire
            deuxièmeIndexAléatoire = random.nextInt((nbClients));
        }
        // on ajoute le client pioché aléatoirement à une position aléatoire et les éléments après le deuxièmeIndexAléatoire se décalent
        i1.getListeClientsÀLivrer().add(deuxièmeIndexAléatoire, clientAReplacer);

    }

    @Override
    public void inversion(Itinéraire i1)
    {
        // nombre de clients dans l'itinéraire
        int nbClients = i1.getListeClientsÀLivrer().size();
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
        LinkedList<Client> listeDeClientsÀInverser;
        // si le premierIndexAléatoire est inférieur au deuxièmeIndexAléatoire
        if (premierIndexAléatoire < deuxièmeIndexAléatoire) {
            // on crée une nouvelle liste chaînée contenant la liste de clients à inverser (entre le premierIndexAléatoire et le deuxièmeIndexAléatoire)
            listeDeClientsÀInverser = new LinkedList<>(i1.getListeClientsÀLivrer().subList(premierIndexAléatoire, deuxièmeIndexAléatoire + 1));
            // on enlève la liste de clients à enlever de la liste des clients de l'itinéraire
            i1.getListeClientsÀLivrer().removeAll(listeDeClientsÀInverser);
        // sinon...
        } else {
            // on crée une nouvelle liste chaînée contenant la liste de clients à inverser (entre le deuxièmeIndexAléatoire et le premierIndexAléatoire)
            listeDeClientsÀInverser = new LinkedList<>(i1.getListeClientsÀLivrer().subList(deuxièmeIndexAléatoire, premierIndexAléatoire + 1));
            // on enlève la liste de clients à enlever de la liste des clients de l'itinéraire
            i1.getListeClientsÀLivrer().removeAll(listeDeClientsÀInverser);
        }
        // on inverse tous les éléments contenus dans la liste de clients à inverser
        Collections.reverse(listeDeClientsÀInverser);

        // si le premierIndexAléatoire est inférieur au deuxièmeIndexAléatoire
        if (premierIndexAléatoire < deuxièmeIndexAléatoire) {
            // on ajoute la liste inversée à la liste de client de l'itinéraire au premierIndexAléatoire
            i1.getListeClientsÀLivrer().addAll(premierIndexAléatoire, listeDeClientsÀInverser);
        // sinon...
        } else {
            // on ajoute la liste inversée à la liste de client de l'itinéraire au deuxièmeIndexAléatoire
            i1.getListeClientsÀLivrer().addAll(deuxièmeIndexAléatoire, listeDeClientsÀInverser);
        }
    }

    @Override
    public Itinéraire transformation2opt(Itinéraire i1) throws ItinéraireTooSmallException, DeuxOptAlgorithmException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException
    {
        // s'il y a 3 clients ou moins dans la liste, ce ne sera pas possible
        if(i1.getListeClientsÀLivrer().size() < 4)
        {
            throw new ItinéraireTooSmallException(i1);
        }
//
//        // On va recréer un itinéraireComplet qui correspond à une liste chaînée allant de l'entrepôt, aux
//        // clients à livrer puis de nouveau à l'entrepôt (et donc à l'itinéraire réel)
//        // Exemple : si on a A = entrepôt, B, C, D, E, F en clients, on aura la liste chaînée suivante :
//        // A -> B -> C -> D -> E -> F -> A.
//        LinkedList<Sommet> itinéraireComplet = new LinkedList<Sommet>();
//        itinéraireComplet.add(i1.getEntrepôt());
//        itinéraireComplet.addAll(i1.getListeClientsÀLivrer());
//        itinéraireComplet.add(i1.getEntrepôt());
//        LinkedList<Sommet> itinéraireCompletSansSection = new LinkedList<Sommet>(itinéraireComplet);
//        // https://github.com/jackspyder/2-opt/blob/master/src/sample/TwoOpt.java

        LinkedList<Client> nouvelleListeClients = new LinkedList<>();
        Sommet entrepôt = i1.getEntrepôt();
        //Itinéraire nouvelItinéraire = new Itinéraire(nouvelleListeClients , (Entrepôt) entrepôt);


        int nbClients = i1.getListeClientsÀLivrer().size();
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
                nouvelleListeClients.add(i1.getListeClientsÀLivrer().get(i));
            }

            int décalage = 0;
            for (int c = premierIndexAléatoire; c <= deuxièmeIndexAléatoire; c++) {
                nouvelleListeClients.add(i1.getListeClientsÀLivrer().get(deuxièmeIndexAléatoire - décalage));
                décalage++;
            }

            for (int j = deuxièmeIndexAléatoire + 1; j < nbClients; j++) {
                nouvelleListeClients.add(i1.getListeClientsÀLivrer().get(j));
                //nouvelItinéraire.ajouterClient(i1.getListeClientsÀLivrer().get(k));
            }
        }
        else
        {
            for (int i = 0; i <= deuxièmeIndexAléatoire - 1; i++) {
                nouvelleListeClients.add(i1.getListeClientsÀLivrer().get(i));
            }

            int décalage = 0;
            for (int c = deuxièmeIndexAléatoire; c <= premierIndexAléatoire; c++) {
                nouvelleListeClients.add(i1.getListeClientsÀLivrer().get(premierIndexAléatoire - décalage));
                décalage++;
            }

            for (int j = premierIndexAléatoire + 1; j < nbClients; j++) {
                nouvelleListeClients.add(i1.getListeClientsÀLivrer().get(j));
            }
        }



        return new Itinéraire(nouvelleListeClients , (Entrepôt) entrepôt);
    }



    /**
     * Méthode privée permettant d'aider dans le traitement de la transformation 2-opt.
     * Récupère l'index de deux clients dont le premier est tiré aléatoirement.
     * @return deux index de clients, ces index formant une arête récupérée aléatoirement.
     */
    private Pair<Integer, Integer> getDeuxClientsPourTransformation2opt(LinkedList<Sommet> itinéraireComplet)
    {
        Pair<Integer, Integer> indexArêteAléatoire;
        Random r = new Random();
        // La limite de la génération de nos nombre aléatoires est entre 0 et la taille de la liste - 1.
        // nextInt(int bound) prenant les éléments de 0 (inclusif) à bound (exclusif)
        int bound = itinéraireComplet.size();

        // on prend un index aléatoire
        int indexAléatoire = r.nextInt(bound);
        // On prend l'élément situé avant l'index aléatoire
        int indexAléatoireMoinsUn;
        // avant
        // si jamais l'index tiré au hasard s'avère être le premier élément de la liste
        if(indexAléatoire == 0)
        {
            // alors l'élément situé avant est en fait le dernier élément de la liste.
            indexAléatoireMoinsUn =  itinéraireComplet.size() - 1;
        }
        else
        {
            // sinon, c'est l'élément situé à index - 1.
            indexAléatoireMoinsUn = indexAléatoire - 1;
        }
        indexArêteAléatoire = new Pair<Integer, Integer>(indexAléatoireMoinsUn, indexAléatoire);

        return indexArêteAléatoire;
    }

    /**
     * Vérifie si deux paires ont un élément Integer commun.
     * @param premièrePaire la première paire.
     * @param secondePaire la seconde paire avec laquelle réaliser la vérification.
     * @return True (Vrai) si deux paires ont un élément Integer commun.
     */
    private boolean verifieSiDeuxPairesOntUnÉlémentCommun(Pair<Integer, Integer> premièrePaire, Pair<Integer, Integer> secondePaire)
    {
        return
                // si la key de la première paire est égale à la key ou bien la value de la seconde paire
                premièrePaire.getKey().equals(secondePaire.getKey())
                || premièrePaire.getKey().equals(secondePaire.getValue())
                // ou bien si la value de la première paire est égale à la value ou bien la key de la seconde paire.
                || premièrePaire.getValue().equals(secondePaire.getValue())
                || premièrePaire.getValue().equals(secondePaire.getKey());
    }
}
