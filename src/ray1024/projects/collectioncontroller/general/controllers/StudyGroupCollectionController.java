package ray1024.projects.collectioncontroller.general.controllers;

import org.xml.sax.InputSource;
import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StudyGroupCollectionController {

    private String collectionFilename = "Collection.xml";
    private MyCollection<StudyGroup> managedCollection;

    public MyCollection<StudyGroup> getManagedCollection() {
        return managedCollection;
    }

    public StudyGroupCollectionController(String collectionFilename) {
        this.collectionFilename = collectionFilename;

    }

    public void loadCollectionFromFile() throws Exception {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get(collectionFilename)));
            XMLDecoder xmlDecoder = new XMLDecoder(new InputSource(inputStreamReader));
            managedCollection = (MyCollection<StudyGroup>) xmlDecoder.readObject();
            xmlDecoder.close();
            inputStreamReader.close();
            for (StudyGroup studyGroup : managedCollection.getVec())
                if (StudyGroup.getNextID() <= studyGroup.getId()) StudyGroup.setNextID(studyGroup.getId()+1);
        } catch (Throwable ex) {
            managedCollection = new MyCollection<>();
            throw new Exception(Phrases.getPhrase("CantLoadCollectionFromFile"));
        }
    }

    public void saveCollection() throws RuntimeException {
        if (collectionFilename == null) return;
        try {
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(collectionFilename)));
            xmlEncoder.writeObject(managedCollection);
            xmlEncoder.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(Phrases.getPhrase("CantSaveCollectionToFile"));
        }
    }

}
