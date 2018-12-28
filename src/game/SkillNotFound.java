package game;

class SkillNotFound extends RuntimeException{
    public SkillNotFound(Person p, String skillname){
        super(String.format("У %s нет скилла под названием %s", p.getName().toString(), skillname));
    }
}
