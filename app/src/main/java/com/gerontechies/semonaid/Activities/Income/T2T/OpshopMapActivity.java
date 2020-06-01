package com.gerontechies.semonaid.Activities.Income.T2T;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.Services.ServiceItemActivity;
import com.gerontechies.semonaid.Activities.Services.ServicesMapActivity;
import com.gerontechies.semonaid.Adapters.ServicesAdapter;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.ServiceItem;
import com.gerontechies.semonaid.Models.OpshopDatabase;
import com.gerontechies.semonaid.Models.OpshopItem;

import com.gerontechies.semonaid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpshopMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,  GoogleMap.OnMarkerClickListener   {

    private GoogleMap mMap;
    List<OpshopItem> allItemList = new ArrayList<>();
    List<OpshopItem> item;
    SemonaidDB db = null;
    OpshopItem selected;
    Typeface font;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opshop_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       // setTitle("Get Assistance");
        Toolbar myToolbar = null;

        font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);

        mapFragment.getMapAsync(this);

        Button list_btn = (Button) findViewById(R.id.list_btn) ;
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
        list_btn.setTypeface(font);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpshopMapActivity.this, OpshopListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)){
//            category = intent.getStringExtra(Intent.EXTRA_TEXT);

        }
        OpshopMapActivity.this.setTitle(R.string.app_name);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        GetEventDetails getEventDetails = new GetEventDetails();
        String[] arrOfStr = marker.getId().split("m");

        Log.d("ERR", arrOfStr[1]);
        int id = Integer.parseInt(arrOfStr[1]);
        getEventDetails.execute(id);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(OpshopMapActivity.this, R.style.BottomSheet);
        View bottomsheet = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_layout, (LinearLayout) findViewById(R.id.bottomSheet));
        TextView locationName = bottomsheet.findViewById(R.id.location_name);
        TextView locationAddress = bottomsheet.findViewById(R.id.location_address_txt);
        TextView locationDays = bottomsheet.findViewById(R.id.location_days);
        locationAddress.setTypeface(font);
        locationDays.setTypeface(font);
        locationName.setTypeface(font);

        Button view_details = bottomsheet.findViewById(R.id.btn_location);
        view_details.setTypeface(font);


        locationName.setText(selected.name);
        locationAddress.setText(selected.address);

        // locationDays.setText(selected.suburb);
        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpshopMapActivity.this, OpshopItemActivity.class);
                intent.putExtra("id", String.valueOf(selected.id)) ;
                startActivity(intent);
            }
        });

        bottomSheetDialog.setContentView(bottomsheet);
        bottomSheetDialog.show();


        return true;
    }

    private class GetEventDetails extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... ints) {
            selected = db.AppDAO().findByIDOpPShop(ints[0]);
            return "eventItem";
        }


    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllOpShops();
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

          //add markers

            for(int i = 0; i<item.size(); i++){
                 Marker marker;

                OpshopItem opshopItem = item.get(i);

                    LatLng item  = new LatLng(opshopItem.getLat(),opshopItem.getLng());
                    mMap.addMarker(new MarkerOptions().position(item).title(opshopItem.getName()));
                marker =  mMap.addMarker(new MarkerOptions()
                        .position(item)
                        .title(opshopItem.getName())


                );
                marker.setTag(opshopItem.getId());

            }

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-37.806498, 144.929392);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f));

        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnInfoWindowClickListener(this);


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

    @Override
    public void onInfoWindowClick(Marker marker) {


    }
}
