package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.MicroShell;

/**
 * Случайным образом перемешивает элементы коллекции
 */
public class ShuffleCommand extends BaseCommand {

    public ShuffleCommand(MicroShell _parentShell) {
        super(_parentShell);
    }

    @Override
    public void execute() {
        if (parentShell != null && parentShell.getManagedCollection() != null)
            parentShell.getManagedCollection().randomShuffle();
    }
}
