package Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MyUDPServer implements Runnable{
    InetSocketAddress listenAddress;
    private IRequestManager requestManager;
    DatagramChannel channel;

    public MyUDPServer(int port, IRequestManager requestManager){
        listenAddress = new InetSocketAddress(port);
        this.requestManager = requestManager;
    }

    public void init() throws IOException {
        channel = DatagramChannel.open();
        channel.socket().bind(listenAddress);
        System.out.println("Server listening for UDP on port " + listenAddress.getPort());
    }

    public void run() {
        try{
            while(true){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                channel.receive(buffer);

                buffer.flip();
                byte[] buff = new byte[1024];
                buffer.get(buff, 0, buffer.limit());
                String result = new String(buff);
                requestManager.receivedRequest(result);

                System.out.println("Port " + listenAddress.getPort() + " received: " + result);
            }
        }
        catch(Exception e){
             System.out.println(e);
        }

    }
}
