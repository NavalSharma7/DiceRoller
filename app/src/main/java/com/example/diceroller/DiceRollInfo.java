package com.example.diceroller;

import java.util.List;

public class DiceRollInfo {

    private Dice die;
    private List<Integer> values;

    public Dice getDie() {
        return die;
    }

    public void setDie(Dice die) {
        this.die = die;
    }

    public void addValue(int value){
        values.add(value);
    }


    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }
}
