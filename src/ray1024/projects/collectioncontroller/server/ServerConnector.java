package ray1024.projects.collectioncontroller.server;


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

public class ServerConnector implements IConnector {
    private final Socket socket;
    private final SocketChannel socketChannel;
    private final ByteBuffer sizeBuffer;
    private ByteBuffer objectBuffer;

    private int objectSize;

    public ServerConnector(Socket socket) {
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
    public IConnector sendRequest(IRequest request) {
        return this;
    }

    @Override
    public IRequest receiveRequest() {
        try {
            if (sizeBuffer.remaining() == 0) {
                if (objectSize == -1) {
                    sizeBuffer.clear();
                    objectSize = sizeBuffer.getInt();
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
    public IResponse receiveResponse() {
        return null;
    }

    @Override
    public IConnector sendResponse(IResponse response) {
        try {
            byte[] buff = Serializer.serialize(response);
            objectSize = buff.length;
            sizeBuffer.clear();
            sizeBuffer.putInt(objectSize);
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
}
