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



        public MyViewHolder(View itemView) {
            super(itemView);

            expTv1 = (ExpandableTextView)  itemView.findViewById(R.id.expand_text_view1);
            expTv2 = (ExpandableTextView)  itemView.findViewById(R.id.expand_text_view2);
            title1 = (TextView) itemView.findViewById(R.id.title1);
            title2 = (TextView) itemView.findViewById(R.id.title2);



        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_tip_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TipItem item = tipItem.get(position);

        holder.expTv1.setText(item.getTips_1());
        holder.expTv2.setText(item.getTips_2());
        holder.title1.setText(item.getTitle_1());
        holder.title2.setText(item.getTitle_2());



    }




    @Override
    public int getItemCount () {
        return tipItem.size();
    }
}
