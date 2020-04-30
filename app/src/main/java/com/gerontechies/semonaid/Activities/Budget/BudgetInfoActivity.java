package com.gerontechies.semonaid.Activities.Budget;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.Tips.TipsMenuActivity;
import com.gerontechies.semonaid.Activities.Services.ServiceInfoActivity;
import com.gerontechies.semonaid.R;

public class BudgetInfoActivity extends AppCompatActivity {

    Button btn_next;
    CardView budget_btn, saving_btn, service_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_info);
        setTitle("Budget Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//
//        btn_next = (Button) findViewById(R.id.btn_next);
//        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
//        btn_next.setTypeface(font);
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(BudgetInfoActivity.this, BudgetMainMenuActivity.class);
//                startActivity(intent);
//            }
//        });

         budget_btn = (CardView) findViewById(R.id.budget_calculator);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);

        budget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, BudgetMainMenuActivity.class);
                startActivity(intent);
            }
        });

        saving_btn = (CardView) findViewById(R.id.savings);

        saving_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, TipsMenuActivity.class);
                startActivity(intent);
            }
        });

        service_btn = (CardView) findViewById(R.id.assistance);
        service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, ServiceInfoActivity.class);
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
