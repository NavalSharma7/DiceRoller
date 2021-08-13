package com.example.diceroller;

import java.util.ArrayList;

public class DataModel {

    public static ArrayList<Dice> getDiceList() {
// default dices values to get started with on the main screen
        ArrayList<Dice> dices = new ArrayList<>();
            dices.add(new Dice(4));
            dices.add(new Dice(6));
            dices.add(new Dice(8));
            dices.add(new Dice(10));
            dices.add(new Dice(12));
            dices.add(new Dice(20));

        return dices;
    }
}
