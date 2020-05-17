package com.gerontechies.semonaid.Activities.Income.Skills;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.SkillListAdapter;
import com.gerontechies.semonaid.Models.Budget.JobContentItem;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

import static com.gerontechies.semonaid.Activities.Income.Skills.SkillsQuizActivity.selectedCertfificationsList;
import static com.gerontechies.semonaid.Activities.Income.Skills.SkillsQuizActivity.selectedSkillsList;

public class SkillsFilterActivity extends AppCompatActivity {

    RecyclerView skills_rv, certification_rv;
    SemonaidDB db = null;
    Button save_btn, reset_btn;
    List<JobContentItem> certificationObjList = new ArrayList<>();
    List<JobContentItem> skillsObjList = new ArrayList<>();
    List<JobContentItem> allCertficateItems = new ArrayList<>();
    List<JobContentItem> allSkillsItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_filter);
        setTitle("Job Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        skills_rv = (RecyclerView) findViewById(R.id.skills_rv);
        certification_rv = (RecyclerView) findViewById(R.id.certifications_rv);

        save_btn = (Button) findViewById(R.id.btn_save);
        reset_btn = (Button) findViewById(R.id.btn_reset);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SkillsFilterActivity.this, SkillListingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SkillsFilterActivity.this);
                builder.setTitle("Reset");
                builder.setMessage("Are you sure you want to reset all the values?");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectedCertfificationsList.clear();
                        selectedSkillsList.clear();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();


        ReadDatabase rd = new ReadDatabase();
        rd.execute();

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

            }

            return status;
        }


        @Override
        protected void onPostExecute(String details) {

            if (allCertficateItems != null) {

                if (allCertficateItems.size() > 0) {


                    //setting the adapters with the skills and certifications values
                    SkillListAdapter adapter = new SkillListAdapter(SkillsFilterActivity.this, allCertficateItems);
                    RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(SkillsFilterActivity.this, LinearLayoutManager.VERTICAL, false);
                    certification_rv.setLayoutManager(mLayoutManagerIncome);

                    certification_rv.setItemAnimator(new DefaultItemAnimator());
                    certification_rv.setAdapter(adapter);
                    certification_rv.setNestedScrollingEnabled(true);

                    SkillListAdapter adapter1 = new SkillListAdapter(SkillsFilterActivity.this, allSkillsItem);
                    RecyclerView.LayoutManager mLayoutManagerIncome1 = new LinearLayoutManager(SkillsFilterActivity.this, LinearLayoutManager.VERTICAL, false);
                    skills_rv.setLayoutManager(mLayoutManagerIncome1);

                    skills_rv.setItemAnimator(new DefaultItemAnimator());
                    skills_rv.setAdapter(adapter1);
                    skills_rv.setNestedScrollingEnabled(true);

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
