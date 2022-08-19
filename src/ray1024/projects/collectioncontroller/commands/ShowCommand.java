package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Показывает все элементы коллекции
 */
public class ShowCommand extends BaseCommand {
    public static final ShowCommand command = new ShowCommand(null);

    private ShowCommand(Terminal terminal) {
        setName("show").setDescription(Phrases.getPhrase("ShowCommandDescription")).setParentShell(terminal);
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() {
        getParentShell().getOutputter().writeLine(getParentShell().getCollectionController().getManagedCollection().toString());
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
