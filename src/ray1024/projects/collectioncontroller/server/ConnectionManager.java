package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;

public class ConnectionManager implements Tickable {

    private final ConnectionAcceptor connectionAcceptor;
    private final LinkedList<IConnector> connections;
    private final Server server;

    private final ForkJoinPool forkJoinPool;


    public ConnectionManager(Server currentServer) {
        this.connections = new LinkedList<>();
        server = currentServer;
        connectionAcceptor = new ConnectionAcceptor();
        forkJoinPool = new ForkJoinPool();
    }

    public synchronized void addConnector(IConnector connector) {
        connections.add(connector);
    }

    @Override
    public void tick() {
        try {
            IConnector curr = connectionAcceptor.getNewConnection();
            if (curr != null) connections.add(curr);
            connections.forEach((conn) -> {
                if (conn.isConnected())
                    server.getRequestExecutor().execute(conn.receiveRequest(), conn);
            });
            connections.removeAll(connections.stream().filter((conn) -> !conn.isConnected()).toList());
        } catch (Throwable ignored) {
        }
    }
}
