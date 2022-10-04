package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IRequest;
import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;

import java.util.concurrent.ForkJoinPool;

public class RequestExecutor {
    private final Server server;
    private final ForkJoinPool forkJoinPool;

    public RequestExecutor(Server server) {
        this.server = server;
        forkJoinPool = ForkJoinPool.commonPool();
    }

    public synchronized void execute(IRequest request, IConnector connector) {
        if (request == null) return;
        try {
            switch (request.getRequestType()) {
                case REGISTRATION -> {
                    forkJoinPool.execute(() -> {
                        if (!server.getUsersManager().isRegistered(request.getUser().getLogin()))
                            server.getResponseSender().sendResponse(new Response().setResponseType(ResponseType.DISCONNECT), connector);
                        else
                            server.getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer("SUCCESS"), connector);
                    });
                    break;
                }
                case EXECUTION_COMMAND -> {
                    forkJoinPool.execute(() -> {
                        server.getExecutor().execute(request.getCommand().setUser(request.getUser()));
                    });
                    break;
                }
                case DISCONNECTION -> {
                    forkJoinPool.execute(connector::disconnect);
                    break;
                }
            }
        } catch (Throwable ignored) {
        }
    }
}
