package com.gerontechies.semonaid.Activities.Services;

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
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.ServiceItem;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

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
    SemonaidDB db = null;
    CardView clothes, showers, accom, health, food, advise, counselling, drug, travel, jobs, helpline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);
        setTitle("Get Assistance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clothes = (CardView) findViewById(R.id.card_blankets);
        showers =  (CardView) findViewById(R.id.card_shower);
        accom = (CardView) findViewById(R.id.card_accom);
        health = (CardView) findViewById(R.id.card_health);
        food = (CardView) findViewById(R.id.card_food);
        advise = (CardView) findViewById(R.id.card_legal);
        counselling = (CardView) findViewById(R.id.card_mental);
        travel = (CardView) findViewById(R.id.card_travel);
        jobs = (CardView) findViewById(R.id.card_emp);
        helpline = (CardView) findViewById(R.id.card_helpline);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();



        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Clothes and Blankets";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        showers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Showers / Laundry";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        accom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Accommodation";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Helpline";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Health Services / Pharmacy";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Food";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });



        advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Legal / Financial Advice";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        counselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Counselling and Psychiatric Services";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        all = (CardView) findViewById(R.id.card_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesMapActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "none");
                startActivity(intent);
            }
        });




        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Travel Assistance";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Employment Assistance";
                Intent intent = new Intent(ServiceInfoActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
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



    //adding the values to the db

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

                    db.AppDAO().insertServiceItem(item);


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
            item = db.AppDAO().getAllServiceItem();
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
                        .setMessage("Tap on a category to find nearby affordable/free services that can assist you")
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
