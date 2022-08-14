package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.data.StudyGroup;
import ray1024.projects.collectioncontroller.terminal.MicroShell;

/**
 * Перезаписывает элемент коллекции с ID равным аргументу
 */
public class UpdateIDCommand extends BaseCommand {
    int id = -1;
    StudyGroup elem = null;

    public UpdateIDCommand(MicroShell _parentShell, StudyGroup element, int intID) {
        super(_parentShell);
        elem = element;
        id = intID;
    }

    @Override
    public void execute() {
        if (parentShell != null && parentShell.getManagedCollection() != null)
            parentShell.getManagedCollection().updateById(id, elem);
    }
}
