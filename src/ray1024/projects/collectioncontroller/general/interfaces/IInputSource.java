package ray1024.projects.collectioncontroller.general.interfaces;

import java.io.IOException;

public interface IInputSource {
    String nextLine() throws IOException;
    boolean hasNextLine() throws IOException;
}
