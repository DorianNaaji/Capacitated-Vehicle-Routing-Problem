package model.graph;

import model.Client;
import model.Fichier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Cette classe représente un graphe non orienté complet, dans l'optique de gérer
 * sommets et arêtes.
 * @deprecated N'est pas utile dans le projet.
 */
public class GrapheNonOrientéComplet
{
    /**
     * Les sommets correspondent vont de v0 à vn avec v0 = l'entrepôt, et de v1 à vn des clients.
     */
    private Set<Sommet> sommets;

    /**
     * Nous sommes dans un graphe non orienté complet, les arêtes représentent donc tous les liens possibles entre
     * les sommets (de v0 aux autres, de v1 aux autres, etc.).
     */
    private Set<Arête> arêtes;

    /**
     * On garde en mémoire pour un GrapheNonOrientéComplet le fichier initial.
     */
    private Fichier fichierInitial;

    public GrapheNonOrientéComplet(Fichier _fichierInitial)
    {
        this.fichierInitial = _fichierInitial;
        this.sommets = new HashSet<Sommet>();
        // V0 est l'entrepôt,
        this.sommets.add(this.fichierInitial.getEntrepôt());
        // et on a de V1 à Vn les clients.
        this.sommets.addAll(this.fichierInitial.getClients());
        // on initialise l'ensemble des arêtes.
        this.arêtes = this.construireEnsembleDesArêtes();
    }

    /**
     * Construit l'ensemble des arêtes à partir des sommets spécifiés dans l'attribut sommets (Graphe complet).
     * @return L'ensemble de toutes les arêtes possibles entre les sommets.
     */
    private Set<Arête> construireEnsembleDesArêtes()
    {
        HashSet<Arête> arêtes = new HashSet<Arête>();
        ArrayList<Sommet> except = new ArrayList<Sommet>();
        Sommet[] sommets = this.sommets.toArray(new Sommet[this.sommets.size()]);
        // on va de i à tailleSommets-1
        for(int i = 0 ; i < this.sommets.size() - 1; i++)
        {
            // on va de i à tailleSommets-1
            for(int j = i + 1; j < this.sommets.size(); j++)
            {
                Sommet sommet1 = sommets[i];
                Sommet sommet2 = sommets[j];
                // on crée l'arête allant du sommet1 au sommet2
                arêtes.add(new Arête(sommets[i], sommets[j]));
            }
        }
        return arêtes;
    }

    /**
     * Récupère l'ensemble des sommets.
     * @return les sommets du graphe.
     */
    public Set<Sommet> getSommets()
    {
        return sommets;
    }

    /**
     * Récupère l'ensemble des arêtes du graphe.
     * @return les arêtes du graphe.
     */
    public Set<Arête> getArêtes()
    {
        return arêtes;
    }

    /**
     * Récupère le fichier initial duquel a été construit le graphe non orienté complet courant.
     * @return le fichier initial.
     */
    public Fichier getFichierInitial()
    {
        return fichierInitial;
    }
}
