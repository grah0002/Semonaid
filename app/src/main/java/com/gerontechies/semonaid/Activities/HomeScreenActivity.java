package com.gerontechies.semonaid.Activities;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gerontechies.semonaid.Activities.Budget.BudgetInfoActivity;
import com.gerontechies.semonaid.Activities.Budget.BudgetMainMenuActivity;
import com.gerontechies.semonaid.Activities.Budget.Tips.TipsMenuActivity;
import com.gerontechies.semonaid.Activities.Services.ServiceInfoActivity;
import com.gerontechies.semonaid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreenActivity extends AppCompatActivity {

    CardView budget_btn, saving_btn, service_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Semonaid");
        getSupportActionBar().hide();

        // budget_btn = (CardView) findViewById(R.id.budget_calculator);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);

//        budget_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeScreenActivity.this, BudgetInfoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        saving_btn = (CardView) findViewById(R.id.savings);
//
//        saving_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeScreenActivity.this, TipsMenuActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        service_btn = (CardView) findViewById(R.id.assistance);
//        service_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeScreenActivity.this, ServiceInfoActivity.class);
//                startActivity(intent);
//            }
//        });


        CardView getStarted = (CardView) findViewById(R.id.get_started);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, GetStartedActivity.class);
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

            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(textView);
        }


    }
