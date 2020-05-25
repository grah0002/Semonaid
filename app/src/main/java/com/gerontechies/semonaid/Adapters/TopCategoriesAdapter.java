package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Activities.Budget.Calculator.TipsActivity;
import com.gerontechies.semonaid.Models.Budget.BudgetItem;
import com.gerontechies.semonaid.R;

import java.util.List;

/*Adapter for the top3 expenses*/
public class TopCategoriesAdapter extends RecyclerView.Adapter<TopCategoriesAdapter.MyViewHolder> {

    Context mContext;
    List<BudgetItem> budgetItem;



    public TopCategoriesAdapter(Activity mContext, List<BudgetItem> budgetItem) {
        this.mContext = mContext;
        this.budgetItem = budgetItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_amt;
        CardView cardView;




        public MyViewHolder(View itemView) {
            super(itemView);

            item_name = (TextView) itemView.findViewById(R.id.category_txt);
            item_amt = (TextView) itemView.findViewById(R.id.category_amt);
            cardView = (CardView) itemView.findViewById(R.id.item);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_tips_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final BudgetItem stepsUser = budgetItem.get(position);

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


        final double amt = stepsUser.amount * multiplier;

        holder.item_name.setText("" +  stepsUser.itemName);
        holder.item_amt.setText("$" + amt + "/ year");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TipsActivity.class);
                intent.putExtra("name", String.valueOf(stepsUser.getItemName()));
                intent.putExtra("category", String.valueOf(stepsUser.getCategory()));
                intent.putExtra("amt", String.valueOf(amt));

                mContext.startActivity(intent);
            }
        });

        Log.d("ITEM--", stepsUser.itemName);
    }


    @Override
    public int getItemCount () {
        return budgetItem.size();
    }
}
