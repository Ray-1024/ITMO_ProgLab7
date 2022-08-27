package ray1024.projects.collectioncontroller;

import ray1024.projects.collectioncontroller.tools.Phrases;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.Selector;

public class Server implements Runnable {
    private Selector selector;
    private ServerSocket serverSocket;

    public Server(){
        try {
            selector = Selector.open();
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost",44147));

        } catch (IOException e) {
            throw new RuntimeException(Phrases.getPhrase("SelectorCan'tBeCreated"));
        }


    }

    @Override
    public void run() {
        while(true){

        }
    }
}
