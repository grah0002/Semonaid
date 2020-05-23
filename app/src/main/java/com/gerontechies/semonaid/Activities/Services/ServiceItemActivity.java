package com.gerontechies.semonaid.Activities.Services;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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

public class ServiceItemActivity extends AppCompatActivity {

    TextView name, what, who, address, suburb, phone, email, helpline, website;
    TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday, holiday;
    TextView cost, tram, train, category1, category2, category3, category4, addressTxt;
    SemonaidDB db = null;
    ServiceItem item;
    String id, route, serviceName;
    boolean isMap=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_item);

        //= (TextView) findViewById(R.id.);

        setTitle("Get Assistance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();


        Intent intent = getIntent();
        id = getIntent().getStringExtra("service_id");


        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        name = (TextView) findViewById(R.id.txt_service_name);
        what = (TextView) findViewById(R.id.txt_service_what);
        who =  (TextView) findViewById(R.id.txt_service_who);
        address = (TextView) findViewById(R.id.txt_cost);
        monday = (TextView) findViewById(R.id.txt_monday);
        tuesday = (TextView) findViewById(R.id.txt_tuesday);
        wednesday = (TextView) findViewById(R.id.txt_wednesday);
        thursday = (TextView) findViewById(R.id.txt_thursday);
        friday = (TextView) findViewById(R.id.txt_friday);
        saturday = (TextView) findViewById(R.id.txt_saturday);
        sunday = (TextView) findViewById(R.id.txt_sunday);
        holiday = (TextView) findViewById(R.id.txt_public);
        category1 = (TextView) findViewById(R.id.txt_category1);
        category2 = (TextView) findViewById(R.id.txt_category2);
        category3= (TextView) findViewById(R.id.txt_category3);
        category4 = (TextView) findViewById(R.id.txt_category4);
        train = (TextView) findViewById(R.id.txt_train);
        tram = (TextView) findViewById(R.id.txt_tram);
        addressTxt = (TextView) findViewById(R.id.txt_address);

    }

    //getting the values for the selected item

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String status = "";
            if(isMap){
                //item = db.ServiceDAO().findByName(serviceName);
            } else{
             //   item = db.ServiceDAO().findByID(Integer.parseInt(id));
            }
            item = db.AppDAO().findByServiceItemID(Integer.parseInt(id));
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            String nameTxt = item.getService_name();
            String whoTxt =  item.getWho();
            String whatTxt =  item.getWhat();
            String mTxt = item.getMonday();
            String tuTxt = item.getTuesday();
            String wTxt = item.getWednesday();
            String tTxt = item.getThursday();
            String fTxt = item.getFriday();
            String sat = item.getSaturday();
            String sun = item.getSunday();
            String pTxt =  item.getPublic_holodays();
            String c1 = item.getCategory_1();
            String c2 = item.getCategory_2();
            String c3 = item.getCategory_3();
            String c4 = item.getCategory_4();
            String price = item.getCost();
            String train1 = item.getNearest_train();
            String tram1 = item.getTram_routes();
            String a1 = item.getAddress_1();
            String a2 = item.getAddress_2();
            String a3 = item.getSuburb();
            String place ="";

            if(!a1.equals("n/a")){
                 place = a1 +", ";
            }

            if(!a2.equals("n/a")){
                place = place +a2  +", ";
            }

            if(!a3.equals("n/a")){
                place = place + a3  ;
            }


           name.setText(item.getService_name());

            if(price.equals("n/a")){
                address.setVisibility(View.GONE);
            } else{
                address.setText("Cost: "+item.getCost());
            }

            if(whoTxt.equals("n/a")){
                who.setVisibility(View.GONE);
            } else{
                who.setText(item.getWho());
            }

            if(whatTxt.equals("n/a")){
                what.setVisibility(View.GONE);
            } else{
                what.setText(item.getWhat());
            }
            if(mTxt.equals("n/a")){
                monday.setVisibility(View.GONE);
            } else{
                monday.setText("Monday - "+item.getMonday());
            }
            if(tuTxt.equals("n/a")){
                tuesday.setVisibility(View.GONE);
            } else{
                tuesday.setText("Tuesday - "+item.getTuesday());
            }
            if(wTxt.equals("n/a")){
                wednesday.setVisibility(View.GONE);
            } else{
                wednesday.setText("Wednesday - "+item.getWednesday());
            }
            if(tTxt.equals("n/a")){
                thursday.setVisibility(View.GONE);
            } else{
                thursday.setText("Thusday - "+item.getThursday());
            }
            if(fTxt.equals("n/a")){
                friday.setVisibility(View.GONE);
            } else{
                friday.setText("Friday - "+item.getFriday());
            }
            if(sat.equals("n/a")){
                saturday.setVisibility(View.GONE);
            } else{
                saturday.setText("Saturday - "+item.getFriday());
            }
            if(sun.equals("n/a")){
                sunday.setVisibility(View.GONE);
            } else{
                sunday.setText("Sunday - "+item.getFriday());
            }


            if(pTxt.equals("n/a")){
                holiday.setVisibility(View.GONE);
            } else{
                holiday.setText("Public Holiday - "+item.getPublic_holodays());
            }
            if(c1.equals("n/a")){
                category1.setVisibility(View.GONE);
            } else{
                category1.setText(item.getCategory_1());

            }
            if(c2.equals("n/a")){
                category2.setVisibility(View.GONE);
            } else{
                category2.setText(item.getCategory_2());

            }
            if(c3.equals("n/a")){
                category3.setVisibility(View.GONE);
            } else{
                category3.setText(item.getCategory_3());
            }
            if(c4.equals("n/a")){
                category4.setVisibility(View.GONE);
            } else{
                category4.setText(item.getCategory_4());

            }


            if(place.equals("n/a")){
                addressTxt.setVisibility(View.GONE);
            } else{
                addressTxt.setText(place);
            }
            if(train1.equals("n/a")){
                train.setVisibility(View.GONE);
            } else{
                train.setText("Train - "+ item.getNearest_train());
            }
            if(tram1.equals("n/a")){
                tram.setVisibility(View.GONE);
            } else{
                tram.setText("Tram - "+ item.getTram_routes());

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
                        .setMessage("This page shows the details of the location you have selected.\n\nIf you wish to use their services, please refer to the 'How To Get There' section at the bottom of the page")
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
