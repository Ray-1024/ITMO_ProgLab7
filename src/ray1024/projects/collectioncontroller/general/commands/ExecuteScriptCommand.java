package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.terminal.MicroShell;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;
import ray1024.projects.collectioncontroller.general.readers.FileSourceReader;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.IOException;

/**
 * Команда запускающая на исполнение скрипт
 * Максимальный размер скрипта: 102400 символов
 * Команды в скрипте указываются в том же порядке, что и в интерактивном режиме
 * При ошибке парсинга скрипта, он не будет выполнен, ни единой команды
 */
public class ExecuteScriptCommand extends BaseCommand {
    private String scriptFilename;
    private BaseCommand[] commands;
    public static final ExecuteScriptCommand command = new ExecuteScriptCommand();

    public ExecuteScriptCommand() {
        setName("execute_script").setDescription(Phrases.getPhrase("ExecuteScriptCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void execute() {
        try {
            getParentShell().getParentTerminal().addMicroshell(new MicroShell(getParentShell().getParentTerminal(), new CommandBuilder(new FileSourceReader(scriptFilename), new ConsoleSourceWriter()), false));
        } catch (IOException e) {
            throw new RuntimeException(Phrases.getPhrase("Can'tFindScript"));
        }
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args.length != 2) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        scriptFilename = args[1];

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
