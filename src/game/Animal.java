package game;

public class Animal extends Person {
    Animal(FIO _name) {
        super(_name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Animal && getName() == ((Animal) obj).getName();
    }
}