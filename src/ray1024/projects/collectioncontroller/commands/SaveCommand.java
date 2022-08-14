package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.MicroShell;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Сохраняет коллекцию в файл с названием указанным в переменной среды 'CCFilename'
 * Колекция хранится в формате XML
 */
public class SaveCommand extends BaseCommand {

    public SaveCommand(Terminal terminal) {
        setName("save").setDescription(Phrases.getPhrase("SaveCommandDescription")).setParentTerminal(terminal);
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() throws RuntimeException {
        getParentTerminal().getCollectionController().saveCollection();
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {

    }

    @Override
    public String getStepDescription() {
        return "";
    }
}
