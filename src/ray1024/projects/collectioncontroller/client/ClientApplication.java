package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.general.commands.*;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Главный красс создающий и запускающий Терминал
 *
 * @MyTag aloha
 */
public class ClientApplication {

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
        ShowCommand.command.getName();
        ShuffleCommand.command.getName();
        UpdateByIDCommand.command.getName();
        ExecuteScriptCommand.command.getName();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            while (true) {
                client.tick();
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

    }
}