package ray1024.projects.collectioncontroller.general.interfaces;

import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;

public interface IResponse {
    ResponseType getResponseType();

    IResponse setResponseType(ResponseType responseType);

    String getAnswer();

    IResponse setAnswer(String answer);

    IResponse setCollection(MyCollection<StudyGroup> collection);

    MyCollection<StudyGroup> getCollection();
}
