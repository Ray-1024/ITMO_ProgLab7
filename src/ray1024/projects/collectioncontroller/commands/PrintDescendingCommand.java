package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.MyCollection;
import ray1024.projects.collectioncontroller.terminal.MicroShell;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Выводит элементы коллекции в порядке лексикографического не возростания названий их групп
 * Не меняет коллекцию
 */
public class PrintDescendingCommand extends BaseCommand {
    public PrintDescendingCommand(MicroShell _parentShell) {
        super(_parentShell);
    }

    @Override
    public void execute() {

        if (parentShell != null && parentShell.getManagedCollection() != null) {
            MyCollection coll = parentShell.getManagedCollection();
            ArrayList<Integer> arr = new ArrayList<>(coll.size());
            for (int i = 0; i < coll.size(); ++i) arr.add(i);

            arr.sort(Comparator.comparing(coll::get));
            for (int i = coll.size() - 1; i >= 0; --i) {
                System.out.print("    " + (coll.size() - i) + ". ");
                System.out.println(coll.get(arr.get(i)));
            }
        }
    }
}
