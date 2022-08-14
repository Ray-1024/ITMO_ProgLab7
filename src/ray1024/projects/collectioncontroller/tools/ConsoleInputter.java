package ray1024.projects.collectioncontroller.tools;

import ray1024.projects.collectioncontroller.interfaces.IInputter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInputter implements IInputter {
    private Scanner scanner;

    public ConsoleInputter() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public boolean hasGetLine() {
        try {
            return scanner.hasNextLine();
        } catch (IllegalStateException illegalStateException) {
            return false;
        }
    }

    @Override
    public String getLine() throws NoSuchElementException, IllegalStateException {
        return scanner.nextLine();
    }
}
