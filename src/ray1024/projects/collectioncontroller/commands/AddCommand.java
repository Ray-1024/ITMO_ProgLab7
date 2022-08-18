package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.StudyGroup;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Команда добавляет в коллекцию элемент
 */
public class AddCommand extends BaseCommand {
    StudyGroup studyGroup = new StudyGroup();
    public static final AddCommand command = new AddCommand(null);

    private AddCommand(Terminal terminal) {
        this.setName("add").setDescription(Phrases.getPhrase("AddCommandDescription")).setParentTerminal(terminal);
        CommandBuilder.registerCommand(this);
        stepsCount = studyGroup.getStepsCount();
    }


    @Override
    public void execute() throws RuntimeException {
        try {
            getParentTerminal().getCollectionController().getManagedCollection().getVec().add(studyGroup);
        } catch (Exception e) {
            throw new RuntimeException(Phrases.getPhrase("Can'tExecuteCommand"));
        }
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
        studyGroup.inputLine(line);
    }

    @Override
    public boolean isObjectReady() {
        return studyGroup.isObjectReady();
    }

    @Override
    public String getStepDescription() {
        return studyGroup.getStepDescription();
    }

    @Override
    public BaseCommand setArgs(String[] args) {
        if (args == null || args.length != 1) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        return this;
    }
}
