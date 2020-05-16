package com.gerontechies.semonaid.Activities.MentalWellbeing.Events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.gerontechies.semonaid.Adapters.EventAdapter;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Arrays;
import java.util.List;

public class EventInfoActivity extends AppCompatActivity {

    TextView event_name, event_desc, event_address, event_day;
    ChipGroup categories;

    SemonaidDB db = null;
    EventItem item;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        event_address = (TextView) findViewById(R.id.location_txt);
        event_desc = (TextView) findViewById(R.id.deatils_txt);
        event_name = (TextView) findViewById(R.id.event_name);
        event_day = (TextView) findViewById(R.id.when_txt) ;
        categories = (ChipGroup) findViewById(R.id.categories_chips);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        setTitle("Event Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        eventId = getIntent().getIntExtra("event_id", 0);


        ReadDatabase rd = new ReadDatabase();
        rd.execute();

    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().findByEventID(eventId);
            return  status;
        }

        @Override
        protected void onPostExecute(String details) {

            String eventCategory = item.category;
            List<String> cat = Arrays.asList(eventCategory.split(","));
            Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);

            //String[] cat = eventCategory.split(",");
            for(int i = 0; i<cat.size(); i++){
                Chip chip = new Chip(EventInfoActivity.this);
                chip.setTypeface(font);
                chip.setText(cat.get(i));
                chip.setTextColor(getResources().getColorStateList(R.color.white));
                chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorPrimary));
                categories.addView(chip);
            }
           event_address.setText(item.Address);
           event_desc.setText(item.Description);
           event_name.setText(item.activity);
           event_day.setText(item.day);

        }

    }
}
