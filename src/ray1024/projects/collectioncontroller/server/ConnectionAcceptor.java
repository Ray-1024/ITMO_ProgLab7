package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.tools.Phrases;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionAcceptor {
    private final ServerSocket serverSocket;

    public ConnectionAcceptor() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 44147));
            serverSocket.setSoTimeout(1);
        } catch (IOException e) {
            throw new RuntimeException(Phrases.getPhrase("ServerCan'tStart"));
        }
    }

    public Socket getNewConnection() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            return null;
        }
    }
}
