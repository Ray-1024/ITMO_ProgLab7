package ray1024.projects.collectioncontroller.general.readers;

import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;

public class DevNullReader implements IInputSource {
    @Override
    public String nextLine() {
        return "";
    }

    @Override
    public boolean hasNextLine() {
        return false;
    }

    @Override
    public boolean isEOF() {
        return false;
    }
}
