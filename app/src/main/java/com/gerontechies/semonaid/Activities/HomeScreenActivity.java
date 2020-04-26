package com.gerontechies.semonaid.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budgeting.BudgetInfoActivity;
import com.gerontechies.semonaid.Activities.Budgeting.TipsMenuActivity;
import com.gerontechies.semonaid.R;

public class HomeScreenActivity extends AppCompatActivity {

    Button budget_btn, saving_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Semonaid");

        budget_btn = (Button) findViewById(R.id.button);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
               budget_btn.setTypeface(font);

        budget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, BudgetInfoActivity.class);
                startActivity(intent);
            }
        });

        saving_btn = (Button) findViewById(R.id.button2);
        saving_btn.setTypeface(font);

        saving_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, TipsMenuActivity.class);
                startActivity(intent);
            }
        });



    }

    //Custom title in the NavBar
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
