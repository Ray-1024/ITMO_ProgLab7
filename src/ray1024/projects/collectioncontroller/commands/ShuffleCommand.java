package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.util.Collections;

/**
 * Случайным образом перемешивает элементы коллекции
 */
public class ShuffleCommand extends BaseCommand {
    public static final ShuffleCommand command = new ShuffleCommand(null);

    private ShuffleCommand(Terminal terminal) {
        setName("shuffle").setDescription(Phrases.getPhrase("ShuffleCommandDescription")).setParentTerminal(terminal);
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() {
        Collections.shuffle(getParentTerminal().getCollectionController().getManagedCollection().getVec());
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
