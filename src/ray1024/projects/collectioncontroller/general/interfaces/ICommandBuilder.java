package ray1024.projects.collectioncontroller.general.interfaces;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

public interface ICommandBuilder extends Tickable {
    BaseCommand getCommand();

    IInputSource getReader();

    IOutputSource getWriter();

    void reset();
}
