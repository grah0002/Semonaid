package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Activities.Income.Skills.SkillDetailItemActivity;
import com.gerontechies.semonaid.Activities.MentalWellbeing.Events.EventInfoActivity;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.JobItem;
import com.gerontechies.semonaid.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    Context mContext;
    List<EventItem> eventItems;



    public EventAdapter(Activity mContext, List<EventItem> eventItems) {
        this.mContext = mContext;
        this.eventItems = eventItems;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView event_name;
       Button event_btn;


        public MyViewHolder(View itemView) {
            super(itemView);

            event_name = (TextView) itemView.findViewById(R.id.event_name);
            event_btn = (Button) itemView.findViewById(R.id.event_btn);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final EventItem eventItem = eventItems.get(position);
        final int eventId = eventItem.id;
        holder.event_name.setText("" +  eventItem.activity);
        holder.event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, EventInfoActivity.class);

                intent.putExtra("event_id", eventId);

                mContext.startActivity(intent);
            }
        });
//
    }


    @Override
    public int getItemCount () {
        return eventItems.size();
    }
}
