package gui;

import gui.generic.Flèche;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Client;
import model.Entrepôt;
import model.Itinéraire;
import model.Solution;
import model.graph.Sommet;

/**
 * Classe représentant le contrôleur d'une fenêtre de type CVRPWindow, permettant d'interagir avec les différents
 * composants de l'interface.
 */
public class CVRPWindowController
{
    // Rappel : La fenêtre mesure 560 pixels de large pour 780 pixels de haut par défaut (défini dans CVRPWindow.java)

    /**
     * Fonction appelée à l'initialisation d'une fenêtre de type CVRPWindow et de son contrôleur CVRPWindowController associé.
     */
    @FXML
    public void initialize()
    {
        System.out.println("CVRPWindowController started");
        this.couleursDisponibles = this.get10CouleursArray();
        this.hauteurFenêtre = (int)this.mainPane.getPrefHeight();
        this.largeurFenêtre = (int)this.mainPane.getPrefWidth();
    }

    /**
     * La largeur de la fenêtre principale.
     */
    private int largeurFenêtre;

    /**
     * La hauteur de la fenêtre principale.
     */
    private int hauteurFenêtre;

    /**
     * La fenêtre principale.
     */
    @FXML
    private Pane mainPane;

    private Color[] couleursDisponibles;

    /**
     * Construit et retourne un tableau de 10 couleurs spécifiques.
     * @return un tableau contenant 10 couleurs spécifiques.
     */
    private Color[] get10CouleursArray()
    {
        Color[] couleurs = new Color[10];
        couleurs[0] = Color.CORNFLOWERBLUE;
        couleurs[1] = Color.BLUEVIOLET;
        couleurs[2] = Color.BROWN;
        couleurs[3] = Color.CORAL;
        couleurs[4] = Color.CYAN;
        couleurs[5] = Color.DARKSEAGREEN;
        couleurs[6] = Color.DARKGREEN;
        couleurs[7] = Color.DARKMAGENTA;
        couleurs[8] = Color.DEEPPINK;
        couleurs[9] = Color.GOLD;
        //Color.DARKBLUE;
        return couleurs;
    }

    /**
     * Dessine un client avec une couleur de remplissage précisée. Les clients sont représentés par des cercles de rayon
     * 5 pixels.
     * @param client le client à dessiner.
     * @param remplissage la couleur de remplsisage.
     */
    private void drawClient(Sommet client, Paint remplissage)
    {
        Pair<Integer, Integer> xy = this.normaliserXY(client.getPositionX(), client.getPositionY());

        // rayon du cercle en pixels.
        int rayon = 7;
        // Nous avons donc les coordonnées du centre de notre cercle (xNormé, yNormé)
        Circle c = new Circle(xy.getKey(), xy.getValue(), rayon, remplissage);
        c.setStrokeWidth(1);
        c.setStroke(Color.BLACK);
        this.mainPane.getChildren().add(c);
    }


    /**
     * Norme deux coordonnées x et y aux dimensions de la fenêtre
     * @param x position x à normer
     * @param y position y à normer
     * @return x et y normés.
     */
    private Pair<Integer, Integer> normaliserXY(int x, int y)
    {
        // x = axe horizontal -> il faut normer par rapport à la largeur, donc par rapport à 560
        // -> produit en croix. (xRéel/100 = xNormé/560 donc xNormé = (xRéel*780)/560
        // xRéel/100 car les valeurs maximales de x et y dans les données sont 100.
        int xNormé = (x*this.largeurFenêtre)/100;
        // pareil pour y
        int yNormé = (y*this.hauteurFenêtre)/100;
        return new Pair<Integer, Integer>(xNormé, yNormé);
    }

    /**
     * Dessine un entrepôt. Les entrepôts sont représentés par des rectangles rouges.
     * @param e l'entrepôt à dessiner
     */
    private void drawEntrepôt(Sommet e)
    {
        Pair<Integer, Integer> xy = this.normaliserXY(e.getPositionX(), e.getPositionY());
        Rectangle rect = new Rectangle(xy.getKey() - 5, xy.getValue() -5, 20, 20);
        rect.setFill(Color.RED);
        rect.setStrokeWidth(2);
        rect.setStroke(Color.BLACK);
        this.mainPane.getChildren().add(rect);
    }

    /**
     * Dessine une flèche d'un sommet de départ à un sommet d'arrivée.
     * @param départ le sommet de départ de la flèche.
     * @param arrivée le sommet d'arrivée de la flèche.
     * @param c la couleur de la flèche.
     */
    private void drawFlèche(Sommet départ, Sommet arrivée, Color c)
    {
        Pair<Integer, Integer> xyDépart = this.normaliserXY(départ.getPositionX(), départ.getPositionY());
        Pair<Integer, Integer> xyArrivée = this.normaliserXY(arrivée.getPositionX(), arrivée.getPositionY());
        // on décale le départ et l'arrivée de 4 pixels pour une flèche plus smooth et qui ne déborde pas sur les points.
        Flèche flèche = new Flèche(xyDépart.getKey() + 4, xyDépart.getValue(), xyArrivée.getKey() - 4, xyArrivée.getValue() - 4, c);
        this.mainPane.getChildren().add(flèche);
    }

    /**
     * Dessine une flèche d'un sommet de départ à un sommet d'arrivée.
     * @param départ le sommet de départ de la flèche.
     * @param arrivée le sommet d'arrivée de la flèche.
     */
    private void drawFlèche(Sommet départ, Sommet arrivée)
    {
        Pair<Integer, Integer> xyDépart = this.normaliserXY(départ.getPositionX(), départ.getPositionY());
        Pair<Integer, Integer> xyArrivée = this.normaliserXY(arrivée.getPositionX(), arrivée.getPositionY());
        Flèche flèche = new Flèche(xyDépart.getKey(), xyDépart.getValue(), xyArrivée.getKey(), xyArrivée.getValue());
        this.mainPane.getChildren().add(flèche);
    }

    /**
     * Affiche le coût entre les deux sommets, à mi-chemin entre le départ et l'arrivée.
     * @param départ le sommet de départ.
     * @param arrivée le sommet d'arrivée.
     */
    private void drawCoût(Sommet départ, Sommet arrivée)
    {
        Pair<Integer, Integer> xyDépart = this.normaliserXY(départ.getPositionX(), départ.getPositionY());
        Pair<Integer, Integer> xyArrivée = this.normaliserXY(arrivée.getPositionX(), arrivée.getPositionY());

        int xMilieu = (xyDépart.getKey() + xyArrivée.getKey())/2;
        int yMilieu = (xyDépart.getValue() + xyArrivée.getValue())/2;

        // on crée un nouveau test.
        Text t = new Text(xMilieu, yMilieu, String.format("%.2f",départ.CalculCoût(arrivée)));
        t.setFill(Color.RED);
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        this.mainPane.getChildren().add(t);
    }

    /**
     * Dessine un itinéraire dans une couleur donnée.
     * @param itinéraire l'itinéraire à dessiner
     * @param c la couleur des points de l'itinéraire
     */
    public void drawItinéraire(Itinéraire itinéraire, Color c)
    {
        this.drawEntrepôt(itinéraire.getEntrepôt());
        this.drawClient(itinéraire.getListeClientsÀLivrer().get(0), c);
        // flèche de l'entrepôt au premier client
        this.drawFlèche(itinéraire.getEntrepôt(), itinéraire.getListeClientsÀLivrer().get(0));
        // coût, de l'entrepôt au premier client.
        this.drawCoût(itinéraire.getEntrepôt(), itinéraire.getListeClientsÀLivrer().get(0));

        // flèche de chaque client précédent au client courant
        for(int i = 1; i < itinéraire.getListeClientsÀLivrer().size(); i++)
        {
            this.drawClient(itinéraire.getListeClientsÀLivrer().get(i), c);
            this.drawFlèche(itinéraire.getListeClientsÀLivrer().get(i - 1), itinéraire.getListeClientsÀLivrer().get(i));
            this.drawCoût(itinéraire.getListeClientsÀLivrer().get(i - 1), itinéraire.getListeClientsÀLivrer().get(i));
        }
        // flèche du client final à l'entrepôt
        this.drawFlèche(itinéraire.getListeClientsÀLivrer().get(itinéraire.getListeClientsÀLivrer().size() - 1), itinéraire.getEntrepôt());
        // coût, de l'entrepôt au premier client.
        this.drawCoût(itinéraire.getListeClientsÀLivrer().get(itinéraire.getListeClientsÀLivrer().size() - 1), itinéraire.getEntrepôt());


    }

    /**
     * Dessine une solution, c'est-à-dire l'ensemble des itinéraires d'une Solution.
     * Une couleur unique est attribuée à chaque itinéraire tracé.
     * @param s la solution à dessiner
     */
    public void drawSolution(Solution s)
    {
        // En théorie, on a un véhicule par itinéraire donc la solution utilisera autant de véhicules
        // qu'il y a d'éléments dans l'ensemble des itinéraires.
        int nbCouleursÀUtiliser = s.getItinéraires().size();

        for(int indexItinéraire = 0, indexCouleur = 0 ; indexItinéraire < nbCouleursÀUtiliser; indexItinéraire++, indexCouleur++)
        {
            // on dessine l'itinéraire à l'index i, avec la ième couleur du tableau de couleurs
            this.drawItinéraire((Itinéraire)s.getItinéraires().toArray()[indexItinéraire], this.couleursDisponibles[indexItinéraire]);
            // si on n'a pas assez de couleurs disponibles (+ de 10 itinéraires), on reset les couleurs
            // -> il faudra des itinéraires avec potentiellement deux fois la même couleur.
            if(indexCouleur == nbCouleursÀUtiliser - 1)
            {
                indexCouleur = 0;
            }
        }
    }

}
