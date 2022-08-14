package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.MyCollection;
import ray1024.projects.collectioncontroller.terminal.MicroShell;

/**
 * Выводит те элементы коллекции, названия групп которых начинаются с аргумента команды
 */
public class FilterStartsWithNameCommand extends BaseCommand {
    String name = null;

    public FilterStartsWithNameCommand(MicroShell _parentShell, String name) {
        super(_parentShell);
        this.name = name;
    }

    @Override
    public void execute() {
        if (name != null && parentShell != null && parentShell.getManagedCollection() != null) {
            MyCollection coll = parentShell.getManagedCollection();
            int ind = 1;
            for (int i = 0; i < coll.size(); ++i) {
                if (coll.get(i).getName().startsWith(name)) {
                    System.out.print((ind) + ". ");
                    ind++;
                    System.out.println(coll.get(i));
                }
            }
        }
    }
}
