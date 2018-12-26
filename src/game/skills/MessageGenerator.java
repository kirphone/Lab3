package game.skills;

@FunctionalInterface
public interface MessageGenerator<T> {
    public String generate(T changes);
}
