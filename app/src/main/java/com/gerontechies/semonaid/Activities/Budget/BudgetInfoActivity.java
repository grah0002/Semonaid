package com.gerontechies.semonaid.Activities.Budget;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.Tips.TipsMenuActivity;
import com.gerontechies.semonaid.Activities.Services.ServiceInfoActivity;
import com.gerontechies.semonaid.Models.ServiceDatabase;
import com.gerontechies.semonaid.Models.ServiceItem;
import com.gerontechies.semonaid.Models.TipDatabase;
import com.gerontechies.semonaid.Models.TipItem;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BudgetInfoActivity extends AppCompatActivity {

    Button btn_next;
    CardView budget_btn, saving_btn, service_btn;
    TipDatabase db = null;
    String jsonData;
    List<TipItem> allItemList = new ArrayList<>();
    List<TipItem> item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_info);
        setTitle("Budget Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//
//        btn_next = (Button) findViewById(R.id.btn_next);
//        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
//        btn_next.setTypeface(font);
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(BudgetInfoActivity.this, BudgetMainMenuActivity.class);
//                startActivity(intent);
//            }
//        });

        db = Room.databaseBuilder(this,
                TipDatabase.class, "tips_database")
                .fallbackToDestructiveMigration()
                .build();


        ReadDatabase ld = new ReadDatabase();
        ld.execute();

        budget_btn = (CardView) findViewById(R.id.budget_calculator);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);

        budget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, BudgetMainMenuActivity.class);
                startActivity(intent);
            }
        });

        saving_btn = (CardView) findViewById(R.id.savings);

        saving_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, TipsMenuActivity.class);
                startActivity(intent);
            }
        });

        service_btn = (CardView) findViewById(R.id.assistance);
        service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, ServiceInfoActivity.class);
                startActivity(intent);
            }
        });



    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("BudgetItems.json");
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

    private class LoadData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String data =  loadJSONFromAsset();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    TipItem item = new TipItem();
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String category = object.getString("category");
                    String tips_1 = object.getString("tips_1");
                    String tips_2 = object.getString("tips_2");
                    String title1 = object.getString("title_1");
                    String title2 = object.getString("title_2");


                    item.setId(id);
                    item.setCategory(category);
                    item.setName(name);
                    item.setTips_1(tips_1);
                    item.setTips_2(tips_2);
                    item.setTitle_1(title1);
                    item.setTitle_2(title2);



                    db.TipDAO().insert(item);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


        @Override
        protected void onPostExecute(String details) {
            //itemCount = 100;


        }

    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.TipDAO().getAll();
            if (!(item.isEmpty() || item == null) ){
                for (TipItem temp : item) {




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
