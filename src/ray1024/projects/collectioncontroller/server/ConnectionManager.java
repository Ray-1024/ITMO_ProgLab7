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
        forkJoinPool.execute(() -> {
                    try {
                        while (connector.isConnected()) {
                            server.getRequestExecutor().executeRequest(connector.receiveRequest(), connector);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    @Override
    public void tick() {
        try {
            IConnector curr = connectionAcceptor.getNewConnection();
            if (curr != null) {
                addConnector(curr);
                System.out.println("--- NEW CONNECTION ---");
            }
            connections.removeAll(connections.stream().filter((conn) -> !conn.isConnected()).toList());
        } catch (Throwable ex) {
            throw ex;
        }
    }
}
