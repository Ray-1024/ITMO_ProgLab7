package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Выводит служебную информация о коллекции
 * Например: дата создания, колличество элементов
 */
public class InfoCommand extends BaseCommand {
    public static final InfoCommand command = new InfoCommand();

    private InfoCommand() {
        this.setName("info").setDescription(Phrases.getPhrase("InfoCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void execute() throws RuntimeException {
        try {
            getParentShell().getParentTerminal().getWriter().println(getParentShell().getParentTerminal().getCollectionController().getManagedCollection().getCollectionInfo().toString());
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

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args == null || args.length != 1) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        return this;
    }
}
