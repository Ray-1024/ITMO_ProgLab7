package ray1024.projects.collectioncontroller.general.writers;

import ray1024.projects.collectioncontroller.general.interfaces.IOutputSource;

public class DevNullWriter implements IOutputSource {
    @Override
    public void println(String line) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void print(String line) {


    }
}
