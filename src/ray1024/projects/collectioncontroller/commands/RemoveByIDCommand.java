package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.MicroShell;

/**
 * Удаляет из коллекции элемент с ID равным аргументом, если такой элемент в коллекции имеется
 */
public class RemoveByIDCommand extends BaseCommand {

    int removeID = -1;

    public RemoveByIDCommand(MicroShell _parentShell, int _ID) {
        super(_parentShell);
        removeID = _ID;
    }

    @Override
    public void execute() {
        if (parentShell != null && parentShell.getManagedCollection() != null)
            parentShell.getManagedCollection().removeByID(removeID);
    }
}
