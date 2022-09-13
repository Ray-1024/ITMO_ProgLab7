package ray1024.projects.collectioncontroller.general.interfaces;

public interface IConnector {
    IConnector sendRequestToServer(IRequest request);

    IRequest receiveRequestFromClient();

    IResponse receiveResponseFromServer();
}
