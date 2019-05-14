import control.CollectionManager;
import control.CommandHandler;

import java.io.IOException;
import java.net.*;

public class Server {

    private DatagramSocket datagramSocket;

    public Server(int port) throws IOException{
        datagramSocket = new DatagramSocket();
        datagramSocket.connect(InetAddress.getLocalHost(), port);

        System.out.println("Сервер запущен");
        System.out.println("IP: " + datagramSocket.getLocalAddress());
    }

    private void listen() throws IOException{
        while(true){
            byte[] ib = new byte[256];

            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.receive(ip);

        }
    }


}
