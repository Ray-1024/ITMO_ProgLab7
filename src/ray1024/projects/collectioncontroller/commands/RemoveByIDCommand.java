package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Удаляет из коллекции элемент с ID равным аргументом, если такой элемент в коллекции имеется
 */
public class RemoveByIDCommand extends BaseCommand {

    int removeID = -1;
    public static final RemoveByIDCommand command = new RemoveByIDCommand(null);

    private RemoveByIDCommand(Terminal terminal) {
        setName("remove_by_id").setDescription(Phrases.getPhrase("RemoveByIdCommandDescription")).setParentShell(terminal);
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() {
        getParentShell().getCollectionController().getManagedCollection().getVec().removeIf((elem) -> {
            return elem.getId() == removeID;
        });
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
        if (args != null && args.length == 2) {
            try {
                removeID = Integer.parseInt(args[1]);
                return this;
            } catch (NumberFormatException ignored) {
            }
        }
        throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
    }
}
