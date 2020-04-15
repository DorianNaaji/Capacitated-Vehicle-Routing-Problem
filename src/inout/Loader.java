package inout;

import model.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Loader {

    private LinkedList<Client> clientList = new LinkedList<Client>();

    // Pour l'importation des fichiers de données
    public void loadFromTxt(String filename) {

        String informations = null;

        try {
            Path currentRelativePath = Paths.get("");
            FileReader fileReader = new FileReader(currentRelativePath.toAbsolutePath().toString() + "\\data\\" + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            while ((informations = bufferedReader.readLine()) != null) {
                Client client = new Client(informations);
                this.clientList.add(client);
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture du fichier : " + e );
        }

    }




}
