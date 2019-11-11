package Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyServer {
    private InetSocketAddress listenAddress;
    private IRequestManager requestManager;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    //private Map<SocketChannel, byte[]> dataTracking = new HashMap<>();

    public MyServer(int port, IRequestManager requestManager){
        listenAddress = new InetSocketAddress(port);
        this.requestManager = requestManager;
    }

    public void init() {
        System.out.println("initializing server");

        if (selector != null) return;
        if (serverChannel != null) return;

        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(listenAddress);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server listening on port " + listenAddress.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws  Exception {
        try{
            while(true){
                int readyCount = selector.select();
                if (readyCount == 0) { continue; }

                Set<SelectionKey> keys = selector.selectedKeys();
                for(SelectionKey key : keys){
                    if (key.isAcceptable()) {
                        this.accept(key);
                    } else if (key.isReadable()) {
                        this.read(key);
                    } else if (key.isWritable()) {
                        this.write(key);
                    }

                    keys.remove(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        System.out.println("Closing server down");
        if (selector != null) {
            try {
                selector.close();
                serverChannel.socket().close();
                serverChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();

        String response = requestManager.getResponse();
        channel.write(ByteBuffer.wrap(response.getBytes()));

        channel.close();
        key.cancel();
    }

    private void read(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int numRead = channel.read(buffer);

        if (numRead == -1) {
            channel.close();
            key.cancel();
            return;
        }

        buffer.flip();
        byte[] buff = new byte[1024];
        buffer.get(buff, 0, numRead);
        String result = new String(buff);
        requestManager.receivedRequest(result);

        key.interestOps(SelectionKey.OP_WRITE);
        System.out.println("Received: " + result);
    }

    private void accept(SelectionKey key) throws Exception {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_READ);
    }
}

