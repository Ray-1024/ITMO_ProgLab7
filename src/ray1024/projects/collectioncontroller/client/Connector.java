package ray1024.projects.collectioncontroller.client;

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

public class Connector implements IConnector {
    private Socket socket;
    private SocketChannel socketChannel;
    private ByteBuffer sizeBuffer, objectBuffer;

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
            throw new RuntimeException("SOCKET_CONNECTOR_BLOCKING_ERROR");
        }
        sizeBuffer = ByteBuffer.allocate(4);
        objectSize = -1;
    }

    public Connector(Socket socket) throws IOException {
        this.socket = socket;
        socketChannel = socket.getChannel();
        socketChannel.configureBlocking(false);
        sizeBuffer = ByteBuffer.allocate(4);
        objectSize = -1;
        sizeBuffer.position(0);
    }

    @Override
    public IConnector sendRequestToServer(IRequest request) {
        try {
            byte[] buff = Serializer.serialize(request);
            objectSize = buff.length;
            socketChannel.write(sizeBuffer.putInt(objectSize));
            socketChannel.write(ByteBuffer.wrap(buff));
            sizeBuffer.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public IRequest receiveRequestFromClient() {
        try {
            if (sizeBuffer.position() == sizeBuffer.capacity()) {
                if (objectSize == -1) {
                    objectSize = sizeBuffer.getInt();
                    if (objectBuffer == null || objectSize > objectBuffer.capacity())
                        objectBuffer = ByteBuffer.allocate(objectSize);
                }
                if (objectBuffer.position() < objectBuffer.capacity()) socketChannel.read(objectBuffer);
                if (objectBuffer.position() == objectBuffer.capacity()) {
                    try {
                        return (IRequest) Serializer.deserialize(objectBuffer.array());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            } else {
                socketChannel.read(sizeBuffer);
                if (objectBuffer != null) objectBuffer.clear();
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IResponse receiveResponseFromServer() {
        try {
            if (sizeBuffer.position() == sizeBuffer.capacity()) {
                if (objectSize == -1) {
                    objectSize = sizeBuffer.getInt();
                    if (objectBuffer == null || objectSize > objectBuffer.capacity())
                        objectBuffer = ByteBuffer.allocate(objectSize);
                }
                if (objectBuffer.position() < objectBuffer.capacity()) socketChannel.read(objectBuffer);
                if (objectBuffer.position() == objectBuffer.capacity()) {
                    try {
                        return (IResponse) Serializer.deserialize(objectBuffer.array());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            } else {
                socketChannel.read(sizeBuffer);
                objectBuffer.clear();
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
