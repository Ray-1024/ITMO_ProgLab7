package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.commands.*;

import java.io.IOException;

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
        Client client = new Client();
        while (true) {
            try {
                client.tick();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
