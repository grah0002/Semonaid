package com.gerontechies.semonaid.Activities.Budget.Calculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Services.ServiceInfoActivity;
import com.gerontechies.semonaid.Adapters.SavingTipsAdapter;
import com.gerontechies.semonaid.Adapters.TopCategoriesAdapter;
import com.gerontechies.semonaid.Models.ServiceDatabase;
import com.gerontechies.semonaid.Models.ServiceItem;
import com.gerontechies.semonaid.Models.TipDatabase;
import com.gerontechies.semonaid.Models.TipItem;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {

    RecyclerView tipsRV;
    TipDatabase db = null;
    ArrayList<TipItem> item = new ArrayList<>();
    String tipName;
    String name;

    ImageView imgIcon;
    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        setTitle("Recommendations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tipsRV = (RecyclerView) findViewById(R.id.tips_list);

        db = Room.databaseBuilder(this,
                TipDatabase.class, "tips_database")
                .fallbackToDestructiveMigration()
                .build();

        TextView name = (TextView) findViewById(R.id.txt_category_name);
        TextView amt = (TextView) findViewById(R.id.txt_amt);
        imgIcon = (ImageView) findViewById(R.id.img_icon);

        tipName = getIntent().getStringExtra("name");
        String amtVal = getIntent().getStringExtra("amt");
        name.setText(tipName);
        amt.setText("$" +amtVal + " spent yearly");

        Log.d("LL", tipName);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        //need to set the image icon based on the selectedCategory

        switch (tipName){
            case "Rent":
                uri = "@drawable/rent";
                break;
            case  "Groceries":
                uri = "@drawable/groceries";
                break;
            case "Other Expense":
                uri = "@drawable/other";
                break;
            case  "Gas Bill":
                uri = "@drawable/gas";
                break;
            case "Water Bill":
                uri = "@drawable/water";
                break;
            case  "Phone Bill":
                uri = "@drawable/phone";
                break;
            case  "Electricity Bill":
                uri = "@drawable/electricty";
                break;
            case  "Internet Bill":
                uri = "@drawable/internet";
                break;
            case  "Insurance":
                uri = "@drawable/insurance";
                break;
            case  "Loans":
                uri = "@drawable/loans";
                break;
            case  "Other Household Expense":
                uri = "@drawable/other";
                break;
            case  "Clothing":
                uri = "@drawable/clothing";
                break;
            case  "Doctor and Medicines":
                uri = "@drawable/medical";
                break;
            case  "Entertainment":
                uri = "@drawable/entertainment";
                break;
            case  "Pets":
                uri = "@drawable/petcare";
                break;
            case  "Other Personal Bills":
                uri = "@drawable/other";
                break;
            case  "Rego and licence":
                uri = "@drawable/auto";
                break;
            case  "Public transport":
                uri = "@drawable/publictransport";
                break;
            case  "Other Transport Expenses":
                uri = "@drawable/other";
                break;
        }

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        imgIcon.setImageDrawable(res);


    }
    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            TipItem item1 = db.TipDAO().getItem(tipName);
            item.add(item1);
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            SavingTipsAdapter adapter = new SavingTipsAdapter(TipsActivity.this,  item);
            RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(TipsActivity.this, LinearLayoutManager.VERTICAL, false);
            tipsRV.setLayoutManager(mLayoutManagerIncome);

            tipsRV.setItemAnimator(new DefaultItemAnimator());
            tipsRV.setAdapter(adapter);
            tipsRV.setNestedScrollingEnabled(false);





        }

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
