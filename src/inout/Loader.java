package inout;

import customexceptions.ClientLoadException;
import customexceptions.FileLoadException;
import model.Client;
import model.Entrepôt;
import model.Fichier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Loader {

    private Path workPath;

    public Loader(Path _workPath)
    {
        this.workPath = _workPath;
    }

    public Loader()
    {
        this.workPath = Paths.get("").toAbsolutePath();
    }

    public ArrayList<Fichier> chargerTousLesFichiers() throws FileLoadException
    {
        ArrayList<Fichier> fichiers = new ArrayList<Fichier>();
        File folder = new File(this.workPath.toString() + "\\data");
        File[] listOfFiles = folder.listFiles();

        try
        {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    ArrayList<Client> clientsFichierCourant = this.loadClientsFromOneTxtFile(file.getName());
                    // Première ligne : coordonnées de l'entrepôt, le seul qui a 0 caisses à livrer et qui a pour numéro 0.
                    // On le récupère.
                    Entrepôt e = new Entrepôt(clientsFichierCourant.get(0).getPositionX(), clientsFichierCourant.get(0).getPositionY());
                    clientsFichierCourant.remove(0);
                    Fichier f  = new Fichier(clientsFichierCourant, file.getName(), e);
                    fichiers.add(f);
                }
            }
            return fichiers;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new FileLoadException("Impossible de charger les fichiers");
        }
    }

    /**
     * Importe les clients à partir d'un fichier donné
     */
    public ArrayList<Client> loadClientsFromOneTxtFile(String filename) throws FileLoadException
    {
        // on crée une liste de clients qui va contenir les clients lu dans les fichiers texte
        ArrayList<Client> clients = new ArrayList<Client>();
        try {
            // on récupère le chemin courant d'exécution du programme
            Path currentRelativePath = Paths.get("");
            // et on va lire les informations des fichiers textes contenues dans le dossier data, situé
            // au même niveau (arborescence de fichier) que le programme
            FileReader fileReader = new FileReader(this.workPath + "\\data\\" + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            String informations = "";
            // tant qu'il y a des données à lire, on initialise un client
            while ((informations = bufferedReader.readLine()) != null) {
                try
                {
                    // les informations contenues dans les fichiers textes sont en fait au format .csv
                    String[] infosClientCourant = informations.split(";");
                    // la première colonne représente le numéro du client
                    int numeroClient = Integer.parseInt(infosClientCourant[0]);

                    // la seconde, la position X de la livraison
                    int positionX = Integer.parseInt(infosClientCourant[1]);

                    // la troisième, la position Y
                    int positionY = Integer.parseInt(infosClientCourant[2]);

                    // puis finalement, la quantité à livrer
                    int quantite = Integer.parseInt(infosClientCourant[3]);

                    Client client = new Client(numeroClient, positionX, positionY, quantite);
                    clients.add(client);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    throw new ClientLoadException("Erreur de parsing et de loading d'un Client");
                }
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new FileLoadException("Erreur lors de l'ouverture du fichier");
        }
        return clients;
    }
}
