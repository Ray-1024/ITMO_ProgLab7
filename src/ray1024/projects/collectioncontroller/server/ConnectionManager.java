package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;

public class ConnectionManager implements Tickable {
    private LinkedList<IConnector> connections;
    private ForkJoinPool forkJoinPool;

    public ConnectionManager() {
        this.connections = new LinkedList<>();
        forkJoinPool = new ForkJoinPool();
    }


    @Override
    public void tick() {
        connections.removeAll(connections.stream().filter((currConn) -> {
            return !currConn.isConnected();
        }).toList());
    }
}
