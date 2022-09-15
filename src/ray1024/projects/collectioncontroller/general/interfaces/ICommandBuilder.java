package ray1024.projects.collectioncontroller.general.interfaces;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;

import java.io.Serializable;

public interface ICommandBuilder extends Tickable, Serializable {
    BaseCommand getCommand();

    ICommandBuilder addCommand(BaseCommand command);

    IInputSource getReader();

    IOutputSource getWriter();

    boolean isDone();

    void reset();
}
