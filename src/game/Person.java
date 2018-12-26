package game;

import game.skills.*;

import java.util.*;

abstract class Person {
    private final FIO name;
    protected double speed;
    private Location currentLoc;
    private Map<String, Skill> skills;
    protected Map<Skill, Person> teachers;

    Person(FIO _name) {
        name = _name;
        skills = new HashMap<>();
        teachers = new HashMap<>();
    }

    public void doSkill(String skillName){
        if(skills.containsKey(skillName)){
            skills.get(skillName).perform();
        }
    }

    public FIO getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public void goToFriend(Person friend) {
        currentLoc = friend.currentLoc;
        System.out.println(name + " теперь проводит время вместе с " + friend.getName().toString());
    }

    public void goToPlace(Location loc) {
        currentLoc = loc;
        System.out.println(name + " теперь находится в месте " + loc.toString());
    }

    public void addSkills(Skill ... newSkills) {
        for(Skill i : newSkills){
            skills.put(i.getName(), i);
        }
    }

    public void teachSkills(Person person, Skill ... newSkills) {
        for (int i = 0; i < newSkills.length; ++i) {
            if(skills.containsKey(newSkills[i].getName())){
                System.out.printf("%s не получит новое умение %s, которое имеет %s, потому что он уже имеет его",
                        person.getName(), newSkills[i].toString(), getName());
                continue;
            }
            person.addSkills(newSkills[i]);
            person.teachers.put(newSkills[i], this);
        }
    }

    @Override
    public String toString(){
        return name.toString();
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}