package com.gerontechies.semonaid.Activities.Services;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.MentalWellbeing.Events.EventInfoActivity;
import com.gerontechies.semonaid.Activities.MentalWellbeing.Events.EventsMapsActivity;
import com.gerontechies.semonaid.Adapters.ServicesAdapter;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.ServiceItem;
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

public class ServicesMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener  {

    private GoogleMap mMap;
    List<ServiceItem> allItemList = new ArrayList<>();
    List<ServiceItem> item;
    SemonaidDB db = null;
    String category;
    Map<String, String> mMarkerMap = new HashMap<>();
    String ROUTE, NAME;
    ServiceItem selected;


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
                Intent intent = new Intent(ServicesMapActivity.this, ServiceInfoActivity.class);
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
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        GetEventDetails getEventDetails = new GetEventDetails();
        getEventDetails.execute((Integer) marker.getTag());
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ServicesMapActivity.this, R.style.BottomSheet);
        View bottomsheet = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_layout, (LinearLayout) findViewById(R.id.bottomSheet));
        TextView locationName = bottomsheet.findViewById(R.id.location_name);
        TextView locationAddress = bottomsheet.findViewById(R.id.location_address_txt);
        TextView locationDays = bottomsheet.findViewById(R.id.location_days);
        Button view_details = bottomsheet.findViewById(R.id.btn_location);


        locationName.setText(selected.service_name);
        locationAddress.setText(selected.category_1);
       // locationDays.setText(selected.suburb);
        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServicesMapActivity.this, ServiceItemActivity.class);
                intent.putExtra("service_id", String.valueOf(selected.id)) ;
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
            selected = db.AppDAO().findByServiceItemID(ints[0]);


            return "eventItem";
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

          //add markers

            for(int i = 0; i<allItemList.size(); i++){
                 Marker marker;

                ServiceItem serviceItem = allItemList.get(i);

                if(category.equals("none")){
                    LatLng item  = new LatLng(serviceItem.getLatitude(),serviceItem.getLongitude());
                  //  mMap.addMarker(new MarkerOptions().position(item).title(serviceItem.getService_name()));
                    marker =  mMap.addMarker(new MarkerOptions()
                            .position(item)
                            .title(serviceItem.getService_name())
                            .snippet(serviceItem.getSuburb())

                    );
                    marker.setTag(serviceItem.getId());
                }

                else {
                    if(serviceItem.getCategory_1().equals(category) || serviceItem.getCategory_2().equals(category) || serviceItem.getCategory_3().equals(category) || serviceItem.getCategory_4().equals(category)){
                        LatLng item  = new LatLng(serviceItem.getLatitude(),serviceItem.getLongitude());
                       marker =  mMap.addMarker(new MarkerOptions()
                                .position(item)
                                .title(serviceItem.getService_name())
                                .snippet(serviceItem.getSuburb())

                        );
                        marker.setTag(serviceItem.getId());
                    }
                }
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
        mMap.setOnMarkerClickListener(this);

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
       // Toast.makeText(this, "Info window clicked",
         //       Toast.LENGTH_SHORT).show();
        String venueID = mMarkerMap.get(marker.getId());
        String venueName = marker.getTitle();
        Intent intent = new Intent(ServicesMapActivity.this, ServiceItemActivity.class);
        intent.putExtra("id", marker.getTag().toString());
        //     intent.putExtra("route", "map");
        startActivity(intent);
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//
//            }
//        });

    }
}
