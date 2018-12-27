package game;

import game.skills.Changeable;
import game.skills.MessageGenerator;
import game.skills.Skill;
import java.util.*;

class Journey {

    private Person pyatachok, kenga, robin, kroshka, puch;
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

    private class Initializator{
        void initialize(){
            //Открытие и закрытие рта
            class SmartMap extends HashMap<String, Skill>{
                void put(Skill skill){
                    super.put(skill.getName(), skill);
                }
            }
            SmartMap skills = new SmartMap();
            Changeable<Boolean, Boolean> openAndCloseMouthChanges = ((isOpen, change) -> isOpen ^= change);
            MessageGenerator<Boolean, Boolean> openAndCloseMouthMessage = (p, before, changes) -> String.format("%s %s рот",
                    p.getName().toString(), before ? "Закрыл" : "Открыл");
            skills.put(new Skill<>("openAndCloseMouth", new Boolean(false),
                    openAndCloseMouthChanges, true, openAndCloseMouthMessage, pyatachok));
            // Бег
            Changeable<Double, Double> runChanges = ((speed, mult) -> speed *= mult);
            MessageGenerator<Double, Double> runMessage = (p, before, ch) -> String.format("Никогда в жизни %s не бегал так быстро, как сейчас! " +
                            "Он несся, не останавливаясь ни на секунду. Лишь в сотне шагов от дома он прекратил бег.",
                    p.getName().toString());
            inits.put("run", new Pair<>(runChanges, runMessage));
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
            inits.put("addColors", new Pair<>(addColorsChanges, addColorsMessage));
            // Катиться
            Changeable<Double, Double> rollChanges = ((speed, add) -> speed += add);
            inits.put("roll", new Pair().addChanges(rollChanges));
            //прыгать
            Changeable<Double, Double> jumpChanges = ((speed, add) -> speed *= Math.pow(add, 2));
            inits.put("jump", new Pair().addChanges(jumpChanges));
        }

        <T1, T2> Changeable<T1, T2> getChanges(String name){
            return inits.get(name).changes;
        }

        <T1, T2> MessageGenerator<T1, T2> getMessage(String name){
            return inits.get(name).message;
        }
    }

    private static class Pair<T1, T2>{
        Changeable<T1, T2> changes;
        MessageGenerator<T1, T2> message;
        Skill skill;
        Pair(){ }
        Pair(Changeable<T1, T2> _changes, MessageGenerator<T1, T2> _message){
            changes = _changes;
            message = _message;
        }

        Pair addChanges(Changeable<T1, T2> _changes){
            changes = _changes;
            return this;
        }

        Pair addMessage(MessageGenerator<T1, T2> _message){
            message = _message;
            return this;
        }
    }

    private void addSkills(){
        Initializator init = new Initializator();
        init.initialize();
        ArrayList<Skill> addSkills = new ArrayList<>();


        pyatachok.addSkills(new Skill<Double, Double>("run", pyatachok.speed, init.getChanges("run"),
                10.0d, init.getMessage("run"), pyatachok));

        ArrayList<Color> allColors = new ArrayList<>(Arrays.asList(Color.values()));
        allColors.remove(0);
        pyatachok.addSkills(new Skill<HashSet<Color>, ArrayList<Color>>("addColors", new HashSet<Color>(),
                skillsActions.get("addColors"), pyatachokColors, specialMessage, pyatachok));

        pyatachok.addSkills(new Skill<Double, Double>("roll", pyatachok.speed, skillsActions.get("roll"), 10.0d,
                (p, ch) -> String.format("%s покатился по земле", pyatachok.getName().toString()), pyatachok));
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