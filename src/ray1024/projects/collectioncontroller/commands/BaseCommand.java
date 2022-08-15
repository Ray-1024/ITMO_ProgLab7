package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.interfaces.IExecute;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.SteppedInputObject;

import java.io.Serializable;

/**
 * Базовый класс для реализации команд, исполняемых Microshell
 */
public abstract class BaseCommand extends SteppedInputObject implements IExecute, Serializable, Cloneable {

    private String name = "BaseCommand";
    private String description = "Base command";
    private Terminal parentTerminal = null;

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

    public Terminal getParentTerminal() {
        return parentTerminal;
    }

    public BaseCommand setParentTerminal(Terminal parentTerminal) {
        this.parentTerminal = parentTerminal;
        return this;
    }

    @Override
    public BaseCommand clone() {
        try {
            return (BaseCommand) super.clone();
        } catch (CloneNotSupportedException ignored) {
            return null;
        }
    }
}
