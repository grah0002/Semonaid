package com.gerontechies.semonaid.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.BudgetInfoActivity;
import com.gerontechies.semonaid.Activities.T2T.T2tMenuActivity;
import com.gerontechies.semonaid.Activities.Yoga.YogaListActivity;
import com.gerontechies.semonaid.Adapters.ViewPagerAdapter;
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
import java.util.Timer;
import java.util.TimerTask;

public class HomeScreenActivity extends AppCompatActivity {

    ViewPager viewPager;
    YogaDatabase db = null;
    List<YogaItem> allItemList = new ArrayList<>();
    List<YogaItem> item;
    Integer [] images = { R.drawable.semonaidupdated, R.drawable.semonaidbg2};
    String [] text = { "Your Second Change at Saving", "Bring that Change Home"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Semonaid");
        getSupportActionBar().hide();

        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);

        db = Room.databaseBuilder(this,
                YogaDatabase.class, "yoga_database")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();
        CardView getStarted = (CardView) findViewById(R.id.card_save);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, BudgetInfoActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        CardView card_inc = (CardView) findViewById(R.id.card_inc);
        card_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, T2tMenuActivity.class);
                startActivity(intent);
                // finish();
            }
        });

        CardView card_mental = (CardView) findViewById(R.id.card_mental);
        card_mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, YogaListActivity.class);
                startActivity(intent);
                // finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, images, text);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new taskTime(), 2000, 7000);


    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.YogaDAO().getAll();
            if (!(item.isEmpty() || item == null) ){
                for (YogaItem temp : item) {

                    allItemList.add(temp);

                }
            }
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            if(allItemList.size()>1){
                Log.d("Data", "Loaded");
            } else{

                LoadData ld = new LoadData();
                ld.execute();

            }
        }
    }
    private class LoadData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String data =  loadJSONFromAsset();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    YogaItem item = new YogaItem();
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String yoga = object.getString("text");
                    String image = object.getString("image");
                    String title = object.getString("title");


                    item.setId(id);
                    item.setName(name);
                    item.setImage(image);
                    item.setYoga(yoga);
                    item.setTitle(title);

                    db.YogaDAO().insert(item);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


        @Override
        protected void onPostExecute(String details) {

        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("YogaItems.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public  class taskTime extends TimerTask{

        @Override
        public void run() {
            HomeScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()== 0 ){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home_menu, menu);
//        return true;
//    }
//                //Custom title in the NavBar
        public void setTitle (String title){
            Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);


            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView textView = new TextView(this);
            textView.setText(title);
            textView.setTypeface(font);
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(R.color.white));

            textView.setGravity(Gravity.CENTER);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(textView);
        }


    }
