package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RollHistoryActivity extends AppCompatActivity {


    // private variable
    HashMap<Integer, ArrayList<Integer>> mInfos;
    ArrayList<DiceRollInfo> mRollInfoList;
    RollHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_history);

        init();
        initListener();
    }

    private void initListener() {
        findViewById(R.id.btn_clear_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });
    }

    private void init() {
        // get the history from shared preferences
        loadHistory();
        //convert the hashmap to array list of diceRollInfo
        convertToArrayList();
        //set the values in adapter and load UI
        loadUI();
    }

    private void loadUI() {
        RecyclerView historyView = new RecyclerView(this);
        historyView = findViewById(R.id.rv_dices_history);
        adapter = new RollHistoryAdapter(this, mRollInfoList);
        historyView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadHistory() {
        // method to load hashmap from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("roll_info", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<HashMap<Integer, ArrayList<Integer>>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        mInfos = gson.fromJson(json, type);
        if(mInfos.size()==0)
            findViewById(R.id.tv_no_roll_history).setVisibility(View.VISIBLE);

        // checking below if the array list is empty or not
        if (mInfos == null) {
            // if the hashmap is empty
            // creating a new hashmap
            mInfos = new HashMap<Integer, ArrayList<Integer>>();
            // show view that no history is present right now
            findViewById(R.id.tv_no_roll_history).setVisibility(View.VISIBLE);
        }
        // make new object to store in array list from hashmap
        mRollInfoList = new ArrayList<>();
    }

    private void convertToArrayList() {
        if (mInfos == null)
            return;
        for (Map.Entry<Integer, ArrayList<Integer>> item : mInfos.entrySet()) {
            int numSides = item.getKey();
            Dice die = new Dice(numSides);
            DiceRollInfo diceRollInfo = new DiceRollInfo();
            diceRollInfo.setDie(die);
            List<Integer> rollValues = item.getValue();
            diceRollInfo.setValues(rollValues);
            mRollInfoList.add(diceRollInfo);
        }
    }

    private void clearHistory() {
        mRollInfoList.clear();
        mInfos.clear();
        loadHistory();


        // get the default list from data model and change the list as it will remove any ctsom added dices
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        mInfos.clear();
        // getting data from gson and storing it in a string.
        String json = gson.toJson(mInfos);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("roll_info", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.clear();
        editor.apply();
        // set the value in recycler view an refresh..
        adapter.setDiceList(mRollInfoList);
        adapter.notifyDataSetChanged();
        findViewById(R.id.tv_no_roll_history).setVisibility(View.VISIBLE);
    }
}