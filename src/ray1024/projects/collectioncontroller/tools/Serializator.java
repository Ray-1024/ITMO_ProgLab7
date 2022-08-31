package ray1024.projects.collectioncontroller.tools;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializator {
    private static final ObjectOutputStream objectOutputStream;
    private static final ByteArrayOutputStream byteStream;

    static {
        try {
            byteStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteStream);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getBytes(Serializable object) {
        try {
            byteStream.reset();
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            return byteStream.toByteArray();
        } catch (Throwable e) {
            throw new RuntimeException("SERIALIZATOR_EXEPTION");
        }
    }

    public static Object getObject()
}
