package com.gerontechies.semonaid.Activities.Income.T2T;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.OpshopDatabase;
import com.gerontechies.semonaid.Models.OpshopItem;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

public class OpshopItemActivity extends AppCompatActivity {

    TextView name, address;
    TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday, holiday, phone, website;
    SemonaidDB db = null;
    OpshopItem item;
    Button openMaps;
    String id;
    CardView timings;
    boolean isMap=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opshop_item);

        setTitle("Sell Treasure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
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
        phone = (TextView) findViewById(R.id.txt_opshop_phone);
        website = (TextView) findViewById(R.id.txt_opshop_website);
        monday = (TextView) findViewById(R.id.txt_monday);
        tuesday = (TextView) findViewById(R.id.txt_tuesday);
        wednesday = (TextView) findViewById(R.id.txt_wednesday);
        thursday = (TextView) findViewById(R.id.txt_thursday);
        friday = (TextView) findViewById(R.id.txt_friday);
        saturday = (TextView) findViewById(R.id.txt_saturday);
        sunday = (TextView) findViewById(R.id.txt_sunday);
        holiday = (TextView) findViewById(R.id.txt_public);
        timings = (CardView) findViewById(R.id.timings_card);
        openMaps = (Button) findViewById(R.id.button_maps);


    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String status = "";
            item = db.AppDAO().findByIDOpPShop(Integer.parseInt(id));
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            String mTxt = item.getMonday();


           name.setText(item.getName());
           address.setText(item.getAddress());
           phone.setText(item.getPhone());
         //  website.setText(item.getWebsite());

            if(mTxt.equals("please call for detail")){
                timings.setVisibility(View.GONE);
            } else{
                monday.setText("Monday - "+item.getMonday());
                tuesday.setText("Tuesday - "+item.getTuesday());
                wednesday.setText("Wednesday - "+item.getWednesday());
                thursday.setText("Thusday - "+item.getThursday());
                friday.setText("Friday - "+item.getFriday());
                sunday.setText("Sunday - "+item.getFriday());
                saturday.setText("Saturday - "+item.getFriday());
            }

            website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(item.website);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });



            openMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q="+item.address);
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
    public boolean onOptionsItemSelected(MenuItem item) {
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
                        .setMessage("View Details of the Shop in this screen.\n\nClick on the 'Open in Maps' button to navigate to this location via Google maps\n\nTap on the website to open it in your browser")
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
