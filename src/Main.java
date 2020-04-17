
import gui.CVRPWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import inout.Loader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main extends Application
{
    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        CVRPWindow mainGui = new CVRPWindow(primaryStage);
        mainGui.show();
    }

    public static void main(String[] args)
    {

        launch(args);

        System.out.println("Start");

        Loader loader = new Loader();

        Path currentRelativePath = Paths.get("");
        File folder = new File(currentRelativePath.toAbsolutePath().toString() + "\\data");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                loader.loadFromTxt(file.getName());
            }
        }

    }


}
