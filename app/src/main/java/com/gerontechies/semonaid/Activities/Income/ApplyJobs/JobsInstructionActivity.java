package com.gerontechies.semonaid.Activities.Income.ApplyJobs;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.MentalWellbeing.Events.EventInfoActivity;
import com.gerontechies.semonaid.Adapters.JobTipsAdapter;
import com.gerontechies.semonaid.Adapters.TopCategoriesAdapter;
import com.gerontechies.semonaid.Adapters.ViewPagerAdapter;
import com.gerontechies.semonaid.Models.Budget.EventItem;
import com.gerontechies.semonaid.Models.Budget.JobTips;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.R;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobsInstructionActivity extends AppCompatActivity {


    SemonaidDB db = null;
    List<JobTips> item;
    List<JobTips> allItemList = new ArrayList<>();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_instruction);
        setTitle("How to Apply to Jobs");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        rv = (RecyclerView) findViewById(R.id.rv_job_tips);

        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    }

    //getting values from db
    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllJobTips();
            if (!(item.isEmpty() || item == null) ){
                for (JobTips temp : item) {
                    allItemList.add(temp);
                }
            }
            return  status;
        }

        @Override
        protected void onPostExecute(String details) {
            if(allItemList.size()>1){
                Log.d("Data", "Loaded"+ String.valueOf(allItemList.size()));
                JobTipsAdapter adapter = new JobTipsAdapter(JobsInstructionActivity.this,  allItemList);
                RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(JobsInstructionActivity.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(mLayoutManagerIncome);
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setAdapter(adapter);
                rv.setNestedScrollingEnabled(true);
            } else{
                LoadData ld = new LoadData();
                ld.execute();

            }

        }

    }

    private class LoadData extends AsyncTask<Void, Void, String>  {

        @Override
        protected String doInBackground(Void... params) {


            JSONArray jsonArray = null;
            try {
                String jsonData = loadJSONFromAsset();
                jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    JobTips item = new JobTips();
                    int id = object.getInt("id");
                    String name = object.getString("title");
                    String desc = object.getString("desc");

                    item.setId(id);
                    item.setName(name);
                    item.setDesc(desc);

                    db.AppDAO().insertJobTipItem(item);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


        @Override
        protected void onPostExecute(String details) {
            //itemCount = 100;
            ReadDatabase rd = new ReadDatabase();
            rd.execute();
        }

    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("JobTips.json");
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


}
