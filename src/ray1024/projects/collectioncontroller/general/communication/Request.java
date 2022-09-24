package ray1024.projects.collectioncontroller.general.communication;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.IUserInfo;

import java.io.Serializable;

public class Request implements Serializable, IRequest {
    private RequestType requestType;
    private BaseCommand command;
    private IUserInfo user;

    public Request() {
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public IRequest setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    @Override
    public BaseCommand getCommand() {
        return command;
    }

    @Override
    public IRequest setCommand(BaseCommand command) {
        this.command = command;
        return this;
    }

    @Override
    public IUserInfo getUserInfo() {
        return user;
    }

    @Override
    public IRequest setUser(IUserInfo user) {
        this.user = user;
        return this;
    }
}
