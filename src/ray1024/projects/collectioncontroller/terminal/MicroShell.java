package ray1024.projects.collectioncontroller.terminal;

import ray1024.projects.collectioncontroller.interfaces.IExecute;

import java.util.ArrayList;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements IExecute {
    private final Terminal parentTerminal;
    private final ArrayList<IExecute> commandsQueue = new ArrayList<IExecute>();

    public Terminal getParentTerminal() {
        return parentTerminal;
    }

    public MicroShell(Terminal parentTerminal) {
        this.parentTerminal = parentTerminal;
    }

    @Override
    public void execute() throws RuntimeException {
        for (IExecute target : commandsQueue) {
            target.execute();
        }
        commandsQueue.clear();
    }

    public void addCommandInQueue(IExecute command) {
        if (command != null) commandsQueue.add(command);
    }

    public void addCommandsInQueue(ArrayList<? extends IExecute> commandsDump) {
        if (commandsDump != null) commandsQueue.addAll(commandsDump);
    }

}
