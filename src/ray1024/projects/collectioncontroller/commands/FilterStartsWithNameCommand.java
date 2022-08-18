package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Выводит те элементы коллекции, названия групп которых начинаются с аргумента команды
 */
public class FilterStartsWithNameCommand extends BaseCommand {
    String name = null;
    public static final FilterStartsWithNameCommand command = new FilterStartsWithNameCommand(null);

    private FilterStartsWithNameCommand(Terminal terminal) {
        setName("filter_starts_with_name").setDescription(Phrases.getPhrase("FilterStartsWithNameCommandDescription")).setParentTerminal(terminal);
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() {
        final int[] ind = new int[1];
        getParentTerminal().getCollectionController().getManagedCollection().stream().filter((elem) -> elem.getName().startsWith(name)).forEach((elem) -> {
            getParentTerminal().getOutputter().writeLine(String.format("    %d. %s", ++ind[0], elem));
        });
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args != null && args.length == 2) {
            name = args[1];
            return this;
        }
        throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {

    }

    @Override
    public String getStepDescription() {
        return "";
    }
}
