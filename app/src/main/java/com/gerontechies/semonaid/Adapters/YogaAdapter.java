package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Models.YogaItem;
import com.gerontechies.semonaid.R;

import java.util.List;

public class YogaAdapter extends RecyclerView.Adapter<YogaAdapter.MyViewHolder> {

    Context mContext;
    List<YogaItem> yogaItem;


    public YogaAdapter(Activity mContext, List<YogaItem> yogaItem) {
        this.mContext = mContext;
        this.yogaItem = yogaItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView title1;
        ImageView image1;
        TextView text1;
        CardView card_yoga1;


        public MyViewHolder(View itemView) {
            super(itemView);


            title1 = (TextView) itemView.findViewById(R.id.yogatitle);

            image1 = (ImageView) itemView.findViewById(R.id.yogaImage);

            text1 = (TextView) itemView.findViewById(R.id.yogatext);

            card_yoga1 = (CardView) itemView.findViewById(R.id.yoga1);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_yoga_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        YogaItem item = yogaItem.get(position);

        holder.title1.setText(item.getTitle());

        String src = item.image;
        Log.d("IMAge", src + "---");
        //String uri = "@drawable/" + "mountainpose";

        String uri="";

        if (item.equals("Mountain")) {
             uri = "@drawable/" + "mountainpose";
        } else if (item.equals("Tree")) {
             uri = "@drawable/" + "treepose";
        } else if (item.equals("Sphinx")) {
             uri = "@drawable/" + "sphinx";
        } else if (item.equals("Bird")) {
             uri = "@drawable/" + "birddog";
        } else if (item.equals("Dog")) {
             uri = "@drawable/" + "downwarddog";
        } else if (item.equals("Savasana")) {
             uri = "@drawable/" + "savasanas";
        }

        int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
        Drawable res = this.mContext.getResources().getDrawable(imageResource);

        holder.image1.setImageDrawable(res);
        holder.text1.setText(item.getYoga());
        holder.card_yoga1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.text1.getVisibility() == View.GONE) {
                    holder.text1.setVisibility(View.VISIBLE);
                    holder.image1.setVisibility(View.VISIBLE);
                } else if (holder.text1.getVisibility() == View.VISIBLE) {
                    holder.text1.setVisibility(View.GONE);
                    holder.image1.setVisibility(View.GONE);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return yogaItem.size();
    }
}
