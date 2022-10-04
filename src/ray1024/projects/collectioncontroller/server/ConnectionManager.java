package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.util.LinkedList;

public class ConnectionManager implements Tickable {

    private final ConnectionAcceptor connectionAcceptor;
    private final LinkedList<IConnector> connections;
    private final Server server;


    public ConnectionManager(Server currentServer) {
        this.connections = new LinkedList<>();
        server = currentServer;
        connectionAcceptor = new ConnectionAcceptor();
    }

    public synchronized void addConnector(IConnector connector) {
        connections.add(connector);
    }

    @Override
    public void tick() {

    }
}
