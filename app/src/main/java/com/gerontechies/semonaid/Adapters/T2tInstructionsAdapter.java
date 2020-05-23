package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Activities.Income.T2T.T2TItemDetailActivity;
import com.gerontechies.semonaid.Models.T2TInstructionsItem;
import com.gerontechies.semonaid.Models.T2tItem;
import com.gerontechies.semonaid.R;

import java.util.List;

public class T2tInstructionsAdapter extends RecyclerView.Adapter<T2tInstructionsAdapter.MyViewHolder> {

    Context mContext;
    List<T2TInstructionsItem> t2tItem;



    public T2tInstructionsAdapter(Activity mContext, List<T2TInstructionsItem> t2tItem) {
        this.mContext = mContext;
        this.t2tItem = t2tItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView stepTxt, detailsTxt;


        public MyViewHolder(View itemView) {
            super(itemView);


            stepTxt = (TextView) itemView.findViewById(R.id.step_txt);
            detailsTxt = (TextView) itemView.findViewById(R.id.details_txt);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.t2t_instructions_steps, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final T2TInstructionsItem item = t2tItem.get(position);


        String step = "Step " +String.valueOf(position+1)+"/"+ t2tItem.size();
        holder.stepTxt.setText(step);
        holder.detailsTxt.setText(item.text);



    }


    @Override
    public int getItemCount () {
        return t2tItem.size();
    }
}
