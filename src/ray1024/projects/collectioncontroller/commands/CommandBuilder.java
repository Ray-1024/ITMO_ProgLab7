package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
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
        if (!commands.containsKey(command.getName())) commands.put(command.getName(), command);
    }

    public static Stream<BaseCommand> getRegisteredCommandsStream() {
        return commands.values().stream();
    }


    public static BaseCommand parseInteractiveCommand(String line, Terminal executor) throws IllegalStateException {
        String[] args = line.split(" ");
        if (args.length == 0) return null;
        if (!commands.containsKey(args[0])) throw new IllegalStateException(Phrases.getPhrase("WrongCommandInLine"));
        BaseCommand prototype = commands.get(args[0]).setParentTerminal(executor).setArgs(args);
        prototype.reset();
        return prototype.clone();
    }
/*
    public static LinkedList<BaseCommand> parseCommandFromScript(MicroShell microShell, String[] lines) {
        try {
            LinkedList<BaseCommand> queue = new LinkedList<>();
            for (int offset = 0; offset < lines.length; ++offset) {
                String[] args = lines[offset].split(" ");
                if (!commands.containsKey(args[0])) {
                    System.out.println(Phrases.UnregisteredScriptCommand);
                    return null;
                }
                BaseCommand prototype = commands.get(args[0]).setParentShell(microShell);
                int cntLines = prototype.parseCommandFromText(args, lines, offset);
                if (cntLines > 0) queue.add((BaseCommand) prototype.clone());
                else {
                    System.out.println(Phrases.WrongCommandScriptSpelling);
                    return null;
                }
            }

        } catch (Throwable ignored) {
        }
        System.out.println(Phrases.WrongCommandScriptSpelling);
        return null;
    }
*/

}
