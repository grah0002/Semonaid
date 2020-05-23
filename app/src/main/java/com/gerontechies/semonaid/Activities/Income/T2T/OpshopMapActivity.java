package com.gerontechies.semonaid.Activities.Income.T2T;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.ServicesAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpshopMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener  {

    private GoogleMap mMap;
    List<OpshopItem> allItemList = new ArrayList<>();
    List<OpshopItem> item;
    OpshopDatabase db = null;
//    String category;
    Map<String, String> mMarkerMap = new HashMap<>();
    String ROUTE, NAME;

    ServicesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opshop_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       // setTitle("Get Assistance");
        Toolbar myToolbar = null;

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
                OpshopDatabase.class, "opshop_database")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    }


    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.OpshopDAO().getAll();
            if (!(item.isEmpty() || item == null) ){
                for (OpshopItem temp : item) {

                    allItemList.add(temp);

                }


            }


            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

          //add markers

            for(int i = 0; i<allItemList.size(); i++){
                 Marker marker;

                OpshopItem opshopItem = allItemList.get(i);

//                if(category.equals("none")){
                    LatLng item  = new LatLng(opshopItem.getLat(),opshopItem.getLng());
                    mMap.addMarker(new MarkerOptions().position(item).title(opshopItem.getName()));
/*                }

                else {
                    if(opshopItem.getCategory_1().equals(category) || serviceItem.getCategory_2().equals(category) || serviceItem.getCategory_3().equals(category) || serviceItem.getCategory_4().equals(category)){
                        LatLng item  = new LatLng(serviceItem.getLatitude(),serviceItem.getLongitude());
                       marker =  mMap.addMarker(new MarkerOptions()
                                .position(item)
                                .title(serviceItem.getService_name())
                                .snippet(serviceItem.getCategory_1())

                        );
                        marker.setTag(serviceItem.getId());
                    }
                } */
              // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(item, 15f));
            }

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-37.806498, 144.929392);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f));

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
