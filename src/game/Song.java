package game;

public class Song {

    private StringBuilder text;
    private final String name;

    public Song(String _name){
        this(_name, "");
    }

    public Song(String _name, String _text){
        text = new StringBuilder(_text);
        name = _name;
    }

    public void addString(String newString){
        text.append(newString);
    }

    public String getName(){
        return name;
    }

    public String getText() {return text.toString(); }
}
