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
        for (Skill newSkill : newSkills) {
            if (skills.containsKey(newSkill.getName())) {
                System.out.printf("%s не получит новое умение %s, которое имеет %s, потому что он уже имеет его",
                        person.getName(), newSkill.toString(), getName());
                continue;
            }
            person.addSkills(newSkill);
            person.teachers.put(newSkill, this);
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