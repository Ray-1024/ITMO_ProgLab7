package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.StudyGroup;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Перезаписывает элемент коллекции с ID равным аргументу
 */
public class UpdateByIDCommand extends BaseCommand {
    int updateID = -1;
    StudyGroup elem = new StudyGroup();
    public static final UpdateByIDCommand command = new UpdateByIDCommand();

    private UpdateByIDCommand() {
        setName("update_by_id").setDescription(Phrases.getPhrase("UpdateByIdCommandDescription"));
        CommandBuilder.registerCommand(this);
        stepsCount = elem.getStepsCount();
    }

    @Override
    public void execute() {

        elem.setId(getParentShell().getParentTerminal().getCollectionController().getManagedCollection().stream().filter((elem) -> {
            return elem.getId() == updateID;
        }).findFirst().get().getId());
        getParentShell().getParentTerminal().getCollectionController().getManagedCollection().getVec().replaceAll((i) -> {
            if (i.getId() == elem.getId()) return elem;
            return i;
        });
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args != null && args.length == 2) {
            try {
                updateID = Integer.parseInt(args[1]);
                return this;
            } catch (NumberFormatException ignored) {
            }
        }
        throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
        elem.inputLine(line);
    }

    @Override
    public String getStepDescription() {
        return elem.getStepDescription();
    }
}
