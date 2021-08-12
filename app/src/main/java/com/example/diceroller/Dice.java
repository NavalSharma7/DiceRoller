package com.example.diceroller;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.*;
import java.lang.*;

public class Dice implements Parcelable {
    /*
    Naval Sharma
    A00241484
		This program has a Die class which is used to create any n-sieded die,
		It includes properties that depic it's features and what side is up..
		The roll method inacts a real life roll of the die and gives out a random side..
*/


    // data fields to describe the die..

    private String type;
    private int numberOfSides;
    private int currentSideUp;

    // constructors..

    //constructor with no argument sets the numberOfSides as 6 default and the type d6
    //to that and gives a ramdom value to the currentSideUp

    public Dice() {
        this.type = "d6";
        this.numberOfSides = 6;
        this.currentSideUp = (int) (Math.random() * (6 - 1)) + 1;
    }

    //constructor with one argument sets the numberOfSides and the type accroding
    //to that and givees a ramdom value to the currentSideUp

    public Dice(int numberOfSides) {
        this.numberOfSides = numberOfSides;
        this.type = "d" + numberOfSides;
        this.currentSideUp = (int) (Math.random() * (numberOfSides - 1)) + 1;
    }

    //constructor with two argument sets the numberOfSides and the type
    //to that and gives a ramdom value to the currentSideUp..

    public Dice(String type, int numberOfSides) {
        this.type = type;
        this.numberOfSides = numberOfSides;
        this.currentSideUp = (int) (Math.random() * (numberOfSides - 1)) + 1;
    }


    //accessors and mutators methods for type, numberOfSides,currentSideUp..
    public String getType() {
        return type;
    }

    public int getNumOfSides() {
        return numberOfSides;
    }

    public int getCurrentSideUp() {
        return currentSideUp;
    }

    public void setType(String type) {
        this.type = type;
    }

    // set the type according as we set the number of sides
    public void setNumOfSides(int numberOfSides) {
        this.numberOfSides = numberOfSides;
        this.type = "d" + numberOfSides;
    }


    public void setCurrentSideUp(int currentSideUp) {
        this.currentSideUp = currentSideUp;
    }


    // the roll method which generates a ramdom value between 1 and numberOfSides
    //and assigns that as the currentSideup..
    public void roll() {
        setCurrentSideUp((int) (Math.random() * (numberOfSides - 1)) + 1);
    }


    //Parcelable methods ..

    public static final Parcelable.Creator<Dice> CREATOR = new Creator<Dice>() {
        @Override
        public Dice createFromParcel(Parcel source) {
            return new Dice(source);
        }

        @Override
        public Dice[] newArray(int size) {
            return new Dice[0];
        }
    };

    public Dice(Parcel parcel) {
        this.type = parcel.readString();
        this.numberOfSides = parcel.readInt();
        this.currentSideUp = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(numberOfSides);
        dest.writeInt(currentSideUp);
    }
}

