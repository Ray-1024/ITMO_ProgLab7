package ray1024.projects.collectioncontroller;

import ray1024.projects.collectioncontroller.commands.*;
import ray1024.projects.collectioncontroller.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.ConsoleSourceReader;
import ray1024.projects.collectioncontroller.tools.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.tools.ConsoleSourceWriter;
import ray1024.projects.collectioncontroller.tools.Phrases;

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

        Terminal terminal = new Terminal(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter());
        while (true) {
            try {
                terminal.tick();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}
