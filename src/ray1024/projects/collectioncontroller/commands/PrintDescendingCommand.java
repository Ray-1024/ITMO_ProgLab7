package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.MyCollection;
import ray1024.projects.collectioncontroller.data.StudyGroup;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Выводит элементы коллекции в порядке лексикографического не возростания названий их групп
 * Не меняет коллекцию
 */
public class PrintDescendingCommand extends BaseCommand {
    public static final PrintDescendingCommand command = new PrintDescendingCommand();

    private PrintDescendingCommand() {
        setName("print_descending").setDescription(Phrases.getPhrase("PrintDescendingDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void execute() {


        MyCollection<StudyGroup> coll = getParentShell().getParentTerminal().getCollectionController().getManagedCollection();
        ArrayList<Integer> arr = new ArrayList<>(coll.size());
        for (int i = 0; i < coll.size(); ++i) arr.add(i);

        arr.sort(Comparator.comparing(coll::get));
        for (int i = coll.size() - 1; i >= 0; --i) {
            getParentShell().getWriter().print("    " + (coll.size() - i) + ". ");
            getParentShell().getWriter().println(coll.get(arr.get(i)));
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
