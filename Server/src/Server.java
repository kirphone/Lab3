import control.CollectionManager;
import control.CommandHandler;

public class Server {

    CommandHandler handler;

    public Server(){
        handler = new CommandHandler(new CollectionManager());
    }
}
