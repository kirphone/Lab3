package game.skills;

public class Skill<T1, T2> {
    private T1 target;
    private T2 changes;
    private String name;
    private MessageGenerator<T2> specialTextMessage;
    private Changeable<T1, T2> action;

    public Skill(String _name, T1 _target, Changeable<T1, T2> _action, T2 _changes, MessageGenerator _specialTextMessage){
        name = _name;
        target = _target;
        action = _action;
        changes = _changes;
        specialTextMessage = _specialTextMessage;
    }

    public Skill(String _name, T1 _target){
        this(_name, _target, null, null, (changes) -> "");
    }

    public String getName(){
        return name;
    }

    private boolean printSpecialTextMessage(){
        String message = specialTextMessage.generate(changes);
        System.out.println(message);
        return message.equals("");
    }

    public void perform(){
        action.change(target, changes);
        printSpecialTextMessage();
    }

    public String toString(){
        return "Скилл под названием " + name;
    }

    public boolean equals(Object obj){
        return obj instanceof Skill && ((Skill) obj).getName().equals(name);
    }
}