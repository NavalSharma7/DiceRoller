package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomDiceDialog.OnInputListener {


    // private variables.
    private DiceAdapter mAdapter;
    private ArrayList<Dice> mDiceList;
    public String TAG_ROLL_DICE = "tag_roll_dice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListeners();
    }

    private void init() {
        RecyclerView dicesView = new RecyclerView(this);
        // get the dices data from shared preferences

        //get the dice list save data once to add default dice lists to the shared preferences.
        mDiceList = DataModel.getDiceList();

        loadData();

        // save the default dices in the shared preferences.
        saveData();

        dicesView = findViewById(R.id.rv_dices);
        //set the adapter
        mAdapter = new DiceAdapter(this, mDiceList, new DiceAdapter.clickItemListener() {
            @Override
            public void onClickItem(Dice die) {
                // send the dice to the rolling screen to ask for input and show roll results.
                if (die == null)
                    return;
                Intent intent = new Intent(MainActivity.this, DiceRollActivity.class);
                intent.putExtra(TAG_ROLL_DICE, die);
                startActivity(intent);
            }
        });

        dicesView.setAdapter(mAdapter);
        // mAdapter.setDiceList(DataModel.getOrderList());
        mAdapter.notifyDataSetChanged();
    }

    private void initListeners() {
        findViewById(R.id.btn_add_custom_dice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open add custom dice dialog
                FragmentManager fm = getSupportFragmentManager();
                CustomDiceDialog customDiceDialog = CustomDiceDialog.newInstance();
                customDiceDialog.show(fm, "fragment_custom_dice");





            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear the custom dices from shared preferences ..
                clearData();
            }
        });
        findViewById(R.id.btn_show_roll_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call roll history activity...
                callRollHistoryActivity();
            }
        });
    }


    // shared preferences methods
    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("dices", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<Dice>>() {
        }.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        mDiceList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (mDiceList == null) {
            // if the array list is empty
            // creating a new array list.
            mDiceList = new ArrayList<>();
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(mDiceList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("dices", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    private void clearData() {

        // get the default list from data model and change the list as it will remove any ctsom added dices
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // get the list from datamodel with default values
        mDiceList = DataModel.getDiceList();
        // getting data from gson and storing it in a string.
        String json = gson.toJson(mDiceList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("dices", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
        // set the value in recycler view an refresh..
        mAdapter.setDiceList(mDiceList);
    }


    //Custom dice dialog methods

    @Override
    public void sendInput(String numOfSides) {
        if (!TextUtils.isEmpty(numOfSides)) {
            int numSides = Integer.parseInt(numOfSides);
            Dice die = new Dice(numSides);
            mDiceList.add(die);
            saveData();
            mAdapter.setDiceList(mDiceList);
        }

    }

    private void callRollHistoryActivity() {
        Intent intent = new Intent(MainActivity.this, RollHistoryActivity.class);
        startActivity(intent);
    }
}