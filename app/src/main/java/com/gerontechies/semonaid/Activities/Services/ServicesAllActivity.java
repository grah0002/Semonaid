package com.gerontechies.semonaid.Activities.Services;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.gerontechies.semonaid.Adapters.ServicesAdapter;
import com.gerontechies.semonaid.Adapters.SummaryItemAdapter;
import com.gerontechies.semonaid.Models.BudgetItem;
import com.gerontechies.semonaid.Models.ServiceDatabase;
import com.gerontechies.semonaid.Models.ServiceItem;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServicesAllActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    String jsonData;
    List<ServiceItem> allItemList = new ArrayList<>();
    List<ServiceItem> item;

    ServicesAdapter mAdapter;
    Button map_btn, filter_btn;

    ServiceDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        setTitle("Get Assistance");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                ServiceDatabase.class, "service_database")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_all_services);

        map_btn = (Button) findViewById(R.id.map_btn);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
        map_btn.setTypeface(font);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServicesAllActivity.this, ServicesMapActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "none");

                startActivity(intent);
            }
        });

//        filter_btn = (Button) findViewById(R.id.filter_btn);
//        filter_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ServicesAllActivity.this, ServicesFilterActivity.class);
//                startActivity(intent);
//            }
//        });



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
               // Log.d("ITEM", String.valueOf(allItemList.size()));
                 mAdapter = new ServicesAdapter(ServicesAllActivity.this,  allItemList);

                RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(ServicesAllActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManagerIncome);

                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                recyclerView.setNestedScrollingEnabled(false);



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
