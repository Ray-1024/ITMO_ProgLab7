package ray1024.projects.collectioncontroller.interfaces;

public interface IConnector {
    IConnector sendRequestToServer(IRequest request);

    IConnector sendRequestToClient(IResponse response);

    IRequest receiveRequestFromClient();

    IResponse receiveResponseFromServer();
}
