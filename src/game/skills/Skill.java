package game.skills;

import game.Person;

public class Skill<T1, T2> {
    private T1 target;
    private T2 changes;
    private String name;
    private MessageGenerator<T1, T2> specialTextMessage;
    private Changeable<T1, T2> action;
    private Person subject;

    public Skill(String _name, T1 _target, Changeable<T1, T2> _action, T2 _changes,
                 MessageGenerator<T1, T2> _specialTextMessage, Person _subject){
        name = _name;
        target = _target;
        action = _action;
        changes = _changes;
        specialTextMessage = _specialTextMessage;
        subject = _subject;
    }

    public Skill(String _name, T1 _target, Changeable<T1, T2> _action, T2 _changes){
        this(_name, _target, _action, _changes, null, null);
    }

    public Skill(String _name, T1 _target){
        this(_name, _target, null, null, null, null);
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

    public void setSpecialTextMessage(MessageGenerator<T1, T2> message){
        specialTextMessage = message;
    }

    private void printSpecialTextMessage(){
        String message = specialTextMessage.generate(subject, target, changes);
        System.out.println(message);
    }

    public void setAction(Changeable<T1, T2> _action){
        action = _action;
    }

    public void setChanges(T2 _changes){
        changes = _changes;
    }

    public void perform(){
        if(specialTextMessage != null) {
            printSpecialTextMessage();
        }
        action.change(target, changes);
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