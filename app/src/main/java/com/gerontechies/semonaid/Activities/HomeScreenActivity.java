package com.gerontechies.semonaid.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.BudgetInfoActivity;
import com.gerontechies.semonaid.Activities.Income.IncomeMenuActivity;
import com.gerontechies.semonaid.Activities.Income.Skills.SkillsQuizActivity;
import com.gerontechies.semonaid.Activities.MentalWellbeing.MentalMenuActivity;
import com.gerontechies.semonaid.Adapters.ViewPagerAdapter;
import com.gerontechies.semonaid.R;

import java.util.Timer;
import java.util.TimerTask;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Semonaid");
        getSupportActionBar().hide();

        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);



        CardView getStarted = (CardView) findViewById(R.id.card_save);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, BudgetInfoActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        CardView income = (CardView) findViewById(R.id.card_inc);
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, IncomeMenuActivity.class);
                startActivity(intent);
            }
        });

        CardView mental = (CardView) findViewById(R.id.card_mental);
        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, MentalMenuActivity.class);
                startActivity(intent);
            }
        });





    }

         //Custom title in the NavBar
        public void setTitle (String title){
            Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);


            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView textView = new TextView(this);
            textView.setText(title);
            textView.setTypeface(font);
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(R.color.white));

            textView.setGravity(Gravity.CENTER);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(textView);
        }


    }
