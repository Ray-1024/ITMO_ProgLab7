package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;
import sun.awt.SunGraphicsCallback;

/**
 * Завершает программу
 * Не сохраняет коллекцию в файл
 */
public class ExitCommand extends BaseCommand {

    public ExitCommand(Terminal terminal) {
        setName("exit").setDescription(Phrases.getPhrase("ExitCommandDescription")).setParentTerminal(terminal);
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
    }

    @Override
    public String getStepDescription() {
        return "";
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args == null || args.length != 1) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        return this;
    }
}
