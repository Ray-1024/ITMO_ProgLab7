package ray1024.projects.collectioncontroller.general.interfaces;

public interface IConnector {
    IConnector sendRequest(IRequest request);

    IRequest receiveRequest();

    IResponse receiveResponse();

    IConnector sendResponse(IResponse response);
}
