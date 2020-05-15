package com.gerontechies.semonaid.Activities.Skills;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.JobAdapter;
import com.gerontechies.semonaid.Adapters.SkillListAdapter;
import com.gerontechies.semonaid.Models.Budget.JobContentItem;
import com.gerontechies.semonaid.Models.Budget.JobItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SkillsQuizActivity extends AppCompatActivity  {

    RecyclerView skillsRV, certRV;
    SemonaidDB db = null;
    Button skills_btn, certifications_btn, back_btn;
    List<JobContentItem> allCertficateItems = new ArrayList<>();
    List<JobContentItem> allSkillsItem = new ArrayList<>();
    List<String> certficationsList = new ArrayList<>();
    List<String> skillsList = new ArrayList<>();
    List<JobContentItem> certificationObjList = new ArrayList<>();
    List<JobContentItem> skillsObjList = new ArrayList<>();
    CardView skills, certifications;

    //Reference - http://www.codeplayon.com/2019/10/how-to-get-list-of-checked-checkboxes-from-recyclerview-android/
    public static HashSet<String>  selectedSkillsList=new HashSet<>();
    public static  HashSet<String>selectedCertfificationsList=new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_quiz);
        setTitle("Saving Tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();



        skills = (CardView) findViewById(R.id.skills_card);
        certifications = (CardView) findViewById(R.id.certifications_card);

        skillsRV = (RecyclerView) findViewById(R.id.skillsRV);
        certRV = (RecyclerView) findViewById(R.id.certficationsRV);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        skills_btn = (Button) findViewById(R.id.button2);
        skills_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                skills.setVisibility(View.GONE);
                certifications.setVisibility(View.VISIBLE);
                //Intent intent = new Intent(SkillsQuizActivity.this, SkillListingActivity.class);
                //startActivity(intent);
            }
        });


        back_btn = (Button) findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certifications.setVisibility(View.GONE);
                skills.setVisibility(View.VISIBLE);

            }
        });
        certifications_btn = (Button) findViewById(R.id.button22);
        certifications_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(SkillsQuizActivity.this, SkillListingActivity.class);
                startActivity(intent);
            }
        });
    }


    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            certificationObjList = db.AppDAO().getAllJobContentItems("certification");
            skillsObjList = db.AppDAO().getAllJobContentItems("skills");
            if (!(certificationObjList.isEmpty() || certificationObjList == null)) {
                for (JobContentItem temp : certificationObjList) {
                    allCertficateItems.add(temp);
                }

            }

            if (!(skillsObjList.isEmpty() || skillsObjList == null)) {
                for (JobContentItem temp : skillsObjList) {
                    allSkillsItem.add(temp);
                }

            } else{

                LoadData ld = new LoadData();
                ld.execute();
            }

            return status;
        }


        @Override
        protected void onPostExecute(String details) {

            if (allCertficateItems != null) {

                if (allCertficateItems.size() > 0) {


                    SkillListAdapter adapter = new SkillListAdapter(SkillsQuizActivity.this, allCertficateItems);
                    RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(SkillsQuizActivity.this, LinearLayoutManager.VERTICAL, false);
                    certRV.setLayoutManager(mLayoutManagerIncome);

                    certRV.setItemAnimator(new DefaultItemAnimator());
                    certRV.setAdapter(adapter);

                    SkillListAdapter adapter1 = new SkillListAdapter(SkillsQuizActivity.this, allSkillsItem);
                    RecyclerView.LayoutManager mLayoutManagerIncome1 = new LinearLayoutManager(SkillsQuizActivity.this, LinearLayoutManager.VERTICAL, false);
                    skillsRV.setLayoutManager(mLayoutManagerIncome1);

                    skillsRV.setItemAnimator(new DefaultItemAnimator());
                    skillsRV.setAdapter(adapter1);
                    //tipsRV.setNestedScrollingEnabled(false);

                }


            }

        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("JobSkills.json");
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

                    JobItem item = new JobItem();
                    int id = object.getInt("id");
                    String name = object.getString("job_name");
                    String category = object.getString("category");
                    String desc = object.getString("desc");
                    String skills = object.getString("skills");
                    String certifications = object.getString("certification");
                    item.setId(id);
                    item.setName(name);
                    item.setCategory(category);
                    item.setSkills(skills);
                    item.setDesc(desc);
                    item.setCertifications(certifications);
                    Log.d("ERR", String.valueOf(id) + "------"+ name);

                    db.AppDAO().insertJobs(item);
                    if(certifications.equals("[]")){

                    } else{
                        JSONArray certificationsArray = new JSONArray(certifications);
                        Log.d("ERR", String.valueOf(certificationsArray.length()));
                        for (int j = 0; j < certificationsArray.length(); j++) {
                            JSONObject object1 = certificationsArray.getJSONObject(j);
                            String certName = object1.getString("name");
                            certficationsList.add(certName);

                        }
                    }
                    if(skills.equals("[]")){

                    } else{
                        JSONArray sList = new JSONArray(skills);
                        Log.d("ERR", String.valueOf(sList.length()));
                        for (int j = 0; j < sList.length(); j++) {
                            JSONObject object1 = sList.getJSONObject(j);
                            String sName = object1.getString("name");
                            skillsList.add(sName);

                        }
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


            @Override
        protected void onPostExecute(String details) {
            if(certficationsList.size()>0){


                InsertCertifications ic = new InsertCertifications();
                ic.execute();
            }

        }

    }
    private class InsertCertifications extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            HashSet<String> hashCertficationsList = new HashSet(certficationsList);
            HashSet<String> hashSkillsList = new HashSet(skillsList);
            for(String name : hashCertficationsList){
                JobContentItem jobContentItem = new JobContentItem();
                jobContentItem.setName(name);
                jobContentItem.setType("certification");
                db.AppDAO().insertSkills(jobContentItem);
            }
            for(String name : hashSkillsList){
                JobContentItem jobContentItem = new JobContentItem();
                jobContentItem.setName(name);
                jobContentItem.setType("skills");
                db.AppDAO().insertSkills(jobContentItem);
            }

            return status;
        }


        @Override
        protected void onPostExecute(String details) {

            ReadDatabase rd = new ReadDatabase();
            rd.execute();

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
            } else if (id == R.id.homeIcon) {
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        public void setTitle(String title) {
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

