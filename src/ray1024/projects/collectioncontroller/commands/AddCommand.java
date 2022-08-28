package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.StudyGroup;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Команда добавляет в коллекцию элемент
 */
public class AddCommand extends BaseCommand {
    private final StudyGroup studyGroup = new StudyGroup();
    public static final AddCommand command = new AddCommand();

    private AddCommand() {
        setName("add").setDescription(Phrases.getPhrase("AddCommandDescription"));
        stepsCount = studyGroup.getStepsCount();
        CommandRegister.registerCommand(this);
    }


    @Override
    public void execute() throws RuntimeException {
        try {
            getParentShell().getParentTerminal().getCollectionController().getManagedCollection().getVec().add(studyGroup);
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
