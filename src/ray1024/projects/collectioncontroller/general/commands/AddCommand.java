package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

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
    public void execute() {
        try {
            getParentShell().getParentTerminal().getCollectionController().getManagedCollection().getVec().add(studyGroup);
        } catch (Exception e) {
            getParentShell().getWriter().println(Phrases.getPhrase("Can'tExecuteCommand"));
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
        try {
            if (args == null || args.length != 1)
                getParentShell().getWriter().println(Phrases.getPhrase("WrongCommandArgs"));
        } catch (Throwable ex) {

        }
        return this;
    }

}
