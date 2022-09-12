package ray1024.projects.collectioncontroller.general.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Класс управляющий коллекцией учебных групп
 */
public class MyCollection<T> implements Serializable {
    private Vector<T> vec = new Vector<T>();
    private CollectionInfo collectionInfo = new CollectionInfo();

    public MyCollection() {
        collectionInfo.initializationDateTime = LocalDateTime.now();
        collectionInfo.collectionElementType = StudyGroup.class;
    }

    public Vector<T> getVec() {
        return vec;
    }

    public void setVec(Vector<T> vec) {
        this.vec = vec;
    }

    public void setCollectionInfo(CollectionInfo collectionInfo) {
        this.collectionInfo = collectionInfo;
    }

    public void clear() {
        vec.clear();
    }

    public CollectionInfo getCollectionInfo() {
        collectionInfo.elementsCount = vec.size();
        return collectionInfo;
    }

    public Stream<T> stream() {
        return vec.stream();
    }

    public String get(int index) {
        if (index < 0 || index >= vec.size()) return null;
        return (String) vec.get(index);
    }

    public int size() {
        return vec.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < vec.size(); ++i) {
            stringBuilder.append("\n    ").append(i + 1).append(". ").append(vec.get(i).toString());
        }
        return stringBuilder.toString();
    }
}
