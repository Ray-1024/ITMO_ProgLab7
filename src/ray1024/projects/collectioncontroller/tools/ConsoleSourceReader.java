package ray1024.projects.collectioncontroller.tools;

import ray1024.projects.collectioncontroller.interfaces.IInputSource;

import java.util.Scanner;

public final class ConsoleSourceReader implements IInputSource {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}
