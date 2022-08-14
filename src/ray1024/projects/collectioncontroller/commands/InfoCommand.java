package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Выводит служебную информация о коллекции
 * Например: дата создания, колличество элементов
 */
public class InfoCommand extends BaseCommand {
    public InfoCommand(Terminal terminal) {
        this.setName("info").setDescription(Phrases.getPhrase("InfoCommandDescription")).setParentTerminal(terminal);
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() throws RuntimeException {
        try {
            getParentTerminal().getOutputter().writeLine(getParentTerminal().getCollectionController().getManagedCollection().getCollectionInfo().toString());
        } catch (Throwable ex) {
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
}
