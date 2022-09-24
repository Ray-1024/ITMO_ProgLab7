package ray1024.projects.collectioncontroller.general.communication;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.data.IUserInfo;

public interface IRequest {
    public RequestType getRequestType();

    public IRequest setRequestType(RequestType requestType);

    public BaseCommand getCommand();

    public IRequest setCommand(BaseCommand command);

    public IUserInfo getUserInfo();

    public IRequest setUser(IUserInfo user);
}
