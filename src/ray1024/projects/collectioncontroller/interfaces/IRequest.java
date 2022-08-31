package ray1024.projects.collectioncontroller.interfaces;

import ray1024.projects.collectioncontroller.commands.BaseCommand;
import ray1024.projects.collectioncontroller.enums.RequestType;

public interface IRequest {
    public RequestType getRequestType();

    public IRequest setRequestType(RequestType requestType);

    public BaseCommand getCommand();

    public IRequest setCommand(BaseCommand command);

    public IUser getUser();

    public IRequest setUser(IUser user);
}
