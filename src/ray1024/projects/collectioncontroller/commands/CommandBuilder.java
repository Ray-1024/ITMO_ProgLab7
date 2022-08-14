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
        BaseCommand prototype = commands.get(args[0]).setParentTerminal(executor);
        prototype.reset();
        return prototype;
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

//    public static BaseCommand parseInteractiveCommandOld(String line, MicroShell microShell) throws EOFException {
//        if (line == null || line.equals("")) return null;
//        try {
//            String[] args = line.split(" ");
//            if (args.length < 1 || args.length > 2) throw new RuntimeException();
//            if ("help".equals(args[0]) && args.length == 1) return new HelpCommand();
//            if ("info".equals(args[0]) && args.length == 1) return new InfoCommand(microShell);
//            if ("show".equals(args[0]) && args.length == 1) return new ShowCommand(microShell);
//            if ("add".equals(args[0]) && args.length == 1) {
//                StudyGroup studyGroup = new StudyGroup();
//                try {
//                    studyGroup.input(microShell.getParentTerminal().getScanner());
//                    return new AddCommand(microShell, studyGroup);
//                } catch (EOFException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (args[0].equals("update") && Integer.parseInt(args[1]) > 0) {
//                StudyGroup elem = new StudyGroup();
//                try {
//                    elem.input(microShell.getParentTerminal().getScanner());
//                    return new UpdateIDCommand(microShell, elem, Integer.parseInt(line.substring(7)));
//                } catch (EOFException e) {
//                    throw new EOFException();
//                }
//            }
//            if (args[0].equals("remove_by_id") && args.length == 2) {
//                return new RemoveByIDCommand(microShell, Integer.parseInt(args[1]));
//            }
//            if ("clear".equals(args[0]) && args.length == 1) return new ClearCommand(microShell);
//            if ("save".equals(args[0]) && args.length == 1) return new SaveCommand(microShell);
//            if ("execute_script".equals(args[0]) && args.length == 2)
//                return new ExecuteScriptCommand(microShell, args[1]);
//            if ("exit".equals(args[0]) && args.length == 1) return new ExitCommand();
//            if ("remove_first".equals(args[0]) && args.length == 1) return new RemoveFirstCommand(microShell);
//            if ("add_if_min".equals(args[0]) && args.length == 1) {
//                StudyGroup studyGroup = new StudyGroup();
//                try {
//                    studyGroup.input(microShell.getParentTerminal().getScanner());
//                    return new AddIfMinCommand(microShell, studyGroup);
//                } catch (EOFException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if ("shuffle".equals(args[0]) && args.length == 1) return new ShuffleCommand(microShell);
//            if ("filter_starts_with_name".equals(args[0]) && args.length == 2)
//                return new FilterStartsWithNameCommand(microShell, args[1]);
//            if ("filter_less_than_students_count".equals(args[0]) && args.length == 2)
//                return new FilterLessThanStudentsCountCommand(microShell, Integer.parseInt(args[1]));
//            if ("print_descending".equals(args[0]) && args.length == 1) return new PrintDescendingCommand(microShell);
//
//        } catch (Exception ignored) {
//
//        }
//        System.out.println(Phrases.CantBuildCommand);
//        return null;
//    }
//
//    public static ArrayList<BaseCommand> parseScriptCommandsOld(MicroShell microShell, ArrayList<String> lines) {
//        if (lines == null) return null;
//        ArrayList<BaseCommand> ans = new ArrayList<>();
//        try {
//
//            for (int i = 0; i < lines.size(); ++i) {
//                String[] args = lines.get(i).split(" ");
//                if (args.length == 0) continue;
//                if ("help".equals(args[0]) && args.length == 1) ans.add(new HelpCommand());
//                if ("info".equals(args[0]) && args.length == 1) ans.add(new InfoCommand(microShell));
//                if ("show".equals(args[0]) && args.length == 1) ans.add(new ShowCommand(microShell));
//                if ("add".equals(args[0]) && args.length == 1) {
//                    StudyGroup studyGroup = new StudyGroup();
//                    if (!studyGroup.parse(lines, i + 1)) throw new RuntimeException();
//                    i += studyGroup.countLinesForParse();
//                    ans.add(new AddCommand(microShell, studyGroup));
//                    continue;
//                }
//                if (args[0].equals("update") && Integer.parseInt(args[1]) > 0 && args.length == 2) {
//                    StudyGroup studyGroup = new StudyGroup();
//                    if (!studyGroup.parse(lines, i + 1)) throw new RuntimeException();
//                    i += studyGroup.countLinesForParse();
//                    ans.add(new UpdateIDCommand(microShell, studyGroup, Integer.parseInt(args[1])));
//                    continue;
//                }
//                if (args[0].equals("remove_by_id") && args.length == 2)
//                    ans.add(new RemoveByIDCommand(microShell, Integer.parseInt(args[1])));
//
//                if ("clear".equals(args[0]) && args.length == 1) ans.add(new ClearCommand(microShell));
//                if ("save".equals(args[0]) && args.length == 1) ans.add(new SaveCommand(microShell));
//                if ("execute_script".equals(args[0]) && args.length == 2)
//                    ans.add(new ExecuteScriptCommand(microShell, args[1]));
//                if ("exit".equals(args[0]) && args.length == 1) ans.add(new ExitCommand());
//                if ("remove_first".equals(args[0]) && args.length == 1) ans.add(new RemoveFirstCommand(microShell));
//                if ("add_if_min".equals(args[0]) && args.length == 1) {
//                    StudyGroup studyGroup = new StudyGroup();
//                    if (!studyGroup.parse(lines, i + 1)) throw new RuntimeException();
//                    i += studyGroup.countLinesForParse();
//                    ans.add(new AddIfMinCommand(microShell, studyGroup));
//                    continue;
//                }
//                if ("shuffle".equals(args[0]) && args.length == 1) ans.add(new ShuffleCommand(microShell));
//                if ("filter_starts_with_name".equals(args[0]) && args.length == 2)
//                    ans.add(new FilterStartsWithNameCommand(microShell, args[1]));
//                if ("filter_less_than_students_count".equals(args[0]) && args.length == 2)
//                    ans.add(new FilterLessThanStudentsCountCommand(microShell, Integer.parseInt(args[1])));
//                if ("print_descending".equals(args[0]) && args.length == 1)
//                    ans.add(new PrintDescendingCommand(microShell));
//            }
//            return ans;
//        } catch (Exception ignored) {
//        }
//        System.out.println(Phrases.CantBuildCommand);
//        return null;
//    }

}
