package gui;

import gui.generic.Window;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe représentant une fenêtre CVRPWindow, qui est l'interface graphique principale du projet.
 */
public class CVRPWindow extends Window
{
    /**
     * Initialise une fenêtre de type CVRPWindow.
     * @param parent fenêtre parent.
     * @throws IOException
     */
    public CVRPWindow(Stage parent) throws IOException
    {
        super(parent, "Capacitated Vehicle Routing Problem", "CVRPWindow.fxml", 560, 780, Modality.NONE);
    }

    /**
     * Récupère le contrôlleur d'une fenêtre de type CVRPWindow.
     * @return un CVRPWindowController initialisé spécialement pour une CVRPWindow.
     */
    public CVRPWindowController getController()
    {
        return (CVRPWindowController)this.controller;
    }
}
