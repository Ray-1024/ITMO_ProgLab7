package ray1024.projects.collectioncontroller.data;

import ray1024.projects.collectioncontroller.commands.BaseCommand;
import ray1024.projects.collectioncontroller.tools.RequestType;

import java.io.Serializable;

public class Request implements Serializable {
    private RequestType requestType;
    private BaseCommand command;
    private User user;

    public Request() {
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Request setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    public BaseCommand getCommand() {
        return command;
    }

    public Request setCommand(BaseCommand command) {
        this.command = command;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Request setUser(User user) {
        this.user = user;
        return this;
    }
}
