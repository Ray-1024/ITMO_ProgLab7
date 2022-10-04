package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.commands.ICommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.IOutputSource;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * Класс хранящий коллекцию и управляющий дочерними MicroShell'ами
 * Так же занимается загрузкой и сохранением коллекции
 * Максимальное колличество исполняющихся Microshell'ов : 10
 */
public class Terminal implements Tickable, Executor {

    private final StudyGroupCollectionController collectionController;
    private final ICommandBuilder commandBuilder;
    private final ForkJoinPool forkJoinPool;

    public IOutputSource getWriter() {
        return commandBuilder.getWriter();
    }

    @Override
    public synchronized void execute(Runnable command) {
        try {
            forkJoinPool.execute(command);
        } catch (Throwable ignored) {
        }
    }

    public Terminal(ICommandBuilder commandBuilder, StudyGroupCollectionController studyGroupCollectionController) {
        this.commandBuilder = commandBuilder;
        collectionController = studyGroupCollectionController;
        forkJoinPool = new ForkJoinPool();
    }

    public StudyGroupCollectionController getCollectionController() {
        return collectionController;
    }

    @Override
    public void tick() {
        try {
            BaseCommand curr = commandBuilder.getCommand();
            if (curr != null) {
                execute(curr);
                commandBuilder.reset();
            }
        } catch (Throwable ignored) {
        }
    }
}
