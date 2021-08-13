package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DiceRollActivity extends AppCompatActivity {


    //private variables

    private Dice mDice;
    RadioGroup rollChoiceGroup;
    TextView rolledValue1, rolledValue2;
    LinearLayout secondValLayout;
    boolean twoRollsSelected = false;
    HashMap<Integer, ArrayList<Integer>> mDiceRollInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);
        if (getIntent() == null)
            return;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mDice = extras.getParcelable("tag_roll_dice");
        }
        init();
        initListeners();
    }


    //private methods

    private void initListeners() {
        findViewById(R.id.btn_roll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRoll();
            }
        });


    }

    private void init() {
        rollChoiceGroup = findViewById(R.id.rg_dice_rolls);
        rolledValue1 = findViewById(R.id.tv_value_first_roll);
        rolledValue2 = findViewById(R.id.tv_value_second_roll);
        secondValLayout = findViewById(R.id.ll_second_roll_value);
        TextView label = findViewById(R.id.tv_label_dice_value);
        if (mDice == null)
            return;
        label.setText(String.format("You have chosen a %s Dice", mDice.getType()));
        // change the visibility of the view depending on the radio button choice.
        rollChoiceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.rb_roll_twice) {
                    secondValLayout.setVisibility(View.VISIBLE);
                    twoRollsSelected = true;

                } else {
                    secondValLayout.setVisibility(View.GONE);
                    twoRollsSelected = false;
                }
            }
        });

        //load history of rolls to add to
        loadHistory();
    }

    private void onClickRoll() {
        if (mDice == null)
            return;

        mDice.roll();
        int currentSideUp = mDice.getCurrentSideUp();
        saveRollToHistory(currentSideUp);
        rolledValue1.setText(String.format("%s", currentSideUp));
        // decide if you want to show value 2
        if (twoRollsSelected) {

            //roll the dice again if user has chosen for two rolls
            mDice.roll();

            int sideUp = mDice.getCurrentSideUp();
            saveRollToHistory(sideUp);
            rolledValue2.setText(String.format("%s", sideUp));
        }
    }

    private void saveRollToHistory(int sideUp) {

        int numSides = mDice.getNumOfSides();
        ArrayList<Integer> rollValues;


        if(mDiceRollInfoList==null)
            return;
        //check if the dice exist in history
        for (Map.Entry<Integer, ArrayList<Integer>> item : mDiceRollInfoList.entrySet()) {
            if(numSides == item.getKey()){
                rollValues = item.getValue();
                rollValues.add(sideUp);
                mDiceRollInfoList.put(numSides,rollValues);
                setList("roll_info", mDiceRollInfoList);
                return;
            }
        }
        //other wise add as first entry for this dice
        rollValues = new ArrayList<>();
        rollValues.add(sideUp);
        mDiceRollInfoList.put(numSides,rollValues);
        setList("roll_info", mDiceRollInfoList);
        }

    private void loadHistory() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("roll_info", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<HashMap<Integer,ArrayList<Integer>>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        mDiceRollInfoList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (mDiceRollInfoList == null) {
            // if the array list is empty
            // creating a new array list.
            mDiceRollInfoList = new HashMap<Integer,ArrayList<Integer>>();
        }
    }


    public <T> void setList(String key, HashMap<Integer,ArrayList<Integer>> list) {
        // set the list as hashmap in shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
        editor.commit();
    }
}