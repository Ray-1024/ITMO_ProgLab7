package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.util.stream.Collectors;

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
    public void run() {
        //getParentShell().getParentTerminal().getWriter().println(getParentShell().getParentTerminal().getCollectionController().getManagedCollection().toString());
        int[] ind = new int[1];
        getParentShell().getParentTerminal().getCollectionController().getManagedCollection().stream().forEach((elem -> {
            getParentShell().getParentTerminal().getWriter().println((++ind[0]) + elem.toString());
        }));
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
