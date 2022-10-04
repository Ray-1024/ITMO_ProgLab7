package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.commands.ICommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.IOutputSource;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Класс хранящий коллекцию и управляющий дочерними MicroShell'ами
 * Так же занимается загрузкой и сохранением коллекции
 * Максимальное колличество исполняющихся Microshell'ов : 10
 */
public class Terminal implements Tickable, Executor {

    private StudyGroupCollectionController collectionController;
    private ICommandBuilder commandBuilder;
    //private ForkJoinPool forkJoinPool;

    public IOutputSource getWriter() {
        return commandBuilder.getWriter();
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public Terminal(ICommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
        //forkJoinPool = new ForkJoinPool();
    }

    public StudyGroupCollectionController getCollectionController() {
        return collectionController;
    }


    public Terminal setCollectionController(StudyGroupCollectionController collectionController) {
        this.collectionController = collectionController;
        return this;
    }

    @Override
    public void tick() {
        
    }
}
