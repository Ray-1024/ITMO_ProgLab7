package ray1024.projects.collectioncontroller;

import ray1024.projects.collectioncontroller.commands.*;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.ConsoleInputter;
import ray1024.projects.collectioncontroller.tools.ConsoleOutputter;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Главный красс создающий и запускающий Терминал
 *
 * @MyTag aloha
 */
public class Main {

    public static void main(String[] args) {
        Phrases.setLocale("Russian");
        String filename = null;
        try {
            filename = System.getenv("CCFilename");
        } catch (RuntimeException ignored) {
            System.out.println(Phrases.getPhrase("EnvironmentVariableDoesn'tExist"));
        }
        Terminal terminal = new Terminal(new ConsoleInputter(), new ConsoleOutputter(), filename);
        // Creating new commands for registration in CommandBuilder
        new HelpCommand(terminal);
        new InfoCommand(terminal);
        new AddCommand(terminal);
        new ClearCommand(terminal);
        new AddIfMinCommand(terminal);
        new ExitCommand(terminal);
        new ShowCommand(terminal);
        new SaveCommand(terminal);
        new RemoveFirstCommand(terminal);
        new ShuffleCommand(terminal);
        new PrintDescendingCommand(terminal);
        new RemoveByIDCommand(terminal);
        new UpdateByIDCommand(terminal);
        new FilterLessThanStudentsCountCommand(terminal);
        new FilterStartsWithNameCommand(terminal);
        //
        terminal.run();
    }

}
