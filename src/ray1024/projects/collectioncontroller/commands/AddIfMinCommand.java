package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.StudyGroup;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Команда добавляет в коллекция элемент если он меньше всех элементов коллекции
 * Т.к сортировка по умолчанию сравнивает группы по названию, то добавляет элемент если название его группы лексикографически меньше любого другого названия групп из коллекции
 */
public class AddIfMinCommand extends BaseCommand {
    private final StudyGroup studyGroup = new StudyGroup();

    public static final AddIfMinCommand command = new AddIfMinCommand(null);
    private AddIfMinCommand(Terminal terminal) {
        this.setName("add_if_min").setDescription(Phrases.getPhrase("AddIfMinCommandDescription")).setParentShell(terminal);
        CommandBuilder.registerCommand(this);
        stepsCount = studyGroup.getStepsCount();
    }


    @Override
    public void execute() throws RuntimeException {
        try {
            if (getParentShell().getCollectionController().getManagedCollection().getVec().stream().allMatch((i) -> {
                return i.compareTo(studyGroup) > 0;
            })) getParentShell().getCollectionController().getManagedCollection().getVec().add(studyGroup);
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
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args == null || args.length != 1) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        return this;
    }
}
