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

    public void setTarget(T1 _target){
        target = _target;
    }

    public String getName(){
        return name;
    }

    public T1 getTarget(){
        return target;
    }

    public void setSpecialTextMessage(MessageGenerator message){
        specialTextMessage = message;
    }

    private boolean printSpecialTextMessage(){
        String message = specialTextMessage.generate(changes);
        System.out.println(message);
        return message.equals("");
    }

    public void setAction(Changeable<T1, T2> _action){
        action = _action;
    }

    public void setChanges(T2 _changes){
        changes = _changes;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}