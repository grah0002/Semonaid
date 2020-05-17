package com.gerontechies.semonaid.Activities.Yoga;

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
import androidx.room.Database;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.YogaAdapter;
import com.gerontechies.semonaid.Models.YogaDatabase;
import com.gerontechies.semonaid.Models.YogaItem;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class YogaListActivity extends AppCompatActivity {

    RecyclerView yogaRV;
    YogaDatabase db = null;
    List<YogaItem> item ;
    String yogaName;
    List<YogaItem> allItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_list);
        setTitle("Yoga Techniques");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        TextView yogaNameText = (TextView) findViewById(R.id.yogaName_txt);

//        if(yogaName.equals("Mountain")){
//            yogaNameText.setText("Mountain Pose");
  //      } else if( yogaName.equals("Tree")){
    //        yogaNameText.setText("Tree Pose");
      //  } else  if(yogaName.equals("Sphinx")){
//            yogaNameText.setText("Sphinx Pose");
  //      } else  if(yogaName.equals("Bird")){
    //        yogaNameText.setText("Bird Pose");
      //  } else if( yogaName.equals("Dog")){
//            yogaNameText.setText("Downward Facing Dog");
  //      } else if( yogaName.equals("Savasana")){
    //        yogaNameText.setText("Savasana");
      //  }
//        Log.d("YOGA",yogaName);


        db = Room.databaseBuilder(this,
                YogaDatabase.class, "yoga_database")
                .fallbackToDestructiveMigration()
                .build();




        yogaRV = (RecyclerView) findViewById(R.id.yogaRV);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    };

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.YogaDAO().getAll();
            if (!(item.isEmpty() || item == null) ) {
                for (YogaItem temp : item) {
                    allItemList.add(temp);
                    Log.d("YOGA",temp.name);
                }

            }
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

//            if(allItemList != null){


                    RecyclerView.LayoutManager mLayoutManagerYoga = new LinearLayoutManager(YogaListActivity.this, LinearLayoutManager.VERTICAL, false);
                    yogaRV.setLayoutManager(mLayoutManagerYoga);
                    yogaRV.setItemAnimator(new DefaultItemAnimator());
                    YogaAdapter adapter = new YogaAdapter(YogaListActivity.this,  allItemList);
                    yogaRV.setAdapter(adapter);
                    yogaRV.setNestedScrollingEnabled(false);

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
