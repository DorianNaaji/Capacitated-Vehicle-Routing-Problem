import inout.Loader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args)
    {
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
