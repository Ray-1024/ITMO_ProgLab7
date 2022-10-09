package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IRequest;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;

public class ConnectionManager implements Tickable {

    private final ConnectionAcceptor connectionAcceptor;
    private final LinkedList<IConnector> connections;
    private final LinkedList<IConnector> disconnected;
    private final Server server;

    private final ForkJoinPool forkJoinPool;


    public ConnectionManager(Server currentServer) {
        this.connections = new LinkedList<>();
        this.disconnected = new LinkedList<>();
        server = currentServer;
        connectionAcceptor = new ConnectionAcceptor();
        forkJoinPool = new ForkJoinPool();
    }

    private void addConnector(IConnector connector) {
        connections.add(connector);
        /*forkJoinPool.execute(() -> {
            while (connector.isConnected()) {
                IRequest request = connector.receiveRequest();
                if (request != null) {
                    System.out.println("--- START EXECUTION REQUEST ---");
                    server.getRequestExecutor().executeRequest(request, connector);
                } else {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        //throw new RuntimeException(e);
                    }
                }
            }
            synchronized (disconnected) {
                disconnected.add(connector);
            }
        });*/
    }

    @Override
    public void tick() {
        IConnector curr = connectionAcceptor.getNewConnection();
        if (curr != null) {
            addConnector(curr);
            System.out.println("--- NEW CONNECTION ---");
        }
        connections.forEach((connector) -> forkJoinPool.execute(() -> {
            if (!connector.isConnected()) {
                synchronized (disconnected) {
                    disconnected.add(connector);
                }
            }
            IRequest request = connector.receiveRequest();
            if (request != null) System.out.println("--- START EXECUTION REQUEST ---");
            server.getRequestExecutor().executeRequest(request, connector);
        }));

        /*for (IConnector connector : connections) {
            if (!connector.isConnected()) {
                synchronized (disconnected) {
                    disconnected.add(connector);
                }
            }
            IRequest request = connector.receiveRequest();
            if (request != null) {
                System.out.println("--- START EXECUTION REQUEST ---");
                server.getRequestExecutor().executeRequest(request, connector);
            }
        }*/
        synchronized (disconnected) {
            connections.removeAll(disconnected);
            disconnected.clear();
        }
    }
}
