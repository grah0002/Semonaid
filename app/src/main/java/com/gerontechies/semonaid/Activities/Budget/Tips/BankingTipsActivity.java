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

public class BankingTipsActivity extends AppCompatActivity {

    Button btn_bankingtips1, btn_bankingtips2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankingtips);
        setTitle("Banking Tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_bankingtips1 = (Button) findViewById(R.id.btn_bankingtips1);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);



        btn_bankingtips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankingTipsActivity.this, BankingTips1Activity.class);
                startActivity(intent);
            }
        });

        btn_bankingtips2 = (Button) findViewById(R.id.btn_bankingtips2);
        btn_bankingtips1.setTypeface(font);
        btn_bankingtips2.setTypeface(font);
        btn_bankingtips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankingTipsActivity.this, BankingTips2Activity.class);
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
