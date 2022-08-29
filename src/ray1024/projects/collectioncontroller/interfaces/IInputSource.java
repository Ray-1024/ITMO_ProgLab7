package ray1024.projects.collectioncontroller.interfaces;

import java.io.IOException;

public interface IInputSource {
    String nextLine() throws IOException;
    boolean hasNextLine() throws IOException;
}
