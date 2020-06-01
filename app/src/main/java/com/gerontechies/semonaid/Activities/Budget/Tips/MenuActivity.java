package com.gerontechies.semonaid.Activities.Budget.Tips;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.Income.T2T.OpshopListActivity;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

public class MenuActivity extends AppCompatActivity {
    String fromRes;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_saving_tip_menu);
        setTitle("Saving Tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back = (Button) findViewById(R.id.button_back2);

        fromRes = getIntent().getStringExtra("from_results");
        if (fromRes.equals("yes")) {

            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuActivity.this.finish();
                }
            });
        } else if (fromRes.equals("no")) {

            back.setVisibility(View.GONE);
        }
        CardView general = (CardView) findViewById(R.id.tip1);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TipCategoryActivity.class);
                intent.putExtra("tip_category", "General");
                startActivity(intent);
            }
        });

        CardView bills = (CardView) findViewById(R.id.tip2);
        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TipCategoryActivity.class);
                intent.putExtra("tip_category", "Bills");
                startActivity(intent);
            }
        });

        CardView banking = (CardView) findViewById(R.id.tip3);
        banking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TipCategoryActivity.class);
                intent.putExtra("tip_category", "Banking");
                startActivity(intent);
            }
        });
        CardView travel = (CardView) findViewById(R.id.tip4);
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TipCategoryActivity.class);
                intent.putExtra("tip_category", "Travel");
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

        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.homeIcon:
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.helpIcon:

                MaterialDialog mDialog = new MaterialDialog.Builder(this)
                        .setTitle("Help")
                        .setMessage("Tap on the category you want to learn to save on.")
                        .setCancelable(false)

                        .setPositiveButton("Close", R.drawable.close, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }

                        })


                        .build();

                // Show Dialog
                mDialog.show();

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
