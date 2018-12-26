package game;

public class Man extends Person {
    Man(FIO _name) {
        super(_name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Man && getName() == ((Man) obj).getName();
    }
}