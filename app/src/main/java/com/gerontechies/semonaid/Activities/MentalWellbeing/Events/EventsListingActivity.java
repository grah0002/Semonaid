package com.gerontechies.semonaid.Activities.MentalWellbeing.Events;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.Services.ServiceInfoActivity;
import com.gerontechies.semonaid.Activities.Services.ServicesAllActivity;
import com.gerontechies.semonaid.Adapters.EventAdapter;
import com.gerontechies.semonaid.Adapters.ServicesAdapter;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.ServiceItem;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventsListingActivity extends AppCompatActivity {

    SemonaidDB db = null;
    List<EventItem> item;
    List<EventItem> allItemList = new ArrayList<>();
    RecyclerView rv;
    CardView map;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_listing);

        //setTitle("Event Finder");


        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            //getting the category name for this selection
            category = intent.getStringExtra(Intent.EXTRA_TEXT);
            setTitle(category);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        rv = (RecyclerView) findViewById(R.id.events_rv);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        map = (CardView) findViewById(R.id.card_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EventsListingActivity.this, EventsMapsActivity.class);
                intent1.putExtra("event_category", category);
                startActivity(intent1);
            }
        });
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllEvents();
            if (!(item.isEmpty() || item == null)) {
                for (EventItem temp : item) {

                    String eventCategory = temp.category;
                    List<String> cat = Arrays.asList(eventCategory.split(","));

                    //String[] cat = eventCategory.split(",");
                    for (int i = 0; i < cat.size(); i++) {
                        //  Log.d("STR", i+"-------"+cat.get(i)+"----"+temp.activity+"----"+ temp.id);
                        if (cat.get(i).equals(category)) {
                            allItemList.add(temp);

                        }
                    }


                }
            }
            return status;
        }

        @Override
        protected void onPostExecute(String details) {

            if (allItemList.size() > 1) {
                Log.d("STR", String.valueOf(allItemList.size()));
                EventAdapter mAdapter = new EventAdapter(EventsListingActivity.this, allItemList);
                RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(EventsListingActivity.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(mLayoutManagerIncome);
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setAdapter(mAdapter);
                //rv.setNestedScrollingEnabled(false);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            this.finish();
            onBackPressed();
            return true;
        } else if (id == R.id.homeIcon) {
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setTitle(String title) {
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTypeface(font);
        textView.setTextSize(20);
        textView.setTextColor(getResources().getColor(R.color.white));

        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }
}
