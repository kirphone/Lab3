package game;

import game.skills.*;

import java.util.*;

public abstract class Person {
    private FIO name;
    protected MutableDouble speed;
    private Location currentLoc;
    private Journey.SmartMap skills;
    private MutableDouble points;
    protected Map<Skill, Person> teachers;

    Person(FIO _name) {
        name = _name;
        skills = new Journey.SmartMap();
        teachers = new HashMap<>();
        speed = new MutableDouble(1.0d);
        points = new MutableDouble(100.0d);
    }

    Person(FIO _name, Location _startPosition) {
        this(_name);
        currentLoc = _startPosition;
    }

    public void doSkill(String skillName) {
        if (skills.containsKey(skillName)) {
            skills.get(skillName).perform();
        } else{
            throw new SkillNotFound(this, skillName);
        }
    }

    public FIO getName() {
        return name;
    }

    public double getSpeed() {
        return speed.getValue();
    }

    public void stop() {
        speed.setValue(0.0d);
    }

    public void goToFriend(Person friend) {
        currentLoc = friend.currentLoc;
        System.out.println(name + " теперь проводит время вместе с " + friend.getName().toString());
    }

    public void goToPlace(Location loc) {
        currentLoc = loc;
        System.out.println(name + " теперь находится в месте " + loc.toString());
    }

    public void think(String text){
        System.out.printf("%s подумал: %s\n", this.getName().toString(), text);
    }

    public void addSkills(Skill... newSkills) {
        ArrayList<Skill> operands = new ArrayList<>();
        Collections.addAll(operands, newSkills);
        addSkills(operands);
    }

    public void addSkills(ArrayList<Skill> newSkills) {
        for (Skill i : newSkills) {
            if (skills.containsKey(i.getName())) {
                System.out.printf("%s не получит новое умение %s, потому что он уже имеет его",
                        this.getName(), i.toString());
                continue;
            }
            skills.put(i);
            skills.get(i.getName()).setSubject(this);
            System.out.printf("%s получил новое умение - %s\n", this.getName().toString(), i.toString());
        }
    }

    public void teachSkills(Person person, Skill... newSkills) {
        person.addSkills(newSkills);
        for (Skill i : newSkills) {
            person.teachers.put(i, this);
        }
    }

    public void sayTo(Person p, String message) {
        System.out.printf("%s сказал %s : %s\n", this.name.toString(), p.name.toString(), message);
    }

    public void changeName(FIO _name) {
        name = _name;
    }

    public Skill getSkill(String name){
        return skills.get(name);
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}