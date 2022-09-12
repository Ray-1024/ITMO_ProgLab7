package ray1024.projects.collectioncontroller.general.readers;

import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public final class FileSourceReader implements IInputSource {
    private Scanner scanner;

    public FileSourceReader(String filename) throws IOException {
        scanner = new Scanner(Files.newInputStream(Paths.get(filename)));
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}
