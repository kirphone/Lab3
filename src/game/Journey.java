package game;

import game.skills.Changeable;
import game.skills.MessageGenerator;
import game.skills.Skill;
import java.util.*;

class Journey {

    private Map<String, Changeable> skillsActions;
    private Person pyatachok, kenga, robin, kroshka, puch;
    public Journey() {
        initializePersons();
        initializeSkillsActions();
        initializeSkills();
    }

    public void start() {
        pyatachok.doSkill("run");
        pyatachok.doSkill("roll");
        pyatachok.goToPlace(Location.PYATACHOKHOME);
    }

    private void initializePersons(){
        pyatachok = new Animal(new FIO("Генри-Пушель", "Пяточок"));
    }

    private void initializeSkillsActions() {
        skillsActions = new HashMap<>();
        // Бег
        Changeable<Double, Double> runChanges = ((speed, mult) -> speed *= mult);
        skillsActions.put("run", runChanges);
        // Добавление цветов
        Changeable<HashSet<Color>, ArrayList<Color>> addColors = ((before, changes) -> {
            before.addAll(changes);
            changes.clear();
        });
        skillsActions.put("addColors", addColors);
        // Катиться
        Changeable<Double, Double> rollChanges = ((speed, add) -> speed += add);
        skillsActions.put("roll", rollChanges);
        //прыгать
        Changeable<Double, Double> jumpChanges = ((speed, add) -> speed *= Math.pow(add, 2));
        skillsActions.put("jump", jumpChanges);
        
    }

    private void initializeSkills(){
        pyatachok.addSkills(new Skill<Double, Double>("run", pyatachok.speed, skillsActions.get("run"), 10.0d,
                (ch) -> String.format("Никогда в жизни %s не бегал так быстро, как сейчас! " +
                        "Он несся, не останавливаясь ни на секунду. Лишь в сотне шагов от дома он прекратил бег.",
                        pyatachok.getName().toString())));

        ArrayList<Color> pyatachokColors = new ArrayList<>();
        pyatachokColors = new ArrayList<Color>(Arrays.asList(Color.values()));
        pyatachokColors.remove(0);

        MessageGenerator<ArrayList<Color> > specialMessage = (changes) -> {
            StringBuilder result = new StringBuilder();
            for(Color i : changes){
                result.append(" ").append(i.toString());
            }
            return result.toString();
        };

        pyatachok.addSkills(new Skill<HashSet<Color>, ArrayList<Color>>("changeColor", new HashSet<Color>(),
                skillsActions.get("changeColor"), pyatachokColors, specialMessage));

        pyatachok.addSkills(new Skill<Double, Double>("roll", pyatachok.speed, skillsActions.get("roll"), 10.0d,
                (ch) -> String.format("%s покатился по земле", pyatachok.getName().toString())));
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