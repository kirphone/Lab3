package game;

public class MutableDouble {
    private double value;

    public MutableDouble(double _value) {
        value = _value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double _value) {
        value = _value;
    }
}
