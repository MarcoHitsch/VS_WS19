package Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class MyTCPServer implements Runnable {
    private InetSocketAddress listenAddress;
    private IRequestManager requestManager;
    private ServerSocketChannel serverChannel;
    private Selector selector;

    public MyTCPServer(int port, IRequestManager requestManager){
        listenAddress = new InetSocketAddress(port);
        this.requestManager = requestManager;
    }

    public void init() {
        if (selector != null) return;
        if (serverChannel != null) return;

        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(listenAddress);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server listening for TCP on port " + listenAddress.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
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
                }
                keys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        System.out.println("Closing TCP server on port " + listenAddress.getPort() + " down");
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
        ByteBuffer buffer = NetworkHelper.getBufferForMessage(response);

        channel.write(buffer);

        channel.close();
        key.cancel();
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        String result = "";
        try {
            result = NetworkHelper.readMessageFromChannel(channel);
        } catch (Exception e) {
            e.printStackTrace();
            channel.close();
            key.cancel();
        }

        requestManager.receivedRequest(result);

        key.interestOps(SelectionKey.OP_WRITE);
        System.out.println("Port " + listenAddress.getPort() + " received: " + result);
    }

    private void accept(SelectionKey key) throws Exception {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_READ);
    }
}

