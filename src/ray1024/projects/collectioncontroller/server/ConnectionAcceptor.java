package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

public class ConnectionAcceptor {
    private final ServerSocketChannel serverSocket;

    public ConnectionAcceptor() {
        try {
            serverSocket = ServerSocketChannel.open().bind(new InetSocketAddress("localhost", 44147));
            serverSocket.configureBlocking(false);
            serverSocket.socket().setSoTimeout(1);
        } catch (IOException e) {
            throw new RuntimeException(Phrases.getPhrase("ServerCan'tStart"));
        }
    }

    public Socket getNewConnection() {
        try {
            return serverSocket.accept().socket();
        } catch (Throwable e) {
            return null;
        }
    }
}
