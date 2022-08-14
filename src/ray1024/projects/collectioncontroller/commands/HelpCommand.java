package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Выводит справку о доступных командах
 * {element} означает что в интерактивном режиме по каждому полю объекта вы будете опрошены
 * в скрипте все поля должны быть указаны начиная со следующей строки за командой, по 1 аргументу в строке
 */
public class HelpCommand extends BaseCommand {
    public HelpCommand(Terminal terminal) {
        this.setParentTerminal(getParentTerminal()).setName("help").setDescription(Phrases.getPhrase("HelpCommandDescription"));
        CommandBuilder.registerCommand(this);
    }

    @Override
    public void execute() throws RuntimeException {
        try {
            final int[] strNumber = {1};
            CommandBuilder.getRegisteredCommandsStream().forEach((command) -> {
                getParentTerminal().getOutputter().writeLine(String.format("%d. %s: %s", strNumber[0], command.getName(), command.getDescription()));
                ++strNumber[0];
            });
        } catch (Throwable throwable) {
            throw new RuntimeException(Phrases.getPhrase("Can'tExecuteCommand"));
        }
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
    }

    @Override
    public String getStepDescription() {
        return "";
    }
}
