package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.*;

import java.util.concurrent.ForkJoinPool;

public class RequestExecutor {
    private final Server server;
    private final ForkJoinPool forkJoinPool;

    public RequestExecutor(Server server) {
        this.server = server;
        forkJoinPool = new ForkJoinPool();
    }

    public synchronized void executeRequest(IRequest request, IConnector connector) {
        if (request == null || connector == null) {
            return;
        }
        try {
            System.out.println("--- REQUEST ---");
            switch (request.getRequestType()) {
                case REGISTRATION: {
                    try {
                        System.out.println("--- REGISTRATION REQUEST ---");
                        forkJoinPool.execute(() -> {
                            if (!server.getUsersManager().isRegistered(request.getUser())) {
                                server.getUsersManager().addUser(request.getUser());
                                server.getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer("SUCCESS"), connector);
                            }
                        });
                    } catch (Exception ex) {
                        System.out.println("---REGISTRATION ERROR ---");
                    }
                    return;
                }
                case AUTHORIZATION: {
                    try {
                        if (!server.getUsersManager().isRegistered(request.getUser()))
                            server.getResponseSender().sendResponse(new Response().setResponseType(ResponseType.DISCONNECT), connector);
                        else
                            server.getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer("SUCCESS"), connector);
                    } catch (Throwable ignored) {
                        System.out.println("--- AUTHORIZATION ERROR ---");
                    }
                    return;
                }
                case EXECUTION_COMMAND: {
                    if (request.getUser() == null || request.getCommand() == null) return;
                    if (!server.getUsersManager().isRegistered(request.getUser())) return;
                    System.out.println("--- EXECUTION REQUEST ---");
                    System.out.println("--- " + request.getCommand().getName() + " command ---");
                    forkJoinPool.execute(() -> server.getTerminal().execute(request.getCommand().setUser(request.getUser().setConnector(connector)).setTerminal(server.getTerminal())));
                    return;
                }
                case DISCONNECTION: {
                    System.out.println("--- DISCONNECTION REQUEST ---");
                    forkJoinPool.execute(connector::disconnect);
                    return;
                }
            }
        } catch (Throwable ex) {
            //throw ex;
        }
    }
}
