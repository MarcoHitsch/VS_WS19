package Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class MyUDPServer {
    InetSocketAddress address;
    DatagramChannel channel;

    public MyUDPServer(int port){
        address = new InetSocketAddress(port);
    }

    public void init() throws IOException {
        channel = DatagramChannel.open();
        channel.socket().bind(address);
    }

    public void run() throws IOException {
        while(true){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            channel.receive(buffer);

            buffer.flip();
            byte[] buff = new byte[1024];
            buffer.get(buff, 0, buffer.limit());
            String result = new String(buff);

            System.out.println("Received: " + result);
        }
    }
}
