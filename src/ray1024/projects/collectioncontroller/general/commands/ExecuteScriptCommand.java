package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.readers.ListSourceReader;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

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
    private String scriptFilename = "...";
    public static final ExecuteScriptCommand command = new ExecuteScriptCommand();
    private CommandBuilder scriptCommandBuilder;
    private LinkedList<String> scriptText;

    public ExecuteScriptCommand() {
        setName("execute_script").setDescription(Phrases.getPhrase("ExecuteScriptCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() {
        try {
            scriptCommandBuilder = new CommandBuilder(new ListSourceReader(scriptText), getParentShell().getWriter());
            getParentShell().getParentTerminal().addMicroshell(new MicroShell(getParentShell().getParentTerminal(), scriptCommandBuilder, false));
        } catch (Throwable ignored) {
        }
    }

    @Override
    public BaseCommand setArgs(String[] args) {
        try {
            if (args.length != 2) {
                getParentShell().getWriter().println(Phrases.getPhrase("WrongCommandArgs"));
            }
            scriptFilename = args[1];
            try {
                long sz = Files.size(Paths.get(scriptFilename));
                if (sz > 1024 * 1024) {
                    getParentShell().getWriter().println(Phrases.getPhrase("TooLongScript"));
                }
            } catch (Throwable ex) {
                System.out.println(Phrases.getPhrase("TooLongScript"));
                return this;
            }
            scriptText = new LinkedList<>(Files.readAllLines(Paths.get(scriptFilename)));
            if (scriptText.stream().anyMatch((line) -> line.startsWith(command.getName()))) {
                getParentShell().getWriter().println(Phrases.getPhrase("UnsupportedScriptLevel"));
                scriptText = null;
                return this;
            }
        } catch (Throwable ex) {
            getParentShell().getWriter().println(Phrases.getPhrase("Can'tFindScript"));
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
