package algorithms;

import customexceptions.*;
import model.Itinéraire;
import model.Solution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe contenant des algorithmes liés à la recherche tabou
 */
public class Tabou
{
    /**
     * Effectue un tabou search sur une solution initiale donnée.
     * @param solutionInitiale la solution initiale, de départ, qui sera optimisée puis retournée.
     * @param tailleMaximaleListeTabou la taille maximale que prendra la liste de tabou.
     * @param nbIterMax le nombre d'itérations maximal de l'algorithme.
     * @param nbSolutionsVoisinesChaqueIter le nombre de solutions voisines devant être trouvées à chaque itération.
     * @param transfo le type de transformation à appliquer lors de la recherche de nouveaux voisins.
     * @param typeDeRechercheVoisinage le type de recherche de voisinage à appliquer.
     * @return la solution initiale optimisée.
     */
    public static Solution tabouSearch(Solution solutionInitiale, int tailleMaximaleListeTabou, int nbIterMax, int nbSolutionsVoisinesChaqueIter, Transformation transfo, TypeDeRechercheVoisinage typeDeRechercheVoisinage) throws ItinéraireTooSmallException, VehiculeCapacityOutOfBoundsException, UnhandledTransformationException, ListOfClientsIsEmptyException, UnhandledTypeDeRechercheVoisinageException
    {
        Solution solutionMin = solutionInitiale;
        double fitnessMinimale = solutionMin.getOptimisationGlobale();
        ArrayList<Solution> listeTabou = new ArrayList<Solution>();
        //System.out.println("Fitness minimales (Tabou) : ");
        for(int i = 0; i < nbIterMax; i++)
        {
            // on récupère le voisinage
            ArrayList<Solution> voisinage = Tabou.getSolutionsVoisines(solutionMin, transfo, typeDeRechercheVoisinage, nbSolutionsVoisinesChaqueIter);
            // duquel on enlève l'ensemble des transformations (solutions) interdites
            voisinage.removeAll(listeTabou);

            // et on récupère la meilleure solution de ce voisinage
            Solution meilleureSolutionVoisine = new Solution();
            meilleureSolutionVoisine.setOptimisationGlobale(Double.MAX_VALUE);
            for(Solution s:voisinage)
            {
                if(s.getOptimisationGlobale() < meilleureSolutionVoisine.getOptimisationGlobale())
                {
                    meilleureSolutionVoisine = s;
                }
            }

            double fitnessCourante = meilleureSolutionVoisine.getOptimisationGlobale();
            // la différence de fitness
            double deltaF =  fitnessCourante - fitnessMinimale;
            // si le delta est positif et que la liste de tabou contient encore de la place
            if(deltaF > 0 && listeTabou.size() + voisinage.size() < tailleMaximaleListeTabou)
            {
                listeTabou.addAll(voisinage);
            }
            // si le delta est positif et que la liste de tabou n'a plus de place
            else if(deltaF > 0 && listeTabou.size() + voisinage.size() > tailleMaximaleListeTabou)
            {
                int espaceRequis = (listeTabou.size() + voisinage.size()) - tailleMaximaleListeTabou;
                // on supprime les "espaceRequis" anciens éléments.
                listeTabou = new ArrayList<Solution>(listeTabou.subList(espaceRequis, listeTabou.size() - 1));
                // et on ajoute les nouveaux éléments à la fin.
                listeTabou.addAll(voisinage);
            }

            if(fitnessCourante < fitnessMinimale)
            {
                solutionMin = meilleureSolutionVoisine;
                fitnessMinimale = fitnessCourante;
                //System.out.println("Itération " + i + " : " + fitnessMinimale);
            }
        }
        return solutionMin;
    }

    /**
     * Algorithme permettant de trouver les solutions voisines d'une solution initiale. Pour trouver une solution
     * voisine, on chosisit d'appliquer sur chaque itinéraire de la solution une transformation donnée. On peut imaginer
     * d'autres moyen
     * @param solutionInitiale la solutionInitiale.
     * @param transfo le type de transformation à appliquer.
     * @param typeDeRechercheVoisinage le type de recherche de voisinage à appliquer.
     * @param nbSolutionsVoisines le nombre de solutions voisines à trouver
     * @return les solutions voisines de la solution initiale.
     */
    public static ArrayList<Solution> getSolutionsVoisines(Solution solutionInitiale, Transformation transfo, TypeDeRechercheVoisinage typeDeRechercheVoisinage,int nbSolutionsVoisines) throws NotImplementedException, UnhandledTypeDeRechercheVoisinageException, UnhandledTransformationException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, ItinéraireTooSmallException
    {
        switch(typeDeRechercheVoisinage)
        {
            case BASIQUE:
                return Tabou.getSolutionsVoisinesBasicSearch(solutionInitiale, transfo, nbSolutionsVoisines);
            case COMPLEXE:
                throw new NotImplementedException();
            default:
                throw new UnhandledTypeDeRechercheVoisinageException(typeDeRechercheVoisinage, Tabou.class);
        }
    }

    /**
     * Algorithme permettant de trouver les solutions voisines d'une solution voisine avec un type de recherche basique.
     * Une transformation "transfo" sera appliquée sur l'ensemble des itinéraires de la solutionInitiale afin de trouver
     * ses voisins proches.
     * @param solutionInitiale la solution à partir de laquelle chercher les solutions voisines.
     * @param transfo le type de transformation à appliquer sur la solution initiale.
     * @param nbSolutionsVoisines le nombre de solutions voisines à trouver.
     * @return les solutions voisines proches de la solutionInitiale avec une méthode de recherche de voisinage basique.
     * @throws UnhandledTransformationException Si un type de transformation donné n'est pas géré.
     * @throws VehiculeCapacityOutOfBoundsException Si une transformation a donné lieu à un dépassement de la capacité maximale des véhicules.
     * @throws ListOfClientsIsEmptyException Si la transformation 2-opt a tenté de créer un itinéraire avec une liste de clients vide.
     */
    private static ArrayList<Solution> getSolutionsVoisinesBasicSearch(Solution solutionInitiale, Transformation transfo, int nbSolutionsVoisines) throws UnhandledTransformationException, VehiculeCapacityOutOfBoundsException, ListOfClientsIsEmptyException, ItinéraireTooSmallException
    {
        ArrayList<Solution> solutionsVoisines = new ArrayList<Solution>();
        for(int i = 0; i < nbSolutionsVoisines; i++)
        {
            Solution voisin = new Solution(solutionInitiale);
            for(Itinéraire itinéraire:voisin.getItinéraires())
            {
                switch(transfo)
                {
                    case TransformationÉchange:
                        TransformateurItinéraire.transformationÉchange(itinéraire);
                        break;
                    case InsertionDécalage:
                        TransformateurItinéraire.insertionDécalage(itinéraire);
                        break;
                    case Inversion:
                        TransformateurItinéraire.inversion(itinéraire);
                        break;
                    case Transformation2Opt:
                        // En backup du 2-opt, on utilise une insertion décalage
                        //todo : décider de la meilleure transfo après le 2-opt
                        int  indexOfitinéraireModif = voisin.getItinéraires().indexOf(itinéraire);
                        Itinéraire itinéraireModif = voisin.getItinéraires().get(indexOfitinéraireModif);
                        itinéraireModif = TransformateurItinéraire.transformation2opt(itinéraireModif, Transformation.TransformationÉchange);
                        voisin.getItinéraires().set(indexOfitinéraireModif, itinéraireModif);
                        break;
                    default:
                        throw new UnhandledTransformationException(transfo, Tabou.class);
                }
                voisin.recalculerLongueurGlobale();
            }
            solutionsVoisines.add(voisin);
        }
        return solutionsVoisines;
    }
}
