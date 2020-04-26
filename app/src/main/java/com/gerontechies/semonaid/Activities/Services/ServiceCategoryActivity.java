package com.gerontechies.semonaid.Activities.Services;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gerontechies.semonaid.R;

public class ServiceCategoryActivity extends AppCompatActivity {



    CardView clothes, showers, accom, health, food, hospitals, advise, counselling, drug, travel, jobs, helpline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_category);


        clothes = (CardView) findViewById(R.id.card_blankets);
        showers =  (CardView) findViewById(R.id.card_shower);
        accom = (CardView) findViewById(R.id.card_accom);
        health = (CardView) findViewById(R.id.card_health);
        food = (CardView) findViewById(R.id.card_food);
        hospitals = (CardView) findViewById(R.id.card_hospital);
        advise = (CardView) findViewById(R.id.card_legal);
        counselling = (CardView) findViewById(R.id.card_mental);
        drug = (CardView) findViewById(R.id.card_drug);
        travel = (CardView) findViewById(R.id.card_travel);
        jobs = (CardView) findViewById(R.id.card_emp);
        helpline = (CardView) findViewById(R.id.card_helpline);

        setTitle("Get Assistance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Clothes and Blankets";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        showers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Showers / Laundry";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        accom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Accommodation";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Helpline";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Health Services / Pharmacy";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Food";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Hospitals / Emergency";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Legal / Financial Advice";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        counselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Counselling and Psychiatric Services";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        drug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Drug and Alcohol";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Travel Assistance";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "Employment Assistance";
                Intent intent = new Intent(ServiceCategoryActivity.this, ServicesCategoryList.class);
                intent.putExtra(Intent.EXTRA_TEXT, category);
                startActivity(intent);
            }
        });

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
