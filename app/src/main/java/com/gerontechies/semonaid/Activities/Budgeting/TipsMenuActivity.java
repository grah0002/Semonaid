package com.gerontechies.semonaid.Activities.Budgeting;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gerontechies.semonaid.R;

public class TipsMenuActivity extends AppCompatActivity {

    Button btn_generaltips, btn_bankingtips, btn_traveltips, btn_othertips, btn_billstips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipsmenu);
        setTitle("Categories of Savings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_generaltips = (Button) findViewById(R.id.btn_generaltips);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        btn_generaltips.setTypeface(font);
        btn_generaltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, GeneralTipsActivity.class);
                startActivity(intent);
            }
        });

        btn_bankingtips = (Button) findViewById(R.id.btn_bankingtips);
        btn_bankingtips.setTypeface(font);
        btn_bankingtips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, BankingTipsActivity.class);
                startActivity(intent);
            }
        });

        btn_traveltips = (Button) findViewById(R.id.btn_traveltips);
        btn_traveltips.setTypeface(font);
        btn_traveltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, TravelTipsActivity.class);
                startActivity(intent);
            }
        });
        btn_othertips = (Button) findViewById(R.id.btn_othertips);
        btn_othertips.setTypeface(font);
        btn_othertips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, OtherTipsActivity.class);
                startActivity(intent);
            }
        });
        btn_billstips = (Button) findViewById(R.id.btn_billstips);
        btn_billstips.setTypeface(font);
        btn_billstips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, BillsTipsMenuActivity.class);
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
