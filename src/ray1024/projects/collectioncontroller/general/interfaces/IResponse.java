package ray1024.projects.collectioncontroller.general.interfaces;

import ray1024.projects.collectioncontroller.general.communicationtypes.ResponseType;

public interface IResponse {
    ResponseType getResponseType();

    IResponse setResponseType(ResponseType responseType);

    String getAnswer();

    IResponse setAnswer(String answer);
}
