package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.MyCollection;
import ray1024.projects.collectioncontroller.terminal.MicroShell;

/**
 * Выводит те элементы коллекции, которые имеют меньшее колличество студентов в группе чем задано в параметре
 */
public class FilterLessThanStudentsCountCommand extends BaseCommand {
    int studentsCount = 0;

    public FilterLessThanStudentsCountCommand(MicroShell _parentShell, int studentsCount) {
        super(_parentShell);
        this.studentsCount = studentsCount;
    }

    @Override
    public void execute() {
        if (parentShell != null && parentShell.getManagedCollection() != null) {
            MyCollection coll = parentShell.getManagedCollection();
            int ind = 1;
            for (int i = 0; i < coll.size(); ++i) {
                if (coll.get(i).getStudentsCount() < studentsCount) {
                    System.out.print("    " + ind + ". ");
                    System.out.println(coll.get(i));
                }
            }
        }
    }
}
