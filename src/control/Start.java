package control;

import game.Journey;
import game.ParametersNullException;

public class Start {
    public static void main(String[] args) {
        Journey game = new Journey();
        try {
            game.start();
        } catch (ParametersNullException e) {
            e.printStackTrace();
        }
    }
}