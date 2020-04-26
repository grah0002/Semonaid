package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Activities.Services.ServiceItemActivity;
import com.gerontechies.semonaid.Activities.Services.ServicesAllActivity;
import com.gerontechies.semonaid.Models.ServiceItem;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    List<ServiceItem> serviceItem;
    Filter mfilter;

    private List<ServiceItem> serviceListFiltered;


    public ServicesAdapter(Activity mContext, List<ServiceItem> serviceItem) {
        this.mContext = mContext;
        this.serviceItem = serviceItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_suburb, item_category_1, item_category_2, item_category_3, item_category_4;
        Button button;


        public MyViewHolder(View itemView) {
            super(itemView);

            item_name = (TextView) itemView.findViewById(R.id.txt_service_item);
            item_suburb = (TextView) itemView.findViewById(R.id.txt_service_suburb);
            button = (Button) itemView.findViewById(R.id.btn_view_details);
//            item_category_1 = (TextView) itemView.findViewById(R.id.txt_category1);
//            item_category_2 = (TextView) itemView.findViewById(R.id.txt_category2);
//            item_category_3 = (TextView) itemView.findViewById(R.id.txt_category3);
//            item_category_4 = (TextView) itemView.findViewById(R.id.txt_category4);



        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ServiceItem service = serviceItem.get(position);
        holder.item_name.setText(service.getService_name());
        final int id = service.getId();

        String suburb ;
        if(service.getSuburb().equals("n/a"))
            suburb = " ";
        else
            suburb=service.getSuburb();
        holder.item_suburb.setText(suburb);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ServiceItemActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(id));

                mContext.startActivity(intent);
            }
        });


//        double amt = stepsUser.amount * multiplier;
//
//        holder.item_name.setText("" +  stepsUser.itemName);
//        holder.item_amt.setText("" + amt);
//
//        Log.d("ITEM--", stepsUser.itemName);
    }




        @Override
        public int getItemCount () {
            return serviceItem.size();
        }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    serviceListFiltered = serviceItem;
                } else {
                    List<ServiceItem> filteredList = new ArrayList<>();
                    for (ServiceItem row : serviceItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getService_name().toLowerCase().contains(charString.toLowerCase()) || row.getSuburb().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    serviceListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = serviceListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                serviceListFiltered = (ArrayList<ServiceItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    }
