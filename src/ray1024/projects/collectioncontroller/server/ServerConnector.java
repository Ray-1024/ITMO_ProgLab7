package ray1024.projects.collectioncontroller.server;


import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IRequest;
import ray1024.projects.collectioncontroller.general.communication.IResponse;
import ray1024.projects.collectioncontroller.general.tools.Serializer;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerConnector implements IConnector {
    private final Socket socket;
    private final SocketChannel socketChannel;
    private final ByteBuffer sizeBufferIn;
    private ByteBuffer objectBufferIn;
    private final ByteBuffer sizeBufferOut;
    private ByteBuffer objectBufferOut;
    private int objectSizeIn;
    private int objectSizeOut;

    public ServerConnector(Socket socket) {
        this.socket = socket;
        socketChannel = socket.getChannel();
        try {
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException("CONNECTOR_SOCKET_SET_BLOCKING_ERROR");
        }
        sizeBufferIn = ByteBuffer.allocate(4).clear();
        objectSizeIn = -1;
        sizeBufferOut = ByteBuffer.allocate(4).clear();
        objectSizeOut = -1;
    }

    @Override
    public IConnector sendRequest(IRequest request) {
        return this;
    }

    @Override
    public IRequest receiveRequest() {
        try {
            if (sizeBufferIn.remaining() == 0) {
                if (objectSizeIn == -1) {
                    sizeBufferIn.clear();
                    objectSizeIn = sizeBufferIn.getInt();
                    if (objectSizeIn > 0)
                        objectBufferIn = ByteBuffer.allocate(objectSizeIn);
                    objectBufferIn.clear();
                }
                if (objectBufferIn.remaining() > 0) socketChannel.read(objectBufferIn);
                if (objectBufferIn.remaining() == 0) {
                    try {
                        sizeBufferIn.clear();
                        objectSizeIn = -1;
                        objectBufferIn.clear();
                        byte[] arr = objectBufferIn.array();
                        return (IRequest) Serializer.deserialize(objectBufferIn.array());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                socketChannel.read(sizeBufferIn);
                if (objectBufferIn != null) objectBufferIn.clear();
                objectSizeIn = -1;
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
            objectSizeOut = buff.length;
            sizeBufferOut.clear();
            sizeBufferOut.putInt(objectSizeOut);
            ByteBuffer byteBuffer = ByteBuffer.wrap(buff);
            sizeBufferOut.clear();
            socketChannel.write(sizeBufferOut);
            socketChannel.write(byteBuffer);
        } catch (IOException ignored) {
        }
        return this;
    }
}
