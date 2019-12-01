package Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.rmi.server.ExportException;

public class MyUDPClient {
    InetSocketAddress address;

    public MyUDPClient(String ipAddress, int port){
        address = new InetSocketAddress(ipAddress, port);
    }

    public void send(String message) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        int bytesSent = channel.send(ByteBuffer.wrap(message.getBytes()), address);
        System.out.println("Sent: " + bytesSent + " bytes of data");
    }
}

