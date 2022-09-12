package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.readers.ListSourceReader;
import ray1024.projects.collectioncontroller.general.terminal.MicroShell;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;
import ray1024.projects.collectioncontroller.general.readers.FileSourceReader;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.writers.DevNullWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Команда запускающая на исполнение скрипт
 * Максимальный размер скрипта: 102400 символов
 * Команды в скрипте указываются в том же порядке, что и в интерактивном режиме
 * При ошибке парсинга скрипта, он не будет выполнен, ни единой команды
 */
public class ExecuteScriptCommand extends BaseCommand {
    private String scriptFilename;
    public static final ExecuteScriptCommand command = new ExecuteScriptCommand();
    private CommandBuilder scriptCommandBuilder;

    public ExecuteScriptCommand() {
        setName("execute_script").setDescription(Phrases.getPhrase("ExecuteScriptCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void execute() {
        getParentShell().getParentTerminal().addMicroshell(new MicroShell(getParentShell().getParentTerminal(), scriptCommandBuilder, false));
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args.length != 2) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        scriptFilename = args[1];
        try {
            scriptCommandBuilder = new CommandBuilder(new ListSourceReader(new LinkedList<>(Files.readAllLines(Paths.get(scriptFilename)))), new DevNullWriter());
        } catch (Throwable ex) {
            throw new RuntimeException("Can'tFindScript");
        }
        return this;
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {

    }

    @Override
    public String getStepDescription() {
        return "";
    }
}
