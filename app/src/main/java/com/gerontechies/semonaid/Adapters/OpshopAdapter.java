package com.gerontechies.semonaid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gerontechies.semonaid.Activities.Income.T2T.OpshopItemActivity;
import com.gerontechies.semonaid.Models.OpshopItem;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class OpshopAdapter extends RecyclerView.Adapter<OpshopAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    List<OpshopItem> opshopItem;
    Filter mfilter;

    private List<OpshopItem> opshopListFiltered;


    public OpshopAdapter(Activity mContext, List<OpshopItem> opshopItem) {
        this.mContext = mContext;
        this.opshopItem = opshopItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_suburb;
        Button button;


        public MyViewHolder(View itemView) {
            super(itemView);

            item_name = (TextView) itemView.findViewById(R.id.txt_opshop_item);
            item_suburb = (TextView) itemView.findViewById(R.id.txt_opshop_suburb);
            button = (Button) itemView.findViewById(R.id.btn_view_details);
            Typeface font = ResourcesCompat.getFont(itemView.getContext(),R.font.montserrat);
            button.setTypeface(font);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.opshop_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        OpshopItem opshop = opshopItem.get(position);
        holder.item_name.setText(opshop.getName());
        final int id = opshop.getId();

        String suburb ;
        if(opshop.getSuburb().equals("n/a"))
            suburb = " ";
        else
            suburb=opshop.getSuburb();
        holder.item_suburb.setText(suburb);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OpshopItemActivity.class);
                intent.putExtra("id", String.valueOf(id));

                mContext.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount () {
            return opshopItem.size();
        }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    opshopListFiltered = opshopItem;
                } else {
                    List<OpshopItem> filteredList = new ArrayList<>();
                    for (OpshopItem row : opshopItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getSuburb().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    opshopListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = opshopListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                opshopListFiltered = (ArrayList<OpshopItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
