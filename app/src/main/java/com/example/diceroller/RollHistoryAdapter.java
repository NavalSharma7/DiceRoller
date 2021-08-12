package com.example.diceroller;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RollHistoryAdapter extends RecyclerView.Adapter<RollHistoryAdapter.HistoryHolder> {

    private Context mContext;
    //order list
    private ArrayList<DiceRollInfo> mDiceRollInfo;


    //constructor

    public RollHistoryAdapter(Context mContext, ArrayList<DiceRollInfo> mDiceRollInfo) {
        this.mContext = mContext;
        this.mDiceRollInfo = mDiceRollInfo;
        notifyDataSetChanged();
    }



    @Override
    public RollHistoryAdapter.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.view_roll_history_adapter, parent, false);
        return new RollHistoryAdapter.HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryHolder holder, int position) {
        DiceRollInfo info = mDiceRollInfo.get(position);
        if (info == null)
            return;
        // set the values to the view of each item from each DieRollinfo item
        Dice die = info.getDie();
        if(die!=null){
            String type = die.getType();
            if(!TextUtils.isEmpty(type))
            holder.diceTypeView.setText(type);
            List<Integer> values = info.getValues();
            holder.diceRollValues.setText(String.format("THe rolled Values are %s",values.toString()));
        }
    }


    @Override
    public int getItemCount() {
        return mDiceRollInfo == null ? 0 : mDiceRollInfo.size();
    }
    // View holder class

    public class HistoryHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView diceTypeView;
        TextView diceRollValues;

        public HistoryHolder(View itemView) {
            super(itemView);
            // initialize your views in 1 order item
            cardView = itemView.findViewById(R.id.parent_view_roll_history);
            diceTypeView = itemView.findViewById(R.id.tv_history_type);
            diceRollValues = itemView.findViewById(R.id.tv_values_history);



        }
    }

    public void setDiceList(ArrayList<DiceRollInfo> diceList){
        this.mDiceRollInfo =diceList;
        notifyDataSetChanged();
    }
}

