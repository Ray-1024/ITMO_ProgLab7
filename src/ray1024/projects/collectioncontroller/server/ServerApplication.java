package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.commands.*;
import ray1024.projects.collectioncontroller.server.Server;

import java.io.IOException;

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
    }

}
