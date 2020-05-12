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
        for(Sommet s1:this.sommets)
        {
            except.add(s1);
            for(Sommet s2:this.sommets.stream().filter(except::contains).collect(Collectors.toSet()))
            {
                Arête ar = new Arête(s1, s2);
                arêtes.add(ar);
            }
        }
        return arêtes;
    }
}
