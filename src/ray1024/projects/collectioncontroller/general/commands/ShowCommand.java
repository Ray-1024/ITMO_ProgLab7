package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Показывает все элементы коллекции
 */
public class ShowCommand extends BaseCommand {
    public static final ShowCommand command = new ShowCommand();

    private ShowCommand() {
        setName("show").setDescription(Phrases.getPhrase("ShowCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void execute() {
        getParentShell().getParentTerminal().getWriter().println(getParentShell().getParentTerminal().getCollectionController().getManagedCollection().toString());
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
