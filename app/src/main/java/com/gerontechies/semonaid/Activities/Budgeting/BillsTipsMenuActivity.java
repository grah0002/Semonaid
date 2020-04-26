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

public class BillsTipsMenuActivity extends AppCompatActivity {

    Button btn_elecbilltips, btn_waterbilltips, btn_gasbilltips, btn_foodbilltips, btn_mobilebilltips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billstipsmenu);
        setTitle("Save on Bills");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_elecbilltips = (Button) findViewById(R.id.btn_elecbilltips);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        btn_elecbilltips.setTypeface(font);
        btn_elecbilltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillsTipsMenuActivity.this, ElecBillTipsActivity.class);
                startActivity(intent);
            }
        });

        btn_waterbilltips = (Button) findViewById(R.id.btn_waterbilltips);
        btn_waterbilltips.setTypeface(font);
        btn_waterbilltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillsTipsMenuActivity.this, WaterBillTipsActivity.class);
                startActivity(intent);
            }
        });

        btn_gasbilltips = (Button) findViewById(R.id.btn_gasbilltips);
        btn_gasbilltips.setTypeface(font);
        btn_gasbilltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillsTipsMenuActivity.this, GasBillTipsActivity.class);
                startActivity(intent);
            }
        });
        btn_foodbilltips = (Button) findViewById(R.id.btn_foodbilltips);
        btn_foodbilltips.setTypeface(font);
        btn_foodbilltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillsTipsMenuActivity.this, FoodBillTipsActivity.class);
                startActivity(intent);
            }
        });
        btn_mobilebilltips = (Button) findViewById(R.id.btn_mobilebilltips);
        btn_mobilebilltips.setTypeface(font);
        btn_mobilebilltips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillsTipsMenuActivity.this, MobileBillTipsActivity.class);
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
