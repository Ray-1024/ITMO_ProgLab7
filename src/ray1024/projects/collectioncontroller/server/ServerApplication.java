package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.commands.*;
import ray1024.projects.collectioncontroller.general.communication.Connector;
import ray1024.projects.collectioncontroller.general.communication.Request;
import ray1024.projects.collectioncontroller.general.communication.RequestType;
import ray1024.projects.collectioncontroller.general.interfaces.IConnector;
import ray1024.projects.collectioncontroller.general.interfaces.IRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

/**
 * Главный красс создающий и запускающий Терминал
 *
 * @MyTag aloha
 */
public class ServerApplication {

    static {
        AddCommand.command.getName();
        AddIfMinCommand.command.getName();
        ClearCommand.command.getName();
        ExitCommand.command.getName();
        FilterLessThanStudentsCountCommand.command.getName();
        FilterStartsWithNameCommand.command.getName();
        HelpCommand.command.getName();
        InfoCommand.command.getName();
        PrintDescendingCommand.command.getName();
        RemoveByIDCommand.command.getName();
        RemoveFirstCommand.command.getName();
        SaveCommand.command.getName();
        ShowCommand.command.getName();
        ShuffleCommand.command.getName();
        UpdateByIDCommand.command.getName();
        ExecuteScriptCommand.command.getName();
    }
    //  User class
    //  Request class(type,command,user)
    //  Response class(type,text_answer,server)
    //  ConnectionAcceptor(nonblocking) + returns new User
    //  Terminal for everyone of users
    //  UsersManager(disconnect, connect)
    //
    //


    public static void main(String[] args) {

        Server server = new Server();
        while (true) {
            try {
                server.tick();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        /*try {
            ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor();
            IConnector client = new Connector(InetAddress.getByName("localhost"), 44147);
            client.sendRequestToServer(new Request().setRequestType(RequestType.DISCONNECTION));
            IConnector server = null;
            Socket socket;
            while (true) {
                if (server == null) {
                    socket = connectionAcceptor.getNewConnection();
                    if (socket != null) server = new Connector(socket);
                } else {
                    IRequest request = server.receiveRequestFromClient();
                    if (request != null) {
                        System.out.println("---REQUEST---");
                        System.out.println("TYPE: " + request.getRequestType());
                        System.out.println("-----END-----");
                    }
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
*/
    }

}
