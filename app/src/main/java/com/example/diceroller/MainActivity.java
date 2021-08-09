package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements DiceAdapter.clickItemListener {


    // private variables.
    private DiceAdapter mAdapter;
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
        dicesView = findViewById(R.id.rv_dices);
        //set the adapter
        mAdapter = new DiceAdapter(this, DataModel.getDiceList());

        dicesView.setAdapter(mAdapter);
       // mAdapter.setDiceList(DataModel.getOrderList());
        mAdapter.notifyDataSetChanged();
    }
    private void initListeners(){
        findViewById(R.id.btn_add_custom_dice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open add custom dice dialog
                FragmentManager fm = getSupportFragmentManager();
                CustomDiceDialog customDiceDialog = CustomDiceDialog.newInstance();
                customDiceDialog.show(fm, "fragment_custom_dice");

            }
        });
    }

    // implement interface methods


    @Override
    public void onClickItem(Dice die) {
        // send the dice to the rolling screen to ask for input and show roll results.
        if (die == null)
            return;
        String type = die.getType();
        Intent i = new Intent(MainActivity.this, DiceRollActivity.class);
        i.putExtra(TAG_ROLL_DICE, die);
        startActivity(i);

    }
}