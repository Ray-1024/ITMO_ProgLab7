package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Команда очищающая коллекцию, делая ее пустой
 */
public class ClearCommand extends BaseCommand {

    public ClearCommand(Terminal terminal) {
        this.setName("clear").setParentTerminal(terminal).setDescription(Phrases.getPhrase("ClearCommandDescription"));
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() throws RuntimeException {
        try {
            getParentTerminal().getCollectionController().getManagedCollection().clear();
        } catch (Exception e) {
            throw new RuntimeException(Phrases.getPhrase("Can'tExecuteCommand"));
        }
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
