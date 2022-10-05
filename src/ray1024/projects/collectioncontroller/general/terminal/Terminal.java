package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.commands.ICommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.IOutputSource;
import ray1024.projects.collectioncontroller.server.Server;

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
    private final Server server;

    public Server getServer() {
        return server;
    }

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

    public Terminal(Server server, ICommandBuilder commandBuilder, StudyGroupCollectionController studyGroupCollectionController) {
        this.commandBuilder = commandBuilder;
        collectionController = studyGroupCollectionController;
        forkJoinPool = new ForkJoinPool();
        this.server = server;
    }

    public StudyGroupCollectionController getCollectionController() {
        return collectionController;
    }

    @Override
    public void tick() {
        try {
            commandBuilder.tick();
            BaseCommand curr = commandBuilder.getCommand();
            if (curr != null) {
                execute(curr.setUser(server.admin).setTerminal(this));
                commandBuilder.reset();
            }
        } catch (Throwable ex) {
            throw ex;
        }
    }
}
