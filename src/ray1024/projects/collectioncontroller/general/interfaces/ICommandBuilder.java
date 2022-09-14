package ray1024.projects.collectioncontroller.general.interfaces;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;

public interface ICommandBuilder extends Tickable {
    BaseCommand getCommand();

    ICommandBuilder addCommand(BaseCommand command);

    IInputSource getReader();

    IOutputSource getWriter();

    boolean isDone();

    void reset();
}
