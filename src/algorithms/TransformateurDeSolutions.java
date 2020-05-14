package algorithms;

import model.Client;
import model.Itinéraire;
import model.Solution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.management.relation.RelationNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Un transformateur de solution, implémentant l'interface ITransformateur,
 * est une classe permettant d'appliquer des transformations à une solution.
 */
public class TransformateurDeSolutions implements ITransformateur
{
    private Solution solution;

    public TransformateurDeSolutions(Solution s)
    {
        this.solution = s;
    }

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
    public void transformation2opt(Itinéraire i1)
    {
        //todo (voir doc de la méthode)
        throw new NotImplementedException();
    }
}
