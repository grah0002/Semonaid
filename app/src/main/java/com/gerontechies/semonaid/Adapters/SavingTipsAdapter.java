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
import com.gerontechies.semonaid.Models.BudgetItem;
import com.gerontechies.semonaid.Models.TipItem;
import com.gerontechies.semonaid.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

public class SavingTipsAdapter extends RecyclerView.Adapter<SavingTipsAdapter.MyViewHolder> {

    Context mContext;
    List<TipItem> tipItem;



    public SavingTipsAdapter(Activity mContext, List<TipItem> tipItem) {
        this.mContext = mContext;
        this.tipItem = tipItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView title1;
        TextView text1;
        CardView card_tip1;



        public MyViewHolder(View itemView) {
            super(itemView);


            title1 = (TextView) itemView.findViewById(R.id.title1);

            text1 = (TextView) itemView.findViewById(R.id.text1);

            card_tip1 = (CardView) itemView.findViewById(R.id.tip1);




        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_tip_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        TipItem item = tipItem.get(position);

        holder.title1.setText(item.getTitle());

        holder.text1.setText(item.getTip());


        holder.card_tip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.text1.getVisibility() == View.GONE){
                    holder.text1.setVisibility(View.VISIBLE);
                } else if(holder.text1.getVisibility() == View.VISIBLE){
                    holder.text1.setVisibility(View.GONE);
                }
            }
        });





    }




    @Override
    public int getItemCount () {
        return tipItem.size();
    }
}
