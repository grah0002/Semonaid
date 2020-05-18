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

import com.gerontechies.semonaid.Models.Budget.JobTips;
import com.gerontechies.semonaid.Models.Budget.TipItem;
import com.gerontechies.semonaid.R;

import java.util.List;

/*Adapter for displaying the how to apply for jobs*/
public class JobTipsAdapter extends RecyclerView.Adapter<JobTipsAdapter.MyViewHolder> {

    Context mContext;
    List<JobTips> jobTips;



    public JobTipsAdapter(Activity mContext, List<JobTips> jobTips) {
        this.mContext = mContext;
        this.jobTips = jobTips;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView title1;
        TextView text1;
        CardView card_tip1;
        ImageView arrow_img;
        TextView step;



        public MyViewHolder(View itemView) {
            super(itemView);


            title1 = (TextView) itemView.findViewById(R.id.title1);

            text1 = (TextView) itemView.findViewById(R.id.text1);

            card_tip1 = (CardView) itemView.findViewById(R.id.tip1);

            arrow_img = (ImageView) itemView.findViewById(R.id.arrow_img);

            step = (TextView) itemView.findViewById(R.id.stage);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_info, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        JobTips item = jobTips.get(position);

        holder.title1.setText(item.getName());
        holder.text1.setText(item.getDesc());

        holder.step.setText("step "+item.getId() +"/10");



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
        return jobTips.size();
    }
}
