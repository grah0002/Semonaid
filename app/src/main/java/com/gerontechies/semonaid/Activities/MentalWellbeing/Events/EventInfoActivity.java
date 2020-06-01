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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.EventAdapter;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

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

    TextView event_name, event_desc, event_address, event_day, event_phone, event_website, event_email;
    ChipGroup categories;

    ImageView map;
    SemonaidDB db = null;
    Button maps;
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
        event_phone = (TextView) findViewById(R.id.phone_txt);
        event_website = (TextView) findViewById(R.id.website_txt);
        event_email = (TextView) findViewById(R.id.email_txt);
        maps = (Button) findViewById(R.id.button_maps);

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

            final Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
            String eventCategory = item.category;
            try {
                JSONArray categoriesJson = new JSONArray(eventCategory);

                for (int j = 0; j < categoriesJson.length(); j++) {
                    JSONObject cObj = categoriesJson.getJSONObject(j);

                    String c = cObj.getString("category");
                    // adding chips for each of the categories
                    Chip chip = new Chip(EventInfoActivity.this);
                    chip.setTypeface(font);
                    if (c.equals("Lifelong Learning")) {
                        chip.setText("Educational");
                    } else {
                        chip.setText(c);
                    }

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
            event_email.setText(item.email);
            event_phone.setText(item.phone);
            // event_website.setText(item.website);

            event_website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EventInfoActivity.this, R.style.BottomSheet);
                    View bottomsheet = LayoutInflater.from(getApplicationContext())
                            .inflate(R.layout.website_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheet));
                    TextView locationName = bottomsheet.findViewById(R.id.location_name);

                    TextView websiteDetails = bottomsheet.findViewById(R.id.website_details);
                    websiteDetails.setTypeface(font);
                    locationName.setTypeface(font);

                    Button view_details = bottomsheet.findViewById(R.id.btn_location);
                    view_details.setTypeface(font);


                    websiteDetails.setText(item.website);
                    view_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(item.website);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });

                    bottomSheetDialog.setContentView(bottomsheet);
                    bottomSheetDialog.show();


                }
            });


            maps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + item.Address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            });
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

                        .setMessage("This page shows the details of the event you have selected. Click on the 'Open in Maps' button to navigate to this location via Google maps. Tap on the website to open it in your browser")
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
