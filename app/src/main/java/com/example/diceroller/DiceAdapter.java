package com.example.diceroller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiceAdapter extends RecyclerView.Adapter<DiceAdapter.DiceViewHolder> {

    private Context mContext;
    //order list
    private ArrayList<Dice> mDiceList;
    private clickItemListener mCLickListener;

    //interface to interact with main activity..


    public interface clickItemListener {
        void onClickItem(Dice die);
    }




    //constructor

    public DiceAdapter(Context mContext, ArrayList<Dice> mDIceList) {
        this.mContext = mContext;
        this.mDiceList = mDIceList;
        notifyDataSetChanged();
    }



    @Override
    public DiceAdapter.DiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.view_dice_adapter_item, parent, false);
        return new DiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiceAdapter.DiceViewHolder holder, int position) {
        Dice die = mDiceList.get(position);
        if (die == null)
            return;

        //click listener for full item of the list
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call interface method for item view click
                mCLickListener.onClickItem(die);
            }
        });


        // set the values to he view of each item from each orderinfo item
//        holder.choice1.setText(info.getBreadEnum().getDisplayName());
//        holder.choice2.setText(info.getCheeseEnum().getDisplayName());
//        holder.choice3.setText(info.getSauceEnum().getDisplayName());
//        holder.choice4.setText(info.getToppings().get(0));
//        double amount =info.getAmount();
//        DecimalFormat precision = new DecimalFormat("0.00");
//        holder.amountView.setText(String.format("$ %s",precision.format(amount)));
//        holder.dateView.setText(info.getOrderDate());

    }

    @Override
    public int getItemCount() {
        return mDiceList == null ? 0 : mDiceList.size();
    }
    // View holder class

    public class DiceViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;

        public DiceViewHolder(View itemView) {
            super(itemView);
            // initialize your views in 1 order item
            cardView = itemView.findViewById(R.id.parent_view_adapter_item);
//


        }
    }

    public void setDiceList(ArrayList<Dice> diceList){
        this.mDiceList=diceList;
        notifyDataSetChanged();
    }
}

