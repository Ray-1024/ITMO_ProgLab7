package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.interfaces.Tickable;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionAcceptor implements Tickable {
    private final ServerSocket serverSocket;
    private Queue<Socket>unknownConnections;
    private Queue<User>

    public ConnectionAcceptor() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 44147));
            serverSocket.setSoTimeout(1);
        } catch (IOException e) {
            throw new RuntimeException(Phrases.getPhrase("ServerCan'tStart"));
        }
    }

    @Override
    public void tick() throws IOException {
        if (currConnection == null) currConnection = serverSocket.accept();
    }

    public Socket getNewConnection() {
        return currConnection;
    }
}
