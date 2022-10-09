package ray1024.projects.collectioncontroller.general.tools;

import java.io.*;

public class Serializer {
    public static byte[] serialize(Object obj) {
        try (ByteArrayOutputStream boas = new ByteArrayOutputStream(); ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            return boas.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }

    public static Object deserialize(byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        } catch (Throwable ex) {
            return null;
        }
    }
}
