package ray1024.projects.collectioncontroller.general.readers;

import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class NonBlockingConsoleSourceReader implements IInputSource {

    private static final int BUFFER_SIZE = 128;
    private final Reader reader = new InputStreamReader(System.in);

    private final StringBuilder stringBuilder = new StringBuilder();
    private String line;
    private final char[] buffer = new char[BUFFER_SIZE];
    private int enterIndex = -1, right = 0;
    private boolean eof = false;


    @Override
    public String nextLine() {
        if (eof) return "";
        if (enterIndex == -1) return "";
        line = stringBuilder.substring(0, enterIndex);
        stringBuilder.delete(0, enterIndex + 1);
        right = 0;
        enterIndex = -1;
        return line;
    }

    @Override
    public boolean hasNextLine() throws IOException {
        if (eof) return false;
        while (true) {
            int cnt = reader.read(buffer);
            if (cnt == 0) break;
            if (cnt == -1) {
                eof = true;
                break;
            }
            stringBuilder.append(buffer, 0, cnt);
        }
        if (enterIndex == -1) {
            for (enterIndex = right; enterIndex < stringBuilder.length(); ++enterIndex) {
                if (stringBuilder.charAt(enterIndex) == '\n') return true;
            }
            enterIndex = -1;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isEOF() {
        return eof;
    }
}
