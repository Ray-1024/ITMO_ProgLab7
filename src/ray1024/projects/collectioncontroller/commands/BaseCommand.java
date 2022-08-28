package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.interfaces.IExecute;
import ray1024.projects.collectioncontroller.terminal.MicroShell;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.SteppedInputObject;

import java.io.Serializable;

/**
 * Базовый класс для реализации команд, исполняемых Microshell
 */
public abstract class BaseCommand extends SteppedInputObject implements IExecute, Serializable, Cloneable {

    private String name = "BaseCommand";
    private String description = "Base command";
    private MicroShell parentShell = null;

    public BaseCommand() {
        stepsCount = 0;
    }

    public String getName() {
        return name;
    }

    public abstract BaseCommand setArgs(String[] args) throws RuntimeException;


    public BaseCommand setName(String name) {
        this.name = name;
        return this;
    }


    public BaseCommand setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MicroShell getParentShell() {
        return parentShell;
    }

    public BaseCommand setParentShell(MicroShell parentShell) {
        this.parentShell = parentShell;
        return this;
    }

    @Override
    protected BaseCommand clone() throws CloneNotSupportedException {
        return (BaseCommand) super.clone();
    }
}
