package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.MicroShell;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Команда запускающая на исполнение скрипт
 * Максимальный размер скрипта: 102400 символов
 * Команды в скрипте указываются в том же порядке, что и в интерактивном режиме
 * При ошибке парсинга скрипта, он не будет выполнен, ни единой команды
 */
public class ExecuteScriptCommand extends BaseCommand {
    private String scriptFilename;
    public static final ExecuteScriptCommand command = new ExecuteScriptCommand();

    public ExecuteScriptCommand() {
        setName("execute_script").setDescription(Phrases.getPhrase("ExecuteScriptCommandDescription"));
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() {
        try {
            getParentShell().getParentTerminal().createMicroshell(new MicroShell(getParentShell().getParentTerminal(), new Scanner(new FileInputStream(scriptFilename)), System.out, false));
        } catch (FileNotFoundException e) {
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
