package ray1024.projects.collectioncontroller.data;

import ray1024.projects.collectioncontroller.enums.ResponseType;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType responseType;
    private String answer;

    public Response() {
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public Response setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public Response setAnswer(String answer) {
        this.answer = answer;
        return this;
    }
}
