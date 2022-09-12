package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.general.interfaces.IConnector;
import ray1024.projects.collectioncontroller.general.interfaces.IRequest;
import ray1024.projects.collectioncontroller.general.interfaces.IResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class ClientToServerConnector implements IConnector {
    private Socket socket;
    private SocketChannel socketChannel;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientToServerConnector(InetAddress serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        socketChannel = socket.getChannel();
        socketChannel.configureBlocking(false);
    }

    @Override
    public IConnector sendRequestToServer(IRequest request) {
        return null;
    }

    @Override
    public IConnector sendRequestToClient(IResponse response) {
        return null;
    }

    @Override
    public IRequest receiveRequestFromClient() {
        return null;
    }

    @Override
    public IResponse receiveResponseFromServer() {
        return null;
    }
}
