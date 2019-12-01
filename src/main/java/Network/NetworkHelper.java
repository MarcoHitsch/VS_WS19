package Network;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class NetworkHelper{
    public static ByteBuffer getBufferForMessage(String message){
        byte[] messageBytes = message.getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(messageBytes.length + 4);
        buffer.putInt(messageBytes.length);
        buffer.put(messageBytes);
        buffer.flip();

        return buffer;
    }

    public static String readMessageFromChannel(SocketChannel channel) throws Exception {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        int numRead = channel.read(lengthBuffer);

        if (numRead == -1) {
            throw new Exception("No bytes read from channel.");
        }

        lengthBuffer.flip();
        int messageLength = lengthBuffer.getInt();

        ByteBuffer messageBuffer = ByteBuffer.allocate(messageLength);
        numRead = channel.read(messageBuffer);
        messageBuffer.flip();

        byte[] buff = new byte[messageLength];
        messageBuffer.get(buff, 0, numRead);
        return new String(buff);
    }

    public static String readMessageFromChannel(DatagramChannel channel) throws Exception {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        channel.receive(lengthBuffer);

        lengthBuffer.flip();
        int messageLength = lengthBuffer.getInt();

        ByteBuffer messageBuffer = ByteBuffer.allocate(messageLength);
        channel.receive(messageBuffer);
        messageBuffer.flip();

        byte[] buff = new byte[messageLength];
        messageBuffer.get(buff, 0, messageBuffer.limit());
        return new String(buff);
    }
}
