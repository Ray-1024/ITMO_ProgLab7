package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Команда очищающая коллекцию, делая ее пустой
 */
public class ClearCommand extends BaseCommand {
    public static final ClearCommand command = new ClearCommand();

    private ClearCommand() {
        this.setName("clear").setDescription(Phrases.getPhrase("ClearCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() throws RuntimeException {
        try {
            getParentShell().getParentTerminal().getCollectionController().getManagedCollection().clear();
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
