package com.gerontechies.semonaid.Activities.Budget.Calculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.Budget.BudgetMainMenuActivity;
import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.BudgetItem;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {


    SemonaidDB db = null;
    private SharedPreferences calculatorPreferences;
    private SharedPreferences.Editor calculatorPrefEditor;
    Bundle BundleBudget;
    String CATEGORY = "Income";
    Bundle BudgetCalculator = new Bundle();
    List<BudgetItem> item;
    EditText income_edit_txt ,govt_edit_txt , other_edit_txt ;
    Spinner income_spinner,  govt_spinner, other_spinner ;
    double incomeTotal = 0.0;
    int TYPE = 100;
    List<BudgetItem> BudgetItemList = new ArrayList<>();
    int income_flag = 0, other_flag = 0, govt_flag = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setTitle("Budget Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        BundleBudget=intent.getExtras();

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        calculatorPreferences = getSharedPreferences("budgetCalculator", MODE_PRIVATE);
        calculatorPrefEditor = calculatorPreferences.edit();

        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
        //spinner items for frequency
        String[] items = new String[]{
                "Weekly", // 1
                "Fortnightly", // 2
                "Monthly", //3
                "Yearly" //4
        };


        income_spinner = (Spinner) findViewById(R.id.spinner_income);
         govt_spinner = (Spinner) findViewById(R.id.spinner_govt);
         other_spinner = (Spinner) findViewById(R.id.spinner_income_other);


        //setting the spinner item style
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,items
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        //setting the spinners content
        income_spinner.setAdapter(spinnerArrayAdapter);
        govt_spinner.setAdapter(spinnerArrayAdapter);
        other_spinner.setAdapter(spinnerArrayAdapter);



         income_edit_txt = (EditText) findViewById(R.id.editText2);
         govt_edit_txt = (EditText) findViewById(R.id.editText_govt);
         other_edit_txt = (EditText) findViewById(R.id.editText_income_other);


        //Setting the fonts for the edit texts
        income_edit_txt.setTypeface(font);
        govt_edit_txt.setTypeface(font);
        other_edit_txt.setTypeface(font);

        Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setTypeface(font);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                addToDb addToDb = new  addToDb();
                addToDb.execute();
            }
        });



    }

    private class addToDb extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String  income,  other_income, govt_income;
            income = income_edit_txt.getText().toString();
            other_income = other_edit_txt.getText().toString();
            govt_income = govt_edit_txt.getText().toString();

            //Spinner selected items

            String income_sp = income_spinner.getSelectedItem().toString();
            String other_sp = other_spinner.getSelectedItem().toString();
            String govt_sp = govt_spinner.getSelectedItem().toString();


            /* The below section checks to see if the value has already been inputted before. If so, it just edits it based on the
             * current selection.
             * Else it adds a new entry to the db with the values*/


            if(TextUtils.isEmpty(income)){
                if(income_flag==99){
                    BudgetItem budgetItem = new BudgetItem("Income-item",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(income_sp);

                BudgetItem budgetItem = new BudgetItem("Income-item",(Double.parseDouble(income)),frequency,TYPE,CATEGORY);
                if(income_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(income_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }



            if(TextUtils.isEmpty(other_income)){
                if(other_flag==99){
                    BudgetItem budgetItem = new BudgetItem("Other Income",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }

            } else {
                int frequency = getFrequency(other_sp);
                BudgetItem budgetItem = new BudgetItem("Other Income",Double.parseDouble(other_income),frequency,TYPE,CATEGORY);
                if(other_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(other_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }



            if(TextUtils.isEmpty(govt_income)){
                if(govt_flag==99){
                    BudgetItem budgetItem = new BudgetItem("Govt Income",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(govt_sp);
                BudgetItem budgetItem = new BudgetItem("Govt Income",Double.parseDouble(govt_income),frequency,TYPE,CATEGORY);
                if(govt_flag==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(govt_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }




            return null;
        }

        @Override
        protected void onPostExecute(String details) {
          //  IncomeActivity.this.finish();
            Intent intent = new Intent(IncomeActivity.this, BudgetMainMenuActivity.class);
            IncomeActivity.this.finish();

            startActivity(intent);


            Log.d("DB-ITEM","Added the values");
            // textView_insert.setText("Added Record: " + details);
        }
    }

    /*
     * Reading the db to get the already inputted values
     * */
    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
           item = db.AppDAO().getBudgetCategoryItems(CATEGORY);
            if (!(item.isEmpty() || item == null) ){
                for (BudgetItem temp : item) {

                    BudgetItemList.add(temp);
                }
            }

            return  "";
        }
        @Override
        protected void onPostExecute(String details) {
            for(int i=0; i<item.size(); i++){

                BudgetItem budgetItem = item.get(i);


                /*if the values exsist, setting the edit text and spinner to those values*/
                if(budgetItem.itemName.equals("Income-item")){
                    income_flag = 99;
                    income_edit_txt.setText(String.valueOf(budgetItem.amount));
                    income_spinner.setSelection(budgetItem.frequency-1);

                } else if(budgetItem.itemName.equals("Other Income")){
                    other_flag = 99;
                    other_edit_txt.setText(String.valueOf(budgetItem.amount));
                    other_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Govt Income")){
                    govt_flag = 99;
                    govt_edit_txt.setText(String.valueOf(budgetItem.amount));
                    govt_spinner.setSelection(budgetItem.frequency-1);
                }

            }

        }
    }

    //getting the frequency based on the spinner selection
    public int getFrequency(String frequency){

        int freq = 0;


        if(frequency.equals("Weekly")){
            freq = 1;
        } else if(frequency.equals("Fortnightly")){
            freq = 2;
        } else if(frequency.equals("Monthly")){
            freq = 3;
        } else if(frequency.equals("Yearly")){
            freq = 4;
        }

        return freq;
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
            this.finish();

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
