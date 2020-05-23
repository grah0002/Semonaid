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
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.Income.T2T.OpshopMapActivity;
import com.gerontechies.semonaid.Activities.Services.ServicesMapActivity;
import com.gerontechies.semonaid.Adapters.OpshopAdapter;
import com.gerontechies.semonaid.Adapters.ServicesAdapter;
import com.gerontechies.semonaid.Models.OpshopDatabase;
import com.gerontechies.semonaid.Models.OpshopItem;

import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class OpshopListActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    String jsonData;
    List<OpshopItem> allItemList = new ArrayList<>();
    List<OpshopItem> item;

    OpshopAdapter mAdapter;

    OpshopDatabase db = null;
//    String category;

    CardView map_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opshop_list);

        setTitle("Sell Treasure");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                OpshopDatabase.class, "opshop_database")
                .fallbackToDestructiveMigration()
                .build();

        Intent intent = getIntent();
/*        if (intent.hasExtra(Intent.EXTRA_TEXT)){
            category = intent.getStringExtra(Intent.EXTRA_TEXT);
            setTitle(category);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }*/
        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        map_btn = (CardView) findViewById(R.id.map_btn);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(OpshopListActivity.this, OpshopMapActivity.class);
//                intent1.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent1);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycle_opshops);



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

            if(allItemList.size()>1){
                 Log.d("ITEM", String.valueOf(allItemList.size()));
                 mAdapter = new OpshopAdapter(OpshopListActivity.this,  allItemList);

                RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(OpshopListActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManagerIncome);

                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                recyclerView.setNestedScrollingEnabled(false);



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
