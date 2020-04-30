package com.gerontechies.semonaid.Activities.Services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.gerontechies.semonaid.Adapters.ServicesAdapter;
import com.gerontechies.semonaid.Models.ServiceDatabase;
import com.gerontechies.semonaid.Models.ServiceItem;
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

public class ServicesMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<ServiceItem> allItemList = new ArrayList<>();
    List<ServiceItem> item;
    ServiceDatabase db = null;
    String category;
    Map<String, String> mMarkerMap = new HashMap<>();
    String ROUTE, NAME;

    ServicesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_map);
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
                Intent intent = new Intent(ServicesMapActivity.this, ServicesAllActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)){
            category = intent.getStringExtra(Intent.EXTRA_TEXT);

        }
        ServicesMapActivity.this.setTitle(R.string.app_name);

        db = Room.databaseBuilder(this,
                ServiceDatabase.class, "service_database")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();
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

          //add markers

            for(int i = 0; i<allItemList.size(); i++){

                ServiceItem serviceItem = allItemList.get(i);

                if(category.equals("none")){
                    LatLng item  = new LatLng(serviceItem.getLatitude(),serviceItem.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(item).title(serviceItem.getService_name()));
                }

                else {
                    if(serviceItem.getCategory_1().equals(category) || serviceItem.getCategory_2().equals(category) || serviceItem.getCategory_3().equals(category) || serviceItem.getCategory_4().equals(category)){
                        LatLng item  = new LatLng(serviceItem.getLatitude(),serviceItem.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(item).title(serviceItem.getService_name()));
                    }
                }
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

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                String venueID = mMarkerMap.get(marker.getId());
//                String venueName = marker.getTitle();
//                Intent intent = new Intent(ServicesMapActivity.this, ServiceItemActivity.class);
//                intent.putExtra("service_name", venueName);
//                intent.putExtra("route", "map");
//                startActivity(intent);
//
//                return false;
//            }
//        });

    }


}
