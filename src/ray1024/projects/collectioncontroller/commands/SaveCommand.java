package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.MicroShell;

/**
 * Сохраняет коллекцию в файл с названием указанным в переменной среды 'CCFilename'
 * Колекция хранится в формате XML
 */
public class SaveCommand extends BaseCommand {

    public SaveCommand(MicroShell _parentShell) {
        super(_parentShell);
    }

    @Override
    public void execute() {
        if (parentShell != null && parentShell.getParentTerminal() != null)
            parentShell.getParentTerminal().saveCollection();
    }
}
