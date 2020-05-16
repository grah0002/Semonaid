package com.gerontechies.semonaid.Activities.Income.Skills;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SkillDetailItemActivity extends AppCompatActivity {

    Button btn_job;
    TextView job_desc, job_name;
    ChipGroup skill_group, certification_group;
    CardView cert_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_detail_item);
        setTitle("Job Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_job = (Button) findViewById(R.id.job_btn);
        job_desc = (TextView) findViewById(R.id.job_desc);
        job_name = (TextView) findViewById(R.id.job_name_txt);
        skill_group = (ChipGroup) findViewById(R.id.job_skill_chip);
        certification_group = (ChipGroup) findViewById(R.id.job_certifications_chip);
        cert_card = (CardView) findViewById(R.id.card_certification);
        Intent intent = getIntent();

        String id = getIntent().getStringExtra("id");
        final String jobName = getIntent().getStringExtra("job_name");
        String jobSkills = getIntent().getStringExtra("job_skills");
        String jobDesc = getIntent().getStringExtra("job_desc");

        String certifiation = getIntent().getStringExtra("job_certification");
        Log.d("CERT", certifiation);
        job_name.setText(jobName);
        try {
            addSkills(jobSkills);
            if(certifiation.equals("[]")){
                cert_card.setVisibility(View.GONE);
            } else{
                addCertification(certifiation);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




        String[] arrOfStr = jobDesc.split("\\.");

        for(int i=0; i< arrOfStr.length; i++)
        {
            if(i==arrOfStr.length-1) {
                job_desc.append("\u2014 "+arrOfStr[i]);
            } else{
                job_desc.append("\u2014 "+arrOfStr[i]+"\n\n");
            }

        }

        btn_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://au.indeed.com/jobs?q="+jobName+"&l=Victoria");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    public void addSkills(String skillList) throws JSONException {
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        JSONArray jsonArray = new JSONArray(skillList);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String name = object.getString("name");
            Chip chip = new Chip(this);
            chip.setTypeface(font);
            chip.setText(name);
            chip.setTextColor(this.getResources().getColorStateList(R.color.white));
            chip.setChipBackgroundColor(this.getResources().getColorStateList(R.color.colorPrimary));
            skill_group.addView(chip);


        }
    }
        public void addCertification(String cert) throws JSONException {
            Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
            JSONArray jsonArray = new JSONArray(cert);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String name = object.getString("name");
                Chip chip = new Chip(this);
                chip.setTypeface(font);
                chip.setText(name);
                chip.setTextColor(this.getResources().getColorStateList(R.color.white));
                chip.setChipBackgroundColor(this.getResources().getColorStateList(R.color.colorPrimary));
                certification_group.addView(chip);


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
