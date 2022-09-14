package ray1024.projects.collectioncontroller.general.communication;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import ray1024.projects.collectioncontroller.general.interfaces.IConnector;
import ray1024.projects.collectioncontroller.general.interfaces.IRequest;
import ray1024.projects.collectioncontroller.general.interfaces.IResponse;
import ray1024.projects.collectioncontroller.general.tools.Serializer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Connector implements IConnector {
    private final Socket socket;
    private final SocketChannel socketChannel;
    private final ByteBuffer sizeBuffer;
    private ByteBuffer objectBuffer;

    private int objectSize;

    public Connector(InetAddress serverAddress, int port) {
        try {
            socket = SocketChannel.open().socket();
            socket.connect(new InetSocketAddress(serverAddress, port));
        } catch (IOException e) {
            throw new RuntimeException("SOCKET_CONNECTOR_ERROR");
        }
        socketChannel = socket.getChannel();
        try {
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException("CONNECTOR_SOCKET_SET_BLOCKING_ERROR");
        }
        sizeBuffer = ByteBuffer.allocate(4);
        objectSize = -1;
        sizeBuffer.clear();
    }

    public Connector(Socket socket) {
        this.socket = socket;
        socketChannel = socket.getChannel();
        try {
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException("CONNECTOR_SOCKET_SET_BLOCKING_ERROR");
        }
        sizeBuffer = ByteBuffer.allocate(4);
        objectSize = -1;
        sizeBuffer.clear();
    }

    @Override
    public IConnector sendRequestToServer(IRequest request) {
        try {
            byte[] buff = Serializer.serialize(request);
            objectSize = buff.length;
            sizeBuffer.clear();
            sizeBuffer.putInt(objectSize);

            /*System.out.println("---SIZE_REQUEST---");
            System.out.println("SIZE: " + objectSize);
            System.out.println(Arrays.toString(sizeBuffer.array()));
            System.out.println(Arrays.toString(buff));
            System.out.println("------------------");*/

            ByteBuffer byteBuffer = ByteBuffer.wrap(buff);
            sizeBuffer.clear();
            socketChannel.write(sizeBuffer);
            socketChannel.write(byteBuffer);
            sizeBuffer.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public IRequest receiveRequestFromClient() {
        try {
            if (sizeBuffer.remaining() == 0) {
                if (objectSize == -1) {
                    sizeBuffer.clear();
                    objectSize = sizeBuffer.getInt();
                    /*System.out.println("---REQUEST_SIZE---");
                    System.out.println(objectSize);
                    System.out.println("------------------");*/
                    if (objectSize > 0 && (objectBuffer == null || objectSize > objectBuffer.capacity()))
                        objectBuffer = ByteBuffer.allocate(objectSize);
                }
                if (objectBuffer.remaining() > 0) socketChannel.read(objectBuffer);
                if (objectBuffer.remaining() == 0) {
                    try {
                        sizeBuffer.clear();
                        objectSize = -1;
                        objectBuffer.clear();
                        return (IRequest) Serializer.deserialize(objectBuffer.array());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                socketChannel.read(sizeBuffer);
                if (objectBuffer != null) objectBuffer.clear();
                objectSize = -1;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public IResponse receiveResponseFromServer() {
        return null;
    }
}
