package game;

import game.skills.Changeable;
import game.skills.MessageGenerator;
import game.skills.Skill;

import java.util.*;

class Journey {

    Map<String, Changeable> skillsActions;

    Person pyatachok;

    public Journey() {
        initializeSkillsActions();
        initializePersons();
        initializeSkills();
    }

    public void start() {
        pyatachok.doSkill("run");
        pyatachok.doSkill("roll");
        pyatachok.goToPlace(Location.PYATACHOKHOME);
    }

    private void initializeSkillsActions() {
        skillsActions = new HashMap();
        Changeable<Double, Double> runChanges = ((num, mult) -> (num * mult));
        skillsActions.put("run", runChanges);
        Changeable<HashSet<Color>, ArrayList<Color> > changeColor = ((before, changes) -> {
            before.addAll(changes);
            changes.clear();
            return before;
        });
        skillsActions.put("changeColor", changeColor);

        Changeable<Double, Double> rollChanges = ((num, add) -> (num + add));
        skillsActions.put("roll", rollChanges);
    }

    private void initializePersons(){
        pyatachok = new Animal(new FIO("Генри", "Пушель-Пятачок"));
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