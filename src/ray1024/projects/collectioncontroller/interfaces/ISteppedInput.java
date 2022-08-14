package ray1024.projects.collectioncontroller.interfaces;

public interface ISteppedInput {
    void inputLine(String line) throws IllegalStateException;

    boolean isObjectReady();

    String getStepDescription();

    void reset();


}
