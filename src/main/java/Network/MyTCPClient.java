package Network;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class MyTCPClient {
    InetSocketAddress sendAddress;
    private Selector selector;
    String message;
    private String response;

    public String getResponse(){return response;}

    public MyTCPClient(String ipAddress, int port){
        sendAddress = new InetSocketAddress(ipAddress, port);
    }

    public void send(String message)
    {
        this.message = message;
        SocketChannel channel;
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);

            channel.register(selector, SelectionKey.OP_CONNECT);
            channel.connect(sendAddress);

            boolean responseRead = false;

            while (!responseRead) {
                selector.select(1000);

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) continue;

                    if (key.isConnectable()) {
                        System.out.println("Established TCP Connection to " + sendAddress);
                        connect(key);
                    }
                    if (key.isWritable()) {
                        write(key);
                    }
                    if (key.isReadable()) {
                        this.response = read(key);
                        responseRead = true;
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            close();
        }
        System.out.println("Connection closed");
    }

    private void close()
    {
        try {
            selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();

        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        int numRead = channel.read(lengthBuffer);

        if (numRead == -1) {
            channel.close();
            key.cancel();
            return "";
        }

        lengthBuffer.flip();
        int messageLength = lengthBuffer.getInt();

        ByteBuffer messageBuffer = ByteBuffer.allocate(messageLength);
        numRead = channel.read(messageBuffer);
        messageBuffer.flip();

        byte[] buff = new byte[messageLength];
        messageBuffer.get(buff, 0, numRead);
        String result = new String(buff);
        return result;
    }

    private void write(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();
        byte[] messageBytes = message.getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(messageBytes.length + 4);
        buffer.putInt(messageBytes.length);
        buffer.put(messageBytes);
        buffer.flip();

        channel.write(buffer);

        key.interestOps(OP_READ);
    }

    private void connect(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.register(selector, OP_WRITE);
    }

}
