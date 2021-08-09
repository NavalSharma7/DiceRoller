package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DiceRollActivity extends AppCompatActivity {


    //private variables

    private  Dice mDice;
    RadioGroup rollChoiceGroup;
    TextView rolledValue1,rolledValue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);
        if(getIntent()==null)
            return;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mDice = extras.getParcelable("tag_roll_dice");
        }

        init();
        initListeners();
    }



    //private methods

    private void initListeners(){
        findViewById(R.id.btn_roll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDice==null)
                    return;

                mDice.roll();
                int currentSideUp = mDice.getCurrentSideUp();
                rolledValue1.setVisibility(View.VISIBLE);
                rolledValue1.setText(String.format("%s",currentSideUp));
                // decide if you want to show value 2


                //roll the dice again if user has chosen for two rolls
                mDice.roll();
                int sideUp = mDice.getCurrentSideUp();
                rolledValue2.setVisibility(View.VISIBLE);
                rolledValue2.setText(String.format("%s",sideUp));

            }
        });
    }
    private void init(){
            rollChoiceGroup  = findViewById(R.id.rg_dice_rolls);
            rolledValue1 = findViewById(R.id.tv_value_first_roll);
            rolledValue2 = findViewById(R.id.tv_value_second_roll);
            TextView label = findViewById(R.id.tv_label_dice_value);
            if(mDice==null)
                return;
            label.setText("You have chosen a"+ mDice.getNumOfSides() + "sided Dice");

    }
}