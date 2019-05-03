import control.CollectionManager;
import control.CommandHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;
    Socket connectSocket;

    public Server(){
        try {
            serverSocket = new ServerSocket(8888);         // serverSocket будет принимать соединения на заданный порт
            connectSocket = serverSocket.accept();  //ожидание соединения с клиентом и получение сокета для коммуникации с клиентом.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
