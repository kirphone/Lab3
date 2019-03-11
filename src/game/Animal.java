package game;

public class Animal extends Person {

    public Animal(FIO _name) {
        super(_name);
    }

    public Animal(FIO _name, Location _startPosition) {
        super(_name, _startPosition);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Animal && getName() == ((Animal) obj).getName();
    }
}