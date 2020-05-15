package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Models.Budget.BudgetItem;
import com.gerontechies.semonaid.R;

import java.util.List;

public class SummaryItemAdapter extends RecyclerView.Adapter<SummaryItemAdapter.MyViewHolder> {

    Context mContext;
    List<BudgetItem> budgetItem;



    public SummaryItemAdapter(Activity mContext, List<BudgetItem> budgetItem) {
        this.mContext = mContext;
        this.budgetItem = budgetItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_amt;




        public MyViewHolder(View itemView) {
            super(itemView);

            item_name = (TextView) itemView.findViewById(R.id.txt_item_category);
            item_amt = (TextView) itemView.findViewById(R.id.txt_item_amount);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_summary_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        BudgetItem stepsUser = budgetItem.get(position);

        int multiplier = 1;
        if(stepsUser.frequency == 1){
            multiplier = 52;
        } else if(stepsUser.frequency == 2){
            multiplier = 26;
        } else if(stepsUser.frequency == 3){
            multiplier = 12;
        } else if(stepsUser.frequency == 4){
            multiplier = 1;
        }


        double amt = stepsUser.amount * multiplier;

        holder.item_name.setText("" +  stepsUser.itemName);
        holder.item_amt.setText("" + amt);

        Log.d("ITEM--", stepsUser.itemName);
    }




        @Override
        public int getItemCount () {
            return budgetItem.size();
        }
    }
