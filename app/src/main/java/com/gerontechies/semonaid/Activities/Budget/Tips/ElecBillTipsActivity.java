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
import androidx.core.content.res.ResourcesCompat;

import com.gerontechies.semonaid.R;

public class ElecBillTipsActivity extends AppCompatActivity {

    Button btn_elecbilltips1, btn_elecbilltips2, btn_elecbilltips3, btn_elecbilltips4, btn_elecbilltips5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elecbilltips);
        setTitle("Electricity Bill Savings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);

        btn_elecbilltips1 = (Button) findViewById(R.id.btn_elecbilltips1);

        btn_elecbilltips1.setTypeface(font);
        btn_elecbilltips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElecBillTipsActivity.this, ElecBillTips1Activity.class);
                startActivity(intent);
            }
        });

        btn_elecbilltips2 = (Button) findViewById(R.id.btn_elecbilltips2);
        btn_elecbilltips2.setTypeface(font);

        btn_elecbilltips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElecBillTipsActivity.this, ElecBillTips2Activity.class);
                startActivity(intent);
            }
        });
        btn_elecbilltips3 = (Button) findViewById(R.id.btn_elecbilltips3);

        btn_elecbilltips3.setTypeface(font);

        btn_elecbilltips3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElecBillTipsActivity.this, ElecBillTips3Activity.class);
                startActivity(intent);
            }
        });
        btn_elecbilltips4 = (Button) findViewById(R.id.btn_elecbilltips4);

        btn_elecbilltips4.setTypeface(font);

        btn_elecbilltips4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElecBillTipsActivity.this, ElecBillTips4Activity.class);
                startActivity(intent);
            }
        });
        btn_elecbilltips5 = (Button) findViewById(R.id.btn_elecbilltips5);

        btn_elecbilltips5.setTypeface(font);

        btn_elecbilltips5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElecBillTipsActivity.this, ElecBillTips5Activity.class);
                startActivity(intent);
            }
        });
    }


    //Custom title in the NavBar
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

}
