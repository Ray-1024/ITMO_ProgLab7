package ray1024.projects.collectioncontroller.general.interfaces;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.communication.RequestType;

public interface IRequest {
    public RequestType getRequestType();

    public IRequest setRequestType(RequestType requestType);

    public BaseCommand getCommand();

    public IRequest setCommand(BaseCommand command);

    public IUser getUser();

    public IRequest setUser(IUser user);
}
