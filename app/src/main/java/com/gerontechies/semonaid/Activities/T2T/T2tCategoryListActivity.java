package com.gerontechies.semonaid.Activities.T2T;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.R;

public class T2tCategoryListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t2t_category_list);
        setTitle("Sources of Treasure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView general = (CardView) findViewById(R.id.t2t1);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(T2tCategoryListActivity.this, T2tCategoryActivity.class);
                intent.putExtra("t2t_category", "Bottle");
                startActivity(intent);
            }
        });

        CardView bills = (CardView) findViewById(R.id.t2t2);
        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(T2tCategoryListActivity.this, T2tCategoryActivity.class);
                intent.putExtra("t2t_category", "Paper");
                startActivity(intent);
            }
        });

        CardView banking = (CardView) findViewById(R.id.t2t3);
        banking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(T2tCategoryListActivity.this, T2tCategoryActivity.class);
                intent.putExtra("t2t_category", "Fabric");
                startActivity(intent);
            }
        });
        CardView travel = (CardView) findViewById(R.id.t2t4);
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(T2tCategoryListActivity.this, T2tCategoryActivity.class);
                intent.putExtra("t2t_category", "PillBottle");
                startActivity(intent);
            }
        });

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        } else if(id == R.id.homeIcon){
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
            finish();
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
