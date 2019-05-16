package control;

import java.io.File;

public class Start {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Путь до файла должен задаваться с помощью аргумента командной строки");
        }
        else {
            CollectionManager manager = new CollectionManager();
            if(manager.isImported()){
                CommandHandler handler = new CommandHandler(manager);
                //handler.control();
            }
        }
    }
}