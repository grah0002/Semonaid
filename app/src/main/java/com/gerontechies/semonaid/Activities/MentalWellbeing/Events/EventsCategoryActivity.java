package com.gerontechies.semonaid.Activities.MentalWellbeing.Events;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
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
import com.gerontechies.semonaid.Activities.Services.ServicesCategoryList;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EventsCategoryActivity extends AppCompatActivity {

    CardView health, social, art, learning, map;
    SemonaidDB db = null;
    List<EventItem> item;
    List<EventItem> allItemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_category);
        setTitle("Event Finder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        map = (CardView) findViewById(R.id.card_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsCategoryActivity.this, EventsMapsActivity.class);
                intent.putExtra("event_category", "none");
                startActivity(intent);
            }
        });

        health = (CardView) findViewById(R.id.card_health);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Health and Wellbeing";
                Intent intent = new Intent(EventsCategoryActivity.this, EventsListingActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        social = (CardView) findViewById(R.id.card_social);
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Social Groups";
                Intent intent = new Intent(EventsCategoryActivity.this, EventsListingActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        art = (CardView) findViewById(R.id.card_arts);
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Arts and culture";
                Intent intent = new Intent(EventsCategoryActivity.this, EventsListingActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        learning = (CardView) findViewById(R.id.card_lifelong);
        learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Lifelong Learning";
                Intent intent = new Intent(EventsCategoryActivity.this, EventsListingActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

    }


    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllEvents();
            if (!(item.isEmpty() || item == null) ){
                for (EventItem temp : item) {

                    allItemList.add(temp);

                }
            }
            return  status;
        }

        @Override
        protected void onPostExecute(String details) {

            if(allItemList.size()>1){
                Log.d("Data", "Loaded");
            } else{
                LoadData ld = new LoadData();
                ld.execute();

            }



        }

    }

    //loading the file from the json
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("WellBeingActivites.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private class LoadData extends AsyncTask<Void, Void, String>  {

        @Override
        protected String doInBackground(Void... params) {


            JSONArray jsonArray = null;
            try {
                String jsonData = loadJSONFromAsset();
                jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    EventItem item = new EventItem();
                    int id = object.getInt("id");
                    String activity = object.getString("activity");
                    String day = object.getString("day");
                    String category = object.getString("Category");

                    String email = object.getString("Email");
                    String phone = object.getString("Phone");
                    String website = object.getString("Website");
                    String Address = object.getString("Address");
                    String Description = object.getString("Description");
                    double longitude = object.getDouble("longitude");
                    double latitude = object.getDouble("latitude");

                    item.setLatitude(latitude);
                    item.setLongitude(longitude);
                    item.setActivity(activity);
                    item.setAddress(Address);
                    item.setCategory(category);
                    item.setDay(day);
                    item.setId(id);
                    item.setEmail(email);
                    item.setWebsite(website);
                    item.setPhone(phone);
                    item.setDescription(Description);

                    db.AppDAO().insertEventItem(item);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


        @Override
        protected void onPostExecute(String details) {
            //itemCount = 100;
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
                        .setMessage("Tap on the category of the events you are interested in to find out more about where they are and what they do" )
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

    public void setTitle(String title){
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);


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
