package ray1024.projects.collectioncontroller.general.tools;

import java.io.*;

public class Serializer {
    //private Object serLock = new Object();
    //private Object deserLock = new Object();
    public static synchronized byte[] serialize(Object obj) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            return boas.toByteArray();
        } catch (IOException ex) {
            return new byte[0];
        }
    }

    public static synchronized Object deserialize(byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        } catch (Throwable ex) {
            return null;
        }
    }
}
