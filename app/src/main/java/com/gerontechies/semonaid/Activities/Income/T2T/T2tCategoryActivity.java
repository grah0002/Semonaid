package com.gerontechies.semonaid.Activities.Income.T2T;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.T2tAdapter;
import com.gerontechies.semonaid.Models.T2tDatabase;
import com.gerontechies.semonaid.Models.T2tItem;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class T2tCategoryActivity extends AppCompatActivity {

    RecyclerView t2tRV;
    T2tDatabase db = null;
    List<T2tItem> item ;
    String t2tName;
    List<T2tItem> allItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t2t_category);
        setTitle("Sources of Treasure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t2tName = getIntent().getStringExtra("t2t_category");
        TextView catName = (TextView) findViewById(R.id.categoryName_txt);

        if(t2tName.equals("Bottle")){
            catName.setText("Treasure from Bottles/Jars");
        } else if( t2tName.equals("Paper")){
            catName.setText("Treasure from Paper");
        } else  if(t2tName.equals("Fabric")){
            catName.setText("Treasure from Fabric");
        } else  if(t2tName.equals("PillBottle")){
            catName.setText("Treasure from Rx Bottles");
        }
        Log.d("T2T",t2tName);


        db = Room.databaseBuilder(this,
                T2tDatabase.class, "t2t_database")
                .fallbackToDestructiveMigration()
                .build();




        t2tRV = (RecyclerView) findViewById(R.id.t2tRV);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    };

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.T2tDAO().getCategory(t2tName);
            if (!(item.isEmpty() || item == null) ) {
                for (T2tItem temp : item) {
                    allItemList.add(temp);
                    Log.d("T2T",temp.name);
                }

            }
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

//            if(allItemList != null){

//                if(allItemList.size()>0){
                    RecyclerView.LayoutManager mLayoutManagerT2t = new LinearLayoutManager(T2tCategoryActivity.this, LinearLayoutManager.VERTICAL, false);
                    t2tRV.setLayoutManager(mLayoutManagerT2t);
                    t2tRV.setItemAnimator(new DefaultItemAnimator());
                    T2tAdapter adapter = new T2tAdapter(T2tCategoryActivity.this,  allItemList);
                    t2tRV.setAdapter(adapter);
                    t2tRV.setNestedScrollingEnabled(false);

//                }
//                else if(allItemList.size()<=0 ){
//
//                    noRes.setVisibility(View.VISIBLE);
//                    searchTxt.setText("Oops, this is embarrassing! \nWe currently do not have any innovations for this category! Please check out the other categories!");
//                }
//latest            }
//            else if(allItemList.size()<=0 ){
//
//                noRes.setVisibility(View.VISIBLE);
//                searchTxt.setText("Oops, this is embarrassing! \nWe currently do not have any innovations for this category! Please check out the other categories!");
//            }



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
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);


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
