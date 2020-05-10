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



        ExpandableTextView expTv1, expTv2;
        TextView title1, title2;
        TextView text1, text2;
        CardView card_tip1, card_tip2;



        public MyViewHolder(View itemView) {
            super(itemView);


            title1 = (TextView) itemView.findViewById(R.id.title1);
            title2 = (TextView) itemView.findViewById(R.id.title2);

            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);

            card_tip1 = (CardView) itemView.findViewById(R.id.tip1);
            card_tip2 = (CardView) itemView.findViewById(R.id.tip2);




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

        holder.title1.setText(item.getTitle_1());
        holder.title2.setText(item.getTitle_2());

        holder.text1.setText(item.getTips_1());

        holder.text2.setText(item.getTips_2());

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

        holder.card_tip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.text2.getVisibility() == View.GONE){
                    holder.text2.setVisibility(View.VISIBLE);
                } else if(holder.text2.getVisibility() == View.VISIBLE){
                    holder.text2.setVisibility(View.GONE);
                }
            }
        });



    }




    @Override
    public int getItemCount () {
        return tipItem.size();
    }
}
