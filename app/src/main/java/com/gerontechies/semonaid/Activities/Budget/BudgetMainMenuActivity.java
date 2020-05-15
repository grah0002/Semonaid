package com.gerontechies.semonaid.Activities.Budget;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.Calculator.BillsActivity;
import com.gerontechies.semonaid.Activities.Budget.Calculator.HousingExpensesActivity;
import com.gerontechies.semonaid.Activities.Budget.Calculator.IncomeActivity;
import com.gerontechies.semonaid.Activities.Budget.Calculator.PersonalActivity;
import com.gerontechies.semonaid.Activities.Budget.Calculator.SummaryActivity;
import com.gerontechies.semonaid.Activities.Budget.Calculator.TransportActivity;
import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.BudgetItem;
import com.gerontechies.semonaid.R;

import java.util.List;

public class BudgetMainMenuActivity extends AppCompatActivity {

    int cat1;
    int cat2;
    ImageView iv_income, iv_add_utilities;
    CardView card_income, card_house_exp, card_bills, card_personal, card_transport;
    Button btn_next;

    List<BudgetItem> item;
    Double sum_income = 0.0, sum_house_exp = 0.0, sum_personal = 0.0, sum_bills = 0.0, sum_transport = 0.0;
    SemonaidDB db = null;
    TextView incomeTotalTxt, houseingTotalTxt, personalTotalTxt, transportTotalTxt, billsTotalTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_menu);
        setTitle("Budget Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        iv_income = (ImageView) findViewById(R.id.iv_add_income);

        card_income = (CardView) findViewById(R.id.card_income);
        card_house_exp = (CardView) findViewById(R.id.card_housing_exp);
        card_bills = (CardView) findViewById(R.id.card_utilities);
        card_personal = (CardView) findViewById(R.id.card_personal);
        card_transport = (CardView) findViewById(R.id.card_transport);

        incomeTotalTxt = (TextView) findViewById(R.id.txt_total_income);
        houseingTotalTxt = (TextView) findViewById(R.id.txt_total_housing_exp);
        billsTotalTxt = (TextView) findViewById(R.id.txt_total_utilities);
        personalTotalTxt = (TextView) findViewById(R.id.txt_total_personal);
        transportTotalTxt = (TextView) findViewById(R.id.txt_total_transport);


        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setTypeface(font);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetMainMenuActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });




        card_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetMainMenuActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });

        card_house_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetMainMenuActivity.this, HousingExpensesActivity.class);
                startActivity(intent);
            }
        });

        card_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetMainMenuActivity.this, BillsActivity.class);
                startActivity(intent);
            }
        });

        card_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetMainMenuActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        card_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetMainMenuActivity.this, TransportActivity.class);
                startActivity(intent);
            }
        });


    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            item = db.AppDAO().getAllBudgetData();
            if (!(item.isEmpty() || item == null)) {
                for (BudgetItem temp : item) {

                    // to check if the value item is weekly, fortnightly, monthly or yearly
                    int multiplier = 1;
                    if(temp.frequency == 1){
                        multiplier = 52;
                    } else if(temp.frequency == 2){
                        multiplier = 26;
                    } else if(temp.frequency == 3){
                        multiplier = 12;
                    } else if(temp.frequency == 4){
                        multiplier = 1;
                    }


                    if(temp.category.equals("Income")){
                        sum_income = sum_income + (temp.amount * multiplier );
                       // IncomeList.add(temp);
                    } else if(temp.category.equals("Household Expenses")){
                        sum_house_exp = sum_house_exp + (temp.amount * multiplier );
                    } else if(temp.category.equals("Utility Bills")){
                        sum_bills = sum_bills + (temp.amount * multiplier );
                    } else if(temp.category.equals("Personal Expenses")){
                        sum_personal = sum_personal + (temp.amount * multiplier );
                    } else if(temp.category.equals("Transport Expenses")){
                        sum_transport = sum_transport + (temp.amount * multiplier );
                    }


                }
            }

            if(sum_income != null){
                //Total : $
                incomeTotalTxt.setText("Total : $ "+ sum_income);
            }

            if(sum_house_exp != null){
                //Total : $
                houseingTotalTxt.setText("Total : $ "+ sum_house_exp);
            }

            if(sum_bills != null){
                //Total : $
                billsTotalTxt.setText("Total : $ "+ sum_bills);
            }

            if(sum_personal != null){
                //Total : $
                personalTotalTxt.setText("Total : $ "+ sum_personal);
            }

            if(sum_transport != null){
                //Total : $
                transportTotalTxt.setText("Total : $ "+ sum_transport);
            }


            return "";
        }

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


}
