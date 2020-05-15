package com.gerontechies.semonaid.Activities.Skills;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.JobAdapter;
import com.gerontechies.semonaid.Models.Budget.JobContentItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.JobItem;
import com.gerontechies.semonaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gerontechies.semonaid.Activities.Skills.SkillsQuizActivity.selectedCertfificationsList;
import static com.gerontechies.semonaid.Activities.Skills.SkillsQuizActivity.selectedSkillsList;

public class SkillListingActivity extends AppCompatActivity {

    RecyclerView tipsRV;
    SemonaidDB db = null;
    List<JobItem> jobItems;
    List<JobItem> allJobItems = new ArrayList<>();
    List<JobItem> allJobItems1 = new ArrayList<>();

    ArrayList<JobContentItem> skillList;
    ArrayList<JobContentItem> certificationList;
    HashSet<String> hashList = new HashSet();
    ArrayList<JobItem> selectedJobs  = new ArrayList<>();
    public static HashSet<String> selectedSkillsList = new HashSet<>();
    boolean isFilterd =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_listing);

        setTitle("Job Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

//        skillList = selectedSkillsList;
//        certificationList = selectedCertfificationsList;


//        if(skillList.size()>0){
//            for(int i=0; i<skillList.size(); i++){
//                JobContentItem s1 = skillList.get(i);
//                selectedItemsHashList.add(s1.name);
//            }
//        } else{
//            Log.d("JOB", "Empty List");
//        }
//        if(certificationList.size()>0){
//            for(int i=0; i<certificationList.size(); i++){
//                JobContentItem s1 = certificationList.get(i);
//                selectedItemsHashList.add(s1.name);
//            }
//        } else{
//            Log.d("JOB", "Empty List");
//        }

        tipsRV = (RecyclerView) findViewById(R.id.rv);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            jobItems = db.AppDAO().getAllJobs();
            if (!(jobItems.isEmpty() || jobItems == null)) {
                for (JobItem temp : jobItems) {
                    allJobItems.add(temp);
                }
                if(selectedSkillsList.size()>0 || selectedCertfificationsList.size()>0){
                    isFilterd=true;
                    for(JobItem jobItem: allJobItems )

                    {
                        Log.d("ERR", "------" + jobItem.name);
                        try {
                            JSONArray skills = new JSONArray(jobItem.getSkills());
                            JSONArray certifications = new JSONArray(jobItem.getCertifications());

                            for (int j = 0; j < skills.length(); j++) {

                                JSONObject object1 = skills.getJSONObject(j);
                                String skillName = object1.getString("name");


                                if(selectedSkillsList.contains(skillName)){
                                    allJobItems1.add(jobItem);
                                    Log.d("ERR", "matched------" + skillName);
                                } else{
                                    Log.d("ERR", "no matched------" );
                                }
                            }

                            for (int j = 0; j < certifications.length(); j++) {

                                JSONObject object1 = certifications.getJSONObject(j);
                                String skillName = object1.getString("name");
                                Log.d("ERR", "------" + skillName);

                                if(selectedCertfificationsList.contains(skillName)){
                                    allJobItems1.add(jobItem);
                                    Log.d("ERR", "matched------" + skillName);
                                } else{
                                    Log.d("ERR", "no ------" );
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                } else{
                    isFilterd = false;
                }

            }
            return status;
        }


        @Override
        protected void onPostExecute(String details) {


            if (allJobItems != null) {

                if (isFilterd) {


                    Log.d("ERR", "some----" + String.valueOf(allJobItems1.size()));
                    JobAdapter adapter = new JobAdapter(SkillListingActivity.this, allJobItems1);
                    RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(SkillListingActivity.this, LinearLayoutManager.VERTICAL, false);
                    tipsRV.setLayoutManager(mLayoutManagerIncome);

                    tipsRV.setItemAnimator(new DefaultItemAnimator());
                    tipsRV.setAdapter(adapter);
                }
                else{
                    Log.d("ERR", "all----" + String.valueOf(allJobItems.size()));
                    JobAdapter adapter = new JobAdapter(SkillListingActivity.this, allJobItems);
                    RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(SkillListingActivity.this, LinearLayoutManager.VERTICAL, false);
                    tipsRV.setLayoutManager(mLayoutManagerIncome);

                    tipsRV.setItemAnimator(new DefaultItemAnimator());
                    tipsRV.setAdapter(adapter);
                }



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
