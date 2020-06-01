package com.gerontechies.semonaid.Activities.MentalWellbeing.Events;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
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

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.ServiceItem;
import com.gerontechies.semonaid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    List<EventItem> allItemList = new ArrayList<>();
    List<EventItem> item;
    SemonaidDB db = null;
    String category;
    Map<String, String> mMarkerMap = new HashMap<>();
    ArrayList<LatLng> latLngs = new ArrayList<LatLng>() ;
    EventItem selected;
    Typeface font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button list_btn = (Button) findViewById(R.id.list_btn) ;
        font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        list_btn.setTypeface(font);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent intent = new Intent(EventsMapsActivity.this, EventInfoActivity.class);
                // startActivity(intent);
                EventsMapsActivity.this.finish();
            }
        });

        Intent intent = getIntent();



        category = getIntent().getStringExtra("event_category");
        Log.d("CAT", category);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        //getting the selected event details
        GetEventDetails getEventDetails = new GetEventDetails();
        getEventDetails.execute((Integer) marker.getTag());

        //Reference - https://www.youtube.com/watch?v=-QychBmRXz0&t=426s
        //Setting the bottom dialog sheet
        //setting the textview values
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EventsMapsActivity.this, R.style.BottomSheet);
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


        locationName.setText(selected.activity);
        locationAddress.setText(selected.Address);
        locationDays.setText(selected.day);
        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsMapsActivity.this, EventInfoActivity.class);
                intent.putExtra("event_id", (int) selected.id);
                startActivity(intent);
            }
        });

        bottomSheetDialog.setContentView(bottomsheet);
        bottomSheetDialog.show();


        return true;
    }


    //getting the details of the selected marker item

    private class GetEventDetails extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... ints) {
            selected = db.AppDAO().findByEventID(ints[0]);
            return "eventItem";
        }

    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllEvents();
            if (!(item.isEmpty() || item == null) ){
                for (EventItem temp : item) {
                    Log.d("CAT", temp.category);
                    if(category.equals("none")){
                        allItemList.add(temp);
                    } else {

                        String eventCategory = temp.category;
                        JSONArray categoriesJson = null;
                        try {
                            categoriesJson = new JSONArray(eventCategory);
                            for (int j = 0; j < categoriesJson.length(); j++) {
                                JSONObject cObj = categoriesJson.getJSONObject(j);

                                String c = cObj.getString("category");
                                if (c.toLowerCase().equals(category.toLowerCase())) {
                                    allItemList.add(temp);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
            return  status;
        }

        @Override
        protected void onPostExecute(String details) {

            for(int i = 0; i<allItemList.size(); i++){
                Marker marker;

                EventItem eventItem = allItemList.get(i);


                LatLng item  = new LatLng(eventItem.getLatitude(),eventItem.getLongitude());
                    //  mMap.addMarker(new MarkerOptions().position(item).title(serviceItem.getService_name()));
                    marker =  mMap.addMarker(new MarkerOptions()
                            .position(item)
                            .title(eventItem.getActivity())
                            .snippet(eventItem.getAddress())

                    );
                    marker.setTag(eventItem.getId());


            }


        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in melbourne central and move the camera
        LatLng melb = new LatLng(-37.806498, 144.929392);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(melb, 12f));

        mMap.setOnInfoWindowClickListener(this);

        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);

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

        Intent intent = new Intent(EventsMapsActivity.this, EventInfoActivity.class);
        intent.putExtra("event_id", (int) marker.getTag());
        startActivity(intent);


    }


}