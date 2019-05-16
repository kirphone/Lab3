import control.CollectionManager;
import control.CommandHandler;
import game.Person;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseThread extends Thread {

    private CommandHandler handler;
    private int port;
    private InetAddress address;
    private DatagramSocket datagramSocket;
    private DatagramPacket inDatagramPacket;

    public ResponseThread(DatagramSocket _datagramSocket, DatagramPacket datagramPacket, CollectionManager manager){
        handler = new CommandHandler(manager);
        datagramSocket = _datagramSocket;
    }

    public void run(){

        String actions = handler.doCommand(new String(inDatagramPacket.getData()));


        DatagramPacket outDatagramPacket = new DatagramPacket(new byte[4096], 4096,
                inDatagramPacket.getAddress(), inDatagramPacket.getPort());
        try {
            datagramSocket.send(outDatagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}