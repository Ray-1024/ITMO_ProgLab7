package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.MicroShell;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Класс-Инструмент
 * 1. Распознование команд и запрос аргументов в интерактивном режиме
 * 2. Создание коллекции команд из списка строк(для парсинга скриптов)
 */

public class CommandBuilder {

    private static final HashMap<String, BaseCommand> commands = new HashMap<>();

    public static void registerCommand(BaseCommand command) {
        commands.put(command.getName(), command);
    }

    public static Stream<BaseCommand> getRegisteredCommandsStream() {
        return commands.values().stream();
    }


    public static BaseCommand parseInteractiveCommand(String line, MicroShell executor) throws IllegalStateException {
        if ("".equals(line)) return null;
        String[] args = line.split(" ");
        if (!commands.containsKey(args[0])) throw new IllegalStateException(Phrases.getPhrase("WrongCommandInLine"));
        BaseCommand prototype = commands.get(args[0]).setParentShell(executor).setArgs(args);
        prototype.reset();
        return prototype.clone();
    }

}
