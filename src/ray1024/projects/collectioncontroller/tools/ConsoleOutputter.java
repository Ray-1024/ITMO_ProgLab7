package ray1024.projects.collectioncontroller.tools;

import ray1024.projects.collectioncontroller.interfaces.IOutputter;

public class ConsoleOutputter implements IOutputter {
    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }
}
