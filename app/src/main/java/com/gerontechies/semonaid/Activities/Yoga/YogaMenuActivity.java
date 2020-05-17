package com.gerontechies.semonaid.Activities.Yoga;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.T2T.T2tCategoryListActivity;
import com.gerontechies.semonaid.Models.T2tDatabase;
import com.gerontechies.semonaid.Models.T2tItem;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class YogaMenuActivity extends AppCompatActivity {

    Button btn_next;
    CardView convert_trash, sell_treasure;
    T2tDatabase db = null;
    String jsonData;
    List<T2tItem> allItemList = new ArrayList<>();
    List<T2tItem> item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t2t_menu);
        setTitle("Trash to Treasure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                T2tDatabase.class, "t2t_database")
                .fallbackToDestructiveMigration()
                .build();


        ReadDatabase ld = new ReadDatabase();
        ld.execute();

        convert_trash = (CardView) findViewById(R.id.convert_trash);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);

        convert_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YogaMenuActivity.this, T2tCategoryListActivity.class);
                startActivity(intent);

            }
        });

        sell_treasure = (CardView) findViewById(R.id.sell_treasure);

        sell_treasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YogaMenuActivity.this, T2tCategoryListActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.T2tDAO().getAll();
            if (!(item.isEmpty() || item == null) ){
                for (T2tItem temp : item) {

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

                    T2tItem item = new T2tItem();
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String category = object.getString("category");
                    String t2t = object.getString("text");
                    String title = object.getString("title");


                    item.setId(id);
                    item.setCategory(category);
                    item.setName(name);
                    item.setT2t(t2t);
                    item.setTitle(title);

                    db.T2tDAO().insert(item);


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
            InputStream is = this.getAssets().open("T2TItems.json");
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
