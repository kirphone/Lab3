package game;

import game.skills.Changeable;
import game.skills.MessageGenerator;
import game.skills.Skill;
import java.util.*;

class Journey {

    private Person pyatachok, kenga, robin, kroshka, puch;
    private SmartMap skills;
    public Journey() {
        initializePersons();
        addSkills();
    }

    public void start() {
        pyatachok.doSkill("run");
        pyatachok.doSkill("roll");
        pyatachok.doSkill("addColors");
        pyatachok.goToPlace(Location.PYATACHOKHOME);
        kenga.goToPlace(Location.FORREST);
        kroshka.goToPlace(Location.FORREST);

    }

    private void initializePersons(){
        pyatachok = new Animal(new FIO("Генри-Пушель", "Пяточок"));
        kenga = new Animal(new FIO("Кенга", ""));
        kroshka = new Animal(new FIO("Крошка", "Ру"));
        puch = new Animal(new FIO("Пух", ""));
        robin = new Man(new FIO("Кристофер", "Робин"));
    }

    static class SmartMap extends HashMap<String, Skill>{
        void put(Skill skill){
            super.put(skill.getName(), skill);
        }
        ArrayList<Skill> get(String ... names){
            ArrayList<Skill> skills = new ArrayList<>();
            for(String s : names){
                skills.add(super.get(s));
            }
            return skills;
        }
    }

    private class Initializator{
        void initialize(){
            //Открытие и закрытие рта
            skills = new SmartMap();
            Changeable<Boolean, Boolean> openAndCloseMouthChanges = ((isOpen, change) -> isOpen ^= change);
            MessageGenerator<Boolean, Boolean> openAndCloseMouthMessage = (p, before, changes) -> String.format("%s %s рот",
                    p.getName().toString(), before ? "Закрыл" : "Открыл");
            skills.put(new Skill<>("openAndCloseMouth", false,
                    openAndCloseMouthChanges, true, openAndCloseMouthMessage, pyatachok));
            // Бег
            Changeable<Double, Double> runChanges = ((speed, mult) -> speed *= mult);
            MessageGenerator<Double, Double> runMessage = (p, before, ch) -> String.format("Никогда в жизни %s не бегал так быстро, как сейчас! " +
                            "Он несся, не останавливаясь ни на секунду. Лишь в сотне шагов от дома он прекратил бег.",
                    p.getName().toString());
            skills.put(new Skill<>("run", 1.0d, runChanges, 10.0d, runMessage, pyatachok));
            // Добавление цветов
            Changeable<HashSet<Color>, ArrayList<Color>> addColorsChanges = ((before, changes) -> {
                before.addAll(changes);
                changes.clear();
            });
            MessageGenerator<HashSet<Color>, ArrayList<Color>> addColorsMessage = (p, before, changes) -> {
                StringBuilder result = new StringBuilder();
                result.append(String.format("%s обретает новые цвета :", p.getName().toString()));
                for(Color i : changes){
                    result.append(" ").append(i.toString());
                }
                return result.toString();
            };
            ArrayList<Color> allColors = new ArrayList<>(Arrays.asList(Color.values()));
            allColors.remove(0);
            skills.put(new Skill<>("addColors", new HashSet<Color>(),
                    addColorsChanges, allColors, addColorsMessage));
            // Катиться
            Changeable<Double, Double> rollChanges = ((speed, add) -> speed += add);
            skills.put(new Skill<>("roll", 1.0d, rollChanges, 10.0d,
                    (p, before, ch) -> String.format("%s покатился по земле", p.getName().toString())));
            //прыгать
            Changeable<Double, Double> jumpChanges = ((speed, add) -> speed *= Math.pow(add, 2));
            skills.put(new Skill<>("jump", 1.0d, jumpChanges, 5.0d, (p, before, ch)
                    -> String.format("%s прыгнул далеко", p.getName().toString())));
        }
    }

    private void addSkills(){
        Initializator init = new Initializator();
        init.initialize();
        pyatachok.addSkills(skills.get("openAndCloseMouth", "run", "addColors", "roll"));
    }

    @Override
    public String toString() {
        return "Это наше путешествие";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}