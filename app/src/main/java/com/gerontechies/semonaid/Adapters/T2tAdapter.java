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

        TextView title1, text1;
        ImageView image1;
        CardView card_t2t1;
        Button details;

        public MyViewHolder(View itemView) {
            super(itemView);


            title1 = (TextView) itemView.findViewById(R.id.t2t_title1);
            image1 = (ImageView) itemView.findViewById(R.id.t2t_image1);
            card_t2t1 = (CardView) itemView.findViewById(R.id.card_t2t1);
            details = (Button) itemView.findViewById(R.id.btn_details);

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

        final T2tItem item = t2tItem.get(position);

        holder.title1.setText(item.getT2t());

        String src = item.getName();
        if (src.equals("BCC")) {
            holder.image1.setImageResource(R.drawable.bottlecapcross);
            Log.d("IMAge", src + "---");
        }
        if (src.equals("MJPV")) {
            holder.image1.setImageResource(R.drawable.masonjarvases);
            Log.d("IMAge", src + "---");
        }
        if (src.equals("PB")) {
            holder.image1.setImageResource(R.drawable.paperbagbracelets);
            Log.d("IMAge", src + "---");
        }
        if (src.equals("PPM")) {
            holder.image1.setImageResource(R.drawable.paperbagplacemat);
            Log.d("IMAge", src + "---");
        }
        if (src.equals("TN")) {
            holder.image1.setImageResource(R.drawable.tshirtnecklace);
            Log.d("IMAge", src + "---");
        }
        if (src.equals("DTB")) {
            holder.image1.setImageResource(R.drawable.denimtotebag);
            Log.d("IMAge", src + "---");
        }
        if (src.equals("PBL")) {
            holder.image1.setImageResource(R.drawable.pilllamp);
            Log.d("IMAge", src + "---");
        }
        if (src.equals("FBV")) {
            holder.image1.setImageResource(R.drawable.pillbamboovase);
            Log.d("IMAge", src + "---");
        }



        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, T2TItemDetailActivity.class);
                intent.putExtra("trash_id", String.valueOf(item.id));
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount () {
        return t2tItem.size();
    }
}
