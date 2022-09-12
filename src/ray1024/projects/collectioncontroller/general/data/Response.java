package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communicationtypes.ResponseType;
import ray1024.projects.collectioncontroller.general.interfaces.IResponse;

import java.io.Serializable;

public class Response implements Serializable, IResponse {
    private ResponseType responseType;
    private String answer;

    public Response() {
    }


    @Override
    public ResponseType getResponseType() {
        return responseType;
    }

    @Override
    public Response setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public Response setAnswer(String answer) {
        this.answer = answer;
        return this;
    }
}
