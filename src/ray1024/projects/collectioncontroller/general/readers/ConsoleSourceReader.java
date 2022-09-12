package ray1024.projects.collectioncontroller.general.readers;

import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleSourceReader implements IInputSource {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() throws IOException {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNextLine() throws IOException {
        return scanner.hasNextLine();
    }

    @Override
    public boolean isEOF() {
        try {
            scanner.hasNextLine();
            return false;
        } catch (IllegalStateException illegalStateException) {
            return true;
        }
    }
}
