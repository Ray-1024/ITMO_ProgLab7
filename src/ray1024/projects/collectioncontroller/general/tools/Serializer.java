package ray1024.projects.collectioncontroller.general.tools;

import java.io.*;
import java.util.Arrays;

public class Serializer {
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            /*System.out.println("---SERIALIZATION---");
            System.out.println(Arrays.toString(boas.toByteArray()));
            System.out.println("-------------------");*/
            return boas.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        InputStream is = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        }
    }
}
