package com.gerontechies.semonaid.Activities.Budget.Calculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.Tips.MenuActivity;
import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.Income.T2T.OpshopListActivity;
import com.gerontechies.semonaid.Adapters.SavingTipsAdapter;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.TipItem;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import static com.gerontechies.semonaid.Activities.Budget.Calculator.SummaryActivity.expenseTotal;

public class TipsActivity extends AppCompatActivity {

    RecyclerView tipsRV;
    SemonaidDB db = null;
    List<TipItem> item ;
    String tipName;

    Button back, thriftStores;
    ImageView imgIcon;
    String uri;
    List<TipItem> allIatemList = new ArrayList<>();

    ImageView noRes;
    TextView searchTxt;
    String category;
    Button viewTips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        setTitle("Recommendations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tipsRV = (RecyclerView) findViewById(R.id.tips_list);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        TextView name = (TextView) findViewById(R.id.txt_category_name);
        TextView amt = (TextView) findViewById(R.id.income_amt);
        TextView expPercentage = (TextView) findViewById(R.id.exp_amt);

        imgIcon = (ImageView) findViewById(R.id.img_icon);
        searchTxt = (TextView) findViewById(R.id.txtSavings);
        noRes = (ImageView) findViewById(R.id.noRes);
        viewTips = (Button) findViewById(R.id.btn_tips);
        thriftStores = (Button) findViewById(R.id.btn_thrift_shops);

        viewTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this, MenuActivity.class);
                intent.putExtra("from_results", "yes");
                startActivity(intent);
            }
        });

        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TipsActivity.this.finish();
            }
        });

        tipName = getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("category");

        Log.d("THIF", "INNNN----" + category);
        if (tipName.equals("Clothing")) {
            Log.d("THIF", "INNNN");
            thriftStores.setVisibility(View.VISIBLE);

            thriftStores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TipsActivity.this, OpshopListActivity.class);
                    intent.putExtra("from_results", "yes");
                    startActivity(intent);
                }
            });
        } else {
            thriftStores.setVisibility(View.GONE);
        }


        String amtVal = getIntent().getStringExtra("amt");
        name.setText(tipName);
        amt.setText("$" + amtVal);

        double percentage = (Double.valueOf(amtVal) / expenseTotal) * 100;

        expPercentage.setText(String.format("%.2f", percentage));

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
            case  "Registration and licence":
                uri = "@drawable/auto";
                break;
            case  "Public Transport":
                uri = "@drawable/publictransport";
                break;
            case  "Other Transport Expenses":
                uri = "@drawable/other";
                break;
            default:
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
            item = db.AppDAO().getTipName(tipName);
            if (!(item.isEmpty() || item == null) ) {
                for (TipItem temp : item) {
                    allIatemList.add(temp);
                }

            } else {
                Log.d("IN", "insie" + category);
                item = db.AppDAO().getTipCategory(category);
                if (!(item.isEmpty() || item == null)) {
                    for (TipItem temp : item) {
                        allIatemList.add(temp);
                    }

                }
            }
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            if(allIatemList != null){

                if(allIatemList.size()>0){
                    SavingTipsAdapter adapter = new SavingTipsAdapter(TipsActivity.this,  allIatemList);
                    RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(TipsActivity.this, LinearLayoutManager.VERTICAL, false);
                    tipsRV.setLayoutManager(mLayoutManagerIncome);

                    tipsRV.setItemAnimator(new DefaultItemAnimator());
                    tipsRV.setAdapter(adapter);
                    tipsRV.setNestedScrollingEnabled(true);

                }
                //if there are no tips, show the no tips image
               else if(allIatemList.size()<=0 ){

                   noRes.setVisibility(View.VISIBLE);
                    searchTxt.setVisibility(View.GONE);
                    // searchTxt.setText("Oops, this is embarrassing! \nWe currently do not have any tips for this category! Please check out the general tips to find more Saving Tips!");
                }
            }
            else if(allIatemList.size()<=0 ){

                noRes.setVisibility(View.VISIBLE);
                searchTxt.setVisibility(View.GONE);
                // searchTxt.setText("Oops, this is embarrassing! \nWe currently do not have any tips for this category! Please check out the general tips to find more Saving Tips!");
            }



        }

    }

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
                        .setMessage("Tap on the tip you are interested in to find more details")
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
