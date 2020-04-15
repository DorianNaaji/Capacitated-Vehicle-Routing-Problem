package gui.generic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe Window abstraite permettant de simplifier l'initialisation de fenêtre aux classes enfants et d'éviter
 * la redondance de code.
 */
public abstract class Window extends Stage
{
    /**
     * Le contrôleur de la fenêtre courante
     */
    protected Object controller;

    /**
     * Constructeur d'une Window.
     * @param parent fenêtre parent (Stage).
     * @param title titre de la fenêtre.
     * @param fxmlResource la ressource FXML associée à la fenêtre.
     * @param width la largeur de la fenêtre.
     * @param height la hauteur de la fenêtre.
     * @param modality la modalité de la fenêtre (bloquante ou non).
     * @throws IOException en cas d'erreur d'initialisation.
     */
    public Window(Stage parent, String title, String fxmlResource, int width, int height, Modality modality) throws IOException
    {
        super();
        this.setTitle(title);
        this.initOwner(parent);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlResource));
        Parent root = loader.load();
        this.controller = loader.getController();
        this.setScene(new Scene(root, width, height));
        this.setResizable(false);
        this.initModality(modality);
        this.getIcons().add(new Image("file:resources/icon.png"));
    }
}
