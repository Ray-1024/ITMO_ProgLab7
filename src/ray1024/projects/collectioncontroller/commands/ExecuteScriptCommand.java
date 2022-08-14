package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.terminal.MicroShell;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Команда запускающая на исполнение скрипт
 * Максимальный размер скрипта: 102400 символов
 * Команды в скрипте указываются в том же порядке, что и в интерактивном режиме
 * При ошибке парсинга скрипта, он не будет выполнен, ни единой команды
 */
public class ExecuteScriptCommand extends BaseCommand {
    String scriptFilename = null;

    public ExecuteScriptCommand(MicroShell _parentShell, String scriptFilename) {
        super(_parentShell);
        this.scriptFilename = scriptFilename;
    }

    @Override
    public void execute() {
        if (scriptFilename == null || parentShell == null || parentShell.getParentTerminal() == null) return;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(scriptFilename));
            StringBuilder stringBuilder = new StringBuilder();

            char[] buff = new char[1024];
            int len = 0, cnt = 0;
            do {
                len = inputStreamReader.read(buff);
                stringBuilder.append(new String(buff, 0, len));
                ++cnt;
                if (cnt > 99) throw new IllegalStateException();
            } while (len == 1024);
            inputStreamReader.close();
            String[] ll = stringBuilder.toString().split("\n");
            ArrayList<String> lines = new ArrayList<>(ll.length);
            lines.addAll(Arrays.asList(ll));
            ArrayList<BaseCommand> forExecute = CommandBuilder.parseScriptCommands(parentShell, lines);
            parentShell.getParentTerminal().addAndStartMicroShell(forExecute);
        } catch (IllegalStateException ex) {
            System.out.println(Phrases.TooLongScript);
        } catch (Exception ignored) {
            System.out.println(Phrases.CantAccess);
        }
    }
}
