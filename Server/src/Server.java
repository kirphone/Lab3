import control.CollectionManager;
import control.CommandHandler;

import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
            byte[] ib = new byte[4096];

            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.receive(ip);

            ResponseThread thread = new ResponseThread(ib, datagramSocket, ip);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int port;
        while (true) {
            System.out.println("Введите порт для запуска сервера");
            try {
                port = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.println("Необходимо ввести число, задающее существующий порт");
            }
        }
        try{
          Server server = new Server(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
