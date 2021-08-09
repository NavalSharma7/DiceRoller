package com.example.diceroller;

import java.util.ArrayList;

public class DataModel {

    public static ArrayList<Dice> getDiceList() {

        ArrayList<Dice> dices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Dice die = new Dice();
            die.setNumOfSides(10);
            die.setType("d10");
            dices.add(die);
        }

        return dices;
    }
}
