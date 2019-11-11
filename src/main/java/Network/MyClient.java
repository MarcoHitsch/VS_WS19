package Network;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class MyClient {
    InetSocketAddress sendAddress;
    private Selector selector;
    String message;
    private String response;

    public String getResponse(){return response;}

    public MyClient(String ipAddress, int port){
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
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        readBuffer.clear();
        int length;
        try {
            length = channel.read(readBuffer);
        } catch (IOException e) {
            System.out.println("Reading problem, closing connection");
            key.cancel();
            channel.close();
            return "";
        }
        if (length == -1) {
            System.out.println("Nothing was read from server");
            channel.close();
            key.cancel();
            return "";
        }
        readBuffer.flip();
        byte[] buff = new byte[1024];
        readBuffer.get(buff, 0, length);
        String response = new String(buff);
        return response;
    }

    private void write(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.write(ByteBuffer.wrap(message.getBytes()));

        // Lets get ready to read.
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
