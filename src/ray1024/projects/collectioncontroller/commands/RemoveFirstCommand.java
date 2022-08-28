package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Удаляет из коллекции первый элемент
 */
public class RemoveFirstCommand extends BaseCommand {
    public static final RemoveFirstCommand command = new RemoveFirstCommand();

    private RemoveFirstCommand() {
        setName("remove_first").setDescription(Phrases.getPhrase("RemoveFirstCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void execute() {
        getParentShell().getParentTerminal().getCollectionController().getManagedCollection().getVec().remove(0);
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
