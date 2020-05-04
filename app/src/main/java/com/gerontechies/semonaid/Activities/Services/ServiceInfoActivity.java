package com.gerontechies.semonaid.Activities.Services;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.Calculator.SummaryActivity;
import com.gerontechies.semonaid.Adapters.ServicesAdapter;
import com.gerontechies.semonaid.Models.BudgetDatabase;
import com.gerontechies.semonaid.Models.BudgetItem;
import com.gerontechies.semonaid.Models.ServiceDatabase;
import com.gerontechies.semonaid.Models.ServiceItem;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ServiceInfoActivity extends AppCompatActivity {

    CardView all, suburb, categories;
    String jsonData;
    List<ServiceItem> item;
    int itemCount = 0;

    boolean isLoaded = false;

    List<ServiceItem> allItemList = new ArrayList<>();
    ServiceDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);
        setTitle("Get Assistance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                ServiceDatabase.class, "service_database")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();




        all = (CardView) findViewById(R.id.card_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesMapActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "none");
                startActivity(intent);
            }
        });


        categories = (CardView) findViewById(R.id.card_categories);
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceInfoActivity.this, ServiceCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("ServicesVic.json");
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
                jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    ServiceItem item = new ServiceItem();
                    int id = object.getInt("id");
                    String service_name = object.getString("service_name");
                    String what = object.getString("what");
                    String who = object.getString("who");
                    String address_1 = object.getString("address_1");
                    String address_2 = object.getString("address_2");
                    String suburb = object.getString("suburb");
                    String phone_number = object.getString("phone_number");
                    String free_line = object.getString("free_line");
                    String email = object.getString("email");
                    String website = object.getString("website");
                    String monday = object.getString("monday");
                    String tuesday = object.getString("tuesday");
                    String wednesday = object.getString("wednesday");
                    String thursday = object.getString("thursday");
                    String friday = object.getString("friday");
                    String saturday = object.getString("saturday");
                    String sunday = object.getString("sunday");
                    String public_holodays = object.getString("public_holodays");
                    String cost = object.getString("cost");
                    String tram_routes = object.getString("tram_routes");
                    String nearest_train = object.getString("nearest_train");
                    String category_1 = object.getString("category_1");
                    String category_2 = object.getString("category_2");
                    String category_4 = object.getString("category_4");
                    String category_3 = object.getString("category_3");
                    double longitude = object.getDouble("longitude");
                    double latitude = object.getDouble("latitude");
                    String geocoded_location = object.getString("geocoded_location");

                    item.setId(id);

                    item.setService_name(service_name);
                    item.setWho(who);
                    item.setWhat(what);
                    item.setAddress_1(address_1);
                    item.setAddress_2(address_2);
                    item.setSuburb(suburb);
                    item.setPhone_number(phone_number);
                    item.setFree_line(free_line);
                    item.setEmail(email);
                    item.setWebsite(website);
                    item.setMonday(monday);
                    item.setTuesday(tuesday);
                    item.setWednesday(wednesday);
                    item.setThursday(thursday);
                    item.setFriday(friday);
                    item.setSaturday(saturday);
                    item.setSunday(sunday);
                    item.setPublic_holodays(public_holodays);
                    item.setCost(cost);
                    item.setTram_routes(tram_routes);
                    item.setNearest_train(nearest_train);
                    item.setCategory_1(category_1);
                    item.setCategory_2(category_2);
                    item.setCategory_3(category_3);
                    item.setCategory_4(category_4);
                    item.setLatitude(latitude);
                    item.setLongitude(longitude);
                    item.setGeocoded_location(geocoded_location);


                    allItemList.add(item);


                    db.ServiceDAO().insert(item);


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

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.ServiceDAO().getAll();
            if (!(item.isEmpty() || item == null) ){
                for (ServiceItem temp : item) {




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
                jsonData = loadJSONFromAsset();
                LoadData ld = new LoadData();
                ld.execute();

            }



        }

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
        }

        return super.onOptionsItemSelected(item);
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
