package control;

public class Command {
    private String name;
    private String body;
    private int size;
    public Command(String command){
        if (command == null)
            return;
        String[] fullCommand = command.trim().split(" ", 2);
        name = fullCommand[0];
        size = fullCommand.length;
        if(fullCommand.length == 2)
            body = fullCommand[1];
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public int getSize(){
        return  size;
    }
}
