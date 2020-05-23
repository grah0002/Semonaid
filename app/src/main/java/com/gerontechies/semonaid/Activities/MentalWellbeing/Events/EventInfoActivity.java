package com.gerontechies.semonaid.Activities.MentalWellbeing.Events;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.EventAdapter;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EventInfoActivity extends AppCompatActivity {

    TextView event_name, event_desc, event_address, event_day;
    ChipGroup categories;

    ImageView map;
    SemonaidDB db = null;
    EventItem item;
    int eventId;

    private String STATIC_MAP_API_ENDPOINT = "https://maps.googleapis.com/maps/api/staticmap?size=230x200&path=";
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        event_address = (TextView) findViewById(R.id.location_txt);
        event_desc = (TextView) findViewById(R.id.deatils_txt);
        event_name = (TextView) findViewById(R.id.event_name);
        event_day = (TextView) findViewById(R.id.when_txt);
        categories = (ChipGroup) findViewById(R.id.categories_chips);
        map = (ImageView) findViewById(R.id.map_image);

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


    //reading the db for the events
    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().findByEventID(eventId);
            return status;
        }

        @Override
        protected void onPostExecute(String details) {

            Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
            String eventCategory = item.category;
            try {
                JSONArray categoriesJson = new JSONArray(eventCategory);

                for (int j = 0; j < categoriesJson.length(); j++) {
                    JSONObject cObj = categoriesJson.getJSONObject(j);

                    String c = cObj.getString("category");
                    // adding chips for each of the categories
                    Chip chip = new Chip(EventInfoActivity.this);
                    chip.setTypeface(font);
                    chip.setText(c);
                    chip.setTextColor(getResources().getColorStateList(R.color.white));
                    chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorPrimary));
                    categories.addView(chip);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            event_address.setText(item.Address);
            event_desc.setText(item.Description);
            event_name.setText(item.activity);
            event_day.setText(item.day);

           // getXmlFromUrl(String.valueOf(item.getLatitude()),String.valueOf(item.getLongitude()));
        }

    }

//    public void getXmlFromUrl(String lat, String  longitude) {
//        String xml = null;
//
//        URL url;
//        Bitmap bmp = null;
//        STATIC_MAP_API_ENDPOINT = "https://maps.google.com/maps/api/staticmap?center=" + lat + "," + longitude + "&zoom=15&size=200x200&sensor=false&key=AIzaSyAcNa93QUXWQdnLf57gaG7NEpkuQHF3v7U";
//
//
//        HttpURLConnection urlConnection = null;
//
//        try {
//            url = new URL(STATIC_MAP_API_ENDPOINT);
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            InputStream in = urlConnection.getInputStream();
//
//            InputStreamReader isw = new InputStreamReader(in);
//
//            BufferedReader br = new BufferedReader(isw);
//            StringBuilder sb = new StringBuilder();
//            bmp = BitmapFactory.decodeStream(in);
//            String line;
//            while ((line = br.readLine()) != null) {
//                sb.append(line+"\n");
//            }
//            br.close();
//
//            xml = sb.toString();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (bmp!=null) {
//
//            map.setImageBitmap(bmp);
//
//        }
//
//    }


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
