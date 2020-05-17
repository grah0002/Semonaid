package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Models.Budget.TipItem;
import com.gerontechies.semonaid.R;

import java.util.List;

/*Adapter to show the saving tips*/

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
        ImageView arrow_img;



        public MyViewHolder(View itemView) {
            super(itemView);


            title1 = (TextView) itemView.findViewById(R.id.title1);

            text1 = (TextView) itemView.findViewById(R.id.text1);

            card_tip1 = (CardView) itemView.findViewById(R.id.tip1);

            arrow_img = (ImageView) itemView.findViewById(R.id.arrow_img);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savings_tipitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        TipItem item = tipItem.get(position);

        holder.title1.setText(item.getTitle());

        holder.text1.setText(item.getTip());

//        if(position == 0){
//            holder.text1.setVisibility(View.VISIBLE);
//        }

        holder.card_tip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.text1.getVisibility() == View.GONE){
                    holder.text1.setVisibility(View.VISIBLE);
                    holder.arrow_img.setImageResource(R.drawable.arrow_up_float);

                } else if(holder.text1.getVisibility() == View.VISIBLE){
                    holder.text1.setVisibility(View.GONE);
                    holder.arrow_img.setImageResource(R.drawable.arrow_down_float);
                }
            }
        });


    }


    @Override
    public int getItemCount () {
        return tipItem.size();
    }
}
