package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Models.T2tItem;
import com.gerontechies.semonaid.R;

import java.util.List;

public class T2tAdapter extends RecyclerView.Adapter<T2tAdapter.MyViewHolder> {

    Context mContext;
    List<T2tItem> t2tItem;



    public T2tAdapter(Activity mContext, List<T2tItem> t2tItem) {
        this.mContext = mContext;
        this.t2tItem = t2tItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView title1;
        TextView text1;
        CardView card_t2t1;



        public MyViewHolder(View itemView) {
            super(itemView);


            title1 = (TextView) itemView.findViewById(R.id.title1);

            text1 = (TextView) itemView.findViewById(R.id.text1);

            card_t2t1 = (CardView) itemView.findViewById(R.id.tip1);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_t2t_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        T2tItem item = t2tItem.get(position);

        holder.title1.setText(item.getTitle());

        holder.text1.setText(item.getT2t());

//        if(position == 0){
//            holder.text1.setVisibility(View.VISIBLE);
//        }

        holder.card_t2t1.setOnClickListener(new View.OnClickListener() {
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
        return t2tItem.size();
    }
}
