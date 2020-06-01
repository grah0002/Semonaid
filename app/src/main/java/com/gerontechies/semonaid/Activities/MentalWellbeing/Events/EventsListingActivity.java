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
import com.google.android.material.chip.Chip;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

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
            if (category.equals("Lifelong Learning")) {
                setTitle("Educational");
            } else {
                setTitle(category);
            }

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
                    JSONArray categoriesJson = null;
                    try {
                        categoriesJson = new JSONArray(eventCategory);
                        for (int j = 0; j < categoriesJson.length(); j++) {
                            JSONObject cObj = categoriesJson.getJSONObject(j);

                            String c = cObj.getString("category");
                            if(c.toLowerCase().equals(category.toLowerCase())){
                                allItemList.add(temp);
                            }
                            // adding chips for each of the categories
//                            Chip chip = new Chip(EventsListingActivity.this);
//                            chip.setTypeface(font);
//                            chip.setText(c);
//                            chip.setTextColor(getResources().getColorStateList(R.color.white));
//                            chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorPrimary));
//                            categories.addView(chip);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    public boolean onOptionsItemSelected(MenuItem item1) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item1.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.homeIcon:
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.helpIcon:

                MaterialDialog mDialog = new MaterialDialog.Builder(this)
                        .setTitle("Help")
                        .setMessage("\"Tap on 'View on Map' button to see the locations of these events on a map. \n" +
                                "\n\nTap on 'View Details' to know more about the event\"" )
                        .setCancelable(false)

                        .setPositiveButton("Close", R.drawable.close, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }

                        })


                        .build();

                // Show Dialog
                mDialog.show();

        }
        return super.onOptionsItemSelected(item1);
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
