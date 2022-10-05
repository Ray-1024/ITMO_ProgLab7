package ray1024.projects.collectioncontroller.general.controllers;

import org.xml.sax.InputSource;
import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.server.Server;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

public class StudyGroupCollectionController implements Serializable {

    private String collectionFilename;
    private MyCollection<StudyGroup> managedCollection;
    private Server server;

    public MyCollection<StudyGroup> getManagedCollection() {
        return managedCollection;
    }

    public StudyGroupCollectionController(Server server, String collectionFilename) {
        this.collectionFilename = collectionFilename;
        if (collectionFilename == null)
            managedCollection = new MyCollection<>();
    }


    public void sortManagedCollection() {
        managedCollection.getVec().sort(Comparator.naturalOrder());
    }

    public synchronized void loadCollection() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get(collectionFilename)));
            XMLDecoder xmlDecoder = new XMLDecoder(new InputSource(inputStreamReader));
            managedCollection = (MyCollection<StudyGroup>) xmlDecoder.readObject();
            xmlDecoder.close();
            inputStreamReader.close();
            for (StudyGroup studyGroup : managedCollection.getVec())
                if (StudyGroup.getNextID() <= studyGroup.getId()) StudyGroup.setNextID(studyGroup.getId() + 1);
        } catch (Throwable ex) {
            managedCollection = new MyCollection<>();
            //throw new Exception(Phrases.getPhrase("CantLoadCollectionFromFile"));
        }
    }

    public synchronized void saveCollection() throws RuntimeException {
        if (collectionFilename == null) collectionFilename = "Coll.xml";
        try {
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(collectionFilename)));
            xmlEncoder.writeObject(managedCollection);
            xmlEncoder.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(Phrases.getPhrase("CantSaveCollectionToFile"));
        }
    }

    public synchronized void setManagedCollection(MyCollection<StudyGroup> managedCollection) {
        this.managedCollection = managedCollection;
    }

}
