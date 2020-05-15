package com.gerontechies.semonaid.Activities.Budget.Tips;

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
import com.gerontechies.semonaid.Adapters.SavingTipsAdapter;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Tips.TipDatabase;
import com.gerontechies.semonaid.Models.Budget.TipItem;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class TipCategoryActivity extends AppCompatActivity {

    RecyclerView tipsRV;
    SemonaidDB db = null;
    List<TipItem> item ;
    String tipName;
    List<TipItem> allIatemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_category);
        setTitle("Saving Tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tipName = getIntent().getStringExtra("tip_category");
        TextView catName = (TextView) findViewById(R.id.categoryName_txt);

        if(tipName.equals("General")){
            catName.setText("General Saving Tips");
        } else if( tipName.equals("Bills")){
            catName.setText("Save on Bills");
        } else  if(tipName.equals("Banking")){
            catName.setText("Banking Tips");
        } else  if(tipName.equals("Travel")){
            catName.setText("Travel Tips");
        }
        Log.d("TIPS",tipName);


        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();




        tipsRV = (RecyclerView) findViewById(R.id.tipsRV);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    };

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getTipCategory(tipName);
            if (!(item.isEmpty() || item == null) ) {
                for (TipItem temp : item) {
                    allIatemList.add(temp);
                    Log.d("TIPS",temp.name);
                }

            }
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            if(allIatemList != null){

                if(allIatemList.size()>0){
                    SavingTipsAdapter adapter = new SavingTipsAdapter(TipCategoryActivity.this,  allIatemList);
                    RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(TipCategoryActivity.this, LinearLayoutManager.VERTICAL, false);
                    tipsRV.setLayoutManager(mLayoutManagerIncome);

                    tipsRV.setItemAnimator(new DefaultItemAnimator());
                    tipsRV.setAdapter(adapter);
                    tipsRV.setNestedScrollingEnabled(false);

                }
//                else if(allIatemList.size()<=0 ){
//
//                    noRes.setVisibility(View.VISIBLE);
//                    searchTxt.setText("Oops, this is embarrassing! \nWe currently do not have any tips for this category! Please check out the general tips to find more Saving Tips!");
//                }
            }
//            else if(allIatemList.size()<=0 ){
//
//                noRes.setVisibility(View.VISIBLE);
//                searchTxt.setText("Oops, this is embarrassing! \nWe currently do not have any tips for this category! Please check out the general tips to find more Saving Tips!");
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
