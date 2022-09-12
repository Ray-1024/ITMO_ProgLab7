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
    public String nextLine() throws IOException {
        return lines.poll();
    }

    @Override
    public boolean hasNextLine() throws IOException {
        return lines.size() > 0;
    }
}
