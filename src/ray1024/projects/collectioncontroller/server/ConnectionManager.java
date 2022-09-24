package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IRequest;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ConnectionManager implements Tickable {
    private LinkedList<IConnector> connections;
    private Server server;

    public ConnectionManager(Server currentServer) {
        this.connections = new LinkedList<>();
        server = currentServer;
    }

    public void addConnector(IConnector connector) {
        connections.add(connector);
    }

    @Override
    public void tick() {
        connections.parallelStream().forEach((connector -> {
            IRequest request = connector.receiveRequest();
            if (request != null) {

            }
        }));
    }
}
