package ray1024.projects.collectioncontroller.general.readers;

import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;

import java.io.IOException;
import java.util.LinkedList;

public class ListSourceReader implements IInputSource {
    private LinkedList<String> lines;

    public ListSourceReader(LinkedList<String> lines) {
        this.lines = lines;
    }

    @Override
    public String nextLine() {
        return lines.poll();
    }

    @Override
    public boolean hasNextLine() {
        return lines.size() > 0;
    }

    @Override
    public boolean isEOF() {
        return lines.size() > 0;
    }
}
