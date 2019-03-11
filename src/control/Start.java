package control;

import java.io.File;

public class Start {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Путь до файла должен задаваться с помощью аргумента командной строки");
        }
        else {
            CommandHandler handler = new CommandHandler(new CollectionManager(new File(args[0])));
            handler.control();
        }
    }
}