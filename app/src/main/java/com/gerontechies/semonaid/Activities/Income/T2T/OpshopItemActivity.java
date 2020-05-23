package com.gerontechies.semonaid.Activities.Income.T2T;

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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Models.OpshopDatabase;
import com.gerontechies.semonaid.Models.OpshopItem;
import com.gerontechies.semonaid.R;

public class OpshopItemActivity extends AppCompatActivity {

    TextView name, address;
    TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday, holiday;
    OpshopDatabase db = null;
    OpshopItem item;
    String id, opshopName;
    boolean isMap=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opshop_item);

        setTitle("Sell Treasure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                OpshopDatabase.class, "opshop_database")
                .fallbackToDestructiveMigration()
                .build();


        Intent intent = getIntent();
        id = getIntent().getStringExtra("id");
        Log.d("ID", id);
        if (intent.hasExtra(Intent.EXTRA_TEXT)){
             id = intent.getStringExtra(Intent.EXTRA_TEXT);
        }

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        name = (TextView) findViewById(R.id.txt_opshop_name);
        address = (TextView) findViewById(R.id.txt_opshop_address);
//        suburb = (TextView) findViewById(R.id.);
//        phone = (TextView) findViewById(R.id.);
//        email = (TextView) findViewById(R.id.);
//        helpline = (TextView) findViewById(R.id.);
//        website = (TextView) findViewById(R.id.);

        monday = (TextView) findViewById(R.id.txt_monday);
        tuesday = (TextView) findViewById(R.id.txt_tuesday);
        wednesday = (TextView) findViewById(R.id.txt_wednesday);
        thursday = (TextView) findViewById(R.id.txt_thursday);
        friday = (TextView) findViewById(R.id.txt_friday);
        saturday = (TextView) findViewById(R.id.txt_saturday);
        sunday = (TextView) findViewById(R.id.txt_sunday);
        holiday = (TextView) findViewById(R.id.txt_public);

//        train = (TextView) findViewById(R.id.txt_train);
//        tram = (TextView) findViewById(R.id.txt_tram);
//        addressTxt = (TextView) findViewById(R.id.txt_address);

    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String status = "";
            if(isMap){
                //item = db.ServiceDAO().findByName(serviceName);
            } else{
             //   item = db.ServiceDAO().findByID(Integer.parseInt(id));
            }
            item = db.OpshopDAO().findByID(Integer.parseInt(id));
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            String mTxt = item.getMonday();
            String tuTxt = item.getTuesday();
            String wTxt = item.getWednesday();
            String tTxt = item.getThursday();
            String fTxt = item.getFriday();
            String sat = item.getSaturday();
            String sun = item.getSunday();


           name.setText(item.getName());
           address.setText(item.getAddress());

            if(mTxt.equals("please call for detail")){
                monday.setVisibility(View.GONE);
            } else{
                monday.setText("Monday - "+item.getMonday());
            }
            if(tuTxt.equals("please call for detail")){
                tuesday.setVisibility(View.GONE);
            } else{
                tuesday.setText("Tuesday - "+item.getTuesday());
            }
            if(wTxt.equals("please call for detail")){
                wednesday.setVisibility(View.GONE);
            } else{
                wednesday.setText("Wednesday - "+item.getWednesday());
            }
            if(tTxt.equals("please call for detail")){
                thursday.setVisibility(View.GONE);
            } else{
                thursday.setText("Thusday - "+item.getThursday());
            }
            if(fTxt.equals("please call for detail")){
                friday.setVisibility(View.GONE);
            } else{
                friday.setText("Friday - "+item.getFriday());
            }
            if(sat.equals("please call for detail")){
                saturday.setVisibility(View.GONE);
            } else{
                saturday.setText("Saturday - "+item.getFriday());
            }
            if(sun.equals("please call for detail")){
                sunday.setVisibility(View.GONE);
            } else{
                sunday.setText("Sunday - "+item.getFriday());
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
            onBackPressed();
            return true;
        } else if(id == R.id.homeIcon){
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
            finish();
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
