package ray1024.projects.collectioncontroller.general.writers;

import ray1024.projects.collectioncontroller.general.interfaces.IOutputSource;

public final class ConsoleSourceWriter implements IOutputSource {
    @Override
    public void println(String line) {
        System.out.println(line);
    }

    @Override
    public void print(String line) {
        System.out.print(line);
    }
}
