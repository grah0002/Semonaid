package com.gerontechies.semonaid.Activities.MentalWellbeing.Yoga;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.R;

public class YogaList2Activity extends AppCompatActivity {

    TextView yogatext1, yogatext2, yogatext3, yogatext4, yogatext5, yogatext6, yogatext7;
    ImageView yogaimage1, yogaimage2, yogaimage3, yogaimage4, yogaimage5, yogaimage6, yogaimage7;
    CardView yogacard1, yogacard2, yogacard3, yogacard4, yogacard5, yogacard6, yogacard7;
    /*    RecyclerView yogaRV;
    YogaDatabase db = null;
    List<YogaItem> item ;
    String yogaName;

    List<YogaItem> allItemList = new ArrayList<>();
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_list2);
        setTitle("Yoga Techniques");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        yogacard1 = (CardView) findViewById(R.id.yogacard1);
        yogacard2 = (CardView) findViewById(R.id.yogacard2);
        yogacard3 = (CardView) findViewById(R.id.yogacard3);
        yogacard4 = (CardView) findViewById(R.id.yogacard4);
        yogacard5 = (CardView) findViewById(R.id.yogacard5);
        yogacard6 = (CardView) findViewById(R.id.yogacard6);
        yogacard7 = (CardView) findViewById(R.id.yogacard7);
        yogatext1 = (TextView) findViewById(R.id.yogatext1);
        yogatext2 = (TextView) findViewById(R.id.yogatext2);
        yogatext3 = (TextView) findViewById(R.id.yogatext3);
        yogatext4 = (TextView) findViewById(R.id.yogatext4);
        yogatext5 = (TextView) findViewById(R.id.yogatext5);
        yogatext6 = (TextView) findViewById(R.id.yogatext6);
        yogatext7 = (TextView) findViewById(R.id.yogatext7);
        yogaimage1 = (ImageView) findViewById((R.id.yogaImage1));
        yogaimage2 = (ImageView) findViewById((R.id.yogaImage2));
        yogaimage3 = (ImageView) findViewById((R.id.yogaImage3));
        yogaimage4 = (ImageView) findViewById((R.id.yogaImage4));
        yogaimage5 = (ImageView) findViewById((R.id.yogaImage5));
        yogaimage6 = (ImageView) findViewById((R.id.yogaImage6));
        yogaimage7 = (ImageView) findViewById((R.id.yogaImage7));

        yogacard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yogatext1.getVisibility() == View.GONE) {
                    yogatext1.setVisibility(View.VISIBLE);
                    yogaimage1.setVisibility(View.VISIBLE);
                } else if (yogatext1.getVisibility() == View.VISIBLE) {
                    yogatext1.setVisibility(View.GONE);
                    yogaimage1.setVisibility(View.GONE);
                }
            }
        });

        yogacard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yogatext2.getVisibility() == View.GONE) {
                    yogatext2.setVisibility(View.VISIBLE);
                    yogaimage2.setVisibility(View.VISIBLE);
                } else if (yogatext2.getVisibility() == View.VISIBLE) {
                    yogatext2.setVisibility(View.GONE);
                    yogaimage2.setVisibility(View.GONE);
                }
            }
        });

        yogacard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yogatext3.getVisibility() == View.GONE) {
                    yogatext3.setVisibility(View.VISIBLE);
                    yogaimage3.setVisibility(View.VISIBLE);
                } else if (yogatext3.getVisibility() == View.VISIBLE) {
                    yogatext3.setVisibility(View.GONE);
                    yogaimage3.setVisibility(View.GONE);
                }
            }
        });

        yogacard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yogatext4.getVisibility() == View.GONE) {
                    yogatext4.setVisibility(View.VISIBLE);
                    yogaimage4.setVisibility(View.VISIBLE);
                } else if (yogatext4.getVisibility() == View.VISIBLE) {
                    yogatext4.setVisibility(View.GONE);
                    yogaimage4.setVisibility(View.GONE);
                }
            }
        });

        yogacard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yogatext5.getVisibility() == View.GONE) {
                    yogatext5.setVisibility(View.VISIBLE);
                    yogaimage5.setVisibility(View.VISIBLE);
                } else if (yogatext5.getVisibility() == View.VISIBLE) {
                    yogatext5.setVisibility(View.GONE);
                    yogaimage5.setVisibility(View.GONE);
                }
            }
        });

        yogacard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yogatext6.getVisibility() == View.GONE) {
                    yogatext6.setVisibility(View.VISIBLE);
                    yogaimage6.setVisibility(View.VISIBLE);
                } else if (yogatext6.getVisibility() == View.VISIBLE) {
                    yogatext6.setVisibility(View.GONE);
                    yogaimage6.setVisibility(View.GONE);
                }
            }
        });

        yogacard7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yogatext7.getVisibility() == View.GONE) {
                    yogatext7.setVisibility(View.VISIBLE);
                    yogaimage7.setVisibility(View.VISIBLE);
                } else if (yogatext7.getVisibility() == View.VISIBLE) {
                    yogatext7.setVisibility(View.GONE);
                    yogaimage7.setVisibility(View.GONE);
                }
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
