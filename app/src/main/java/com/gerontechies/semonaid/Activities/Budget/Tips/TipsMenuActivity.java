package com.gerontechies.semonaid.Activities.Budget.Tips;

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
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.gerontechies.semonaid.R;

public class TipsMenuActivity extends AppCompatActivity {

    CardView btn_generaltips, btn_bankingtips, btn_traveltips, btn_othertips, btn_billstips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipsmenu);
        setTitle("Save My Earnings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_generaltips = (CardView) findViewById(R.id.saving_1);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        btn_generaltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, GeneralTipsActivity.class);
                startActivity(intent);
            }
        });

        btn_bankingtips = (CardView) findViewById(R.id.saving_3);
        btn_bankingtips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, BankingTipsActivity.class);
                startActivity(intent);
            }
        });

        btn_traveltips = (CardView) findViewById(R.id.saving_4);
        btn_traveltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, TravelTipsActivity.class);
                startActivity(intent);
            }
        });
        btn_othertips = (CardView) findViewById(R.id.saving_5);
        btn_othertips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsMenuActivity.this, OtherTipsActivity.class);
                startActivity(intent);
            }
        });
        btn_billstips = (CardView) findViewById(R.id.saving_2);
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
