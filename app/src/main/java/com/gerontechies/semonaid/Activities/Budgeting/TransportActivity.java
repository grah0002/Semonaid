package com.gerontechies.semonaid.Activities.Budgeting;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gerontechies.semonaid.Models.BudgetDatabase;
import com.gerontechies.semonaid.Models.BudgetItem;
import com.gerontechies.semonaid.R;

import java.util.ArrayList;
import java.util.List;

public class TransportActivity extends AppCompatActivity {

    BudgetDatabase db = null;

    private SharedPreferences calculatorPreferences;
    private SharedPreferences.Editor calculatorPrefEditor;
    Bundle BundleBudget;
    String CATEGORY = "Transport Expenses";
    Bundle BudgetCalculator = new Bundle();
    List<BudgetItem> item;

    EditText rego_edit_txt, public_edit_txt, other_edit_txt;
    Spinner rego_spinner, public_spinner,   other_spinner ;
    int TYPE = 999;

    List<BudgetItem> BudgetItemList = new ArrayList<>();

    int rego_flag = 0, public_flag = 0,   other_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        setTitle("Budget Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        BundleBudget=intent.getExtras();

        db = Room.databaseBuilder(this,
                BudgetDatabase.class, "budget_database")
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


        //setting the spinner item style
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,items
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);


        rego_spinner = (Spinner) findViewById(R.id.spinner_rego);
        public_spinner = (Spinner) findViewById(R.id.spinner_public);
        other_spinner = (Spinner) findViewById(R.id.spinner_other_transport);


        //setting the spinners content
        rego_spinner.setAdapter(spinnerArrayAdapter);
        public_spinner.setAdapter(spinnerArrayAdapter);
        other_spinner.setAdapter(spinnerArrayAdapter);


        rego_edit_txt = (EditText) findViewById(R.id.editText_rego);
        public_edit_txt = (EditText) findViewById(R.id.editText_public);
        other_edit_txt = (EditText) findViewById(R.id.editText_other_transport);


        //Setting the fonts for the edit texts
        rego_edit_txt.setTypeface(font);
        public_edit_txt.setTypeface(font);
        other_edit_txt.setTypeface(font);


        Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setTypeface(font);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                addToDb addToDb = new addToDb();
                addToDb.execute();
            }
        });

    }


    private class addToDb extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String  rego,  public_transport, other;
            rego = rego_edit_txt.getText().toString();
            public_transport = public_edit_txt.getText().toString();
            other = other_edit_txt.getText().toString();


            //Spinner selected items

            String rego_sp = rego_spinner.getSelectedItem().toString();
            String public_sp = public_spinner.getSelectedItem().toString();
            String other_sp = other_spinner.getSelectedItem().toString();


            //rego
            if(TextUtils.isEmpty(rego)){
                if(rego_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Rego and licence",0,1,TYPE,CATEGORY);
                    db.BudgetDAO().updateItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(rego_sp);
                BudgetItem budgetItem = new BudgetItem("Rego and licence",Double.parseDouble(rego),frequency,TYPE,CATEGORY);
                if(rego_flag == 99){
                    db.BudgetDAO().updateItem(budgetItem);
                } else if(rego_flag == 0){
                    db.BudgetDAO().insert(budgetItem);
                }

            }

            //public
            if(TextUtils.isEmpty(public_transport)){
                if(public_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Public Transport",0,1,TYPE,CATEGORY);
                    db.BudgetDAO().updateItem(budgetItem);
                }

            } else {
                int frequency = getFrequency(public_sp);
                BudgetItem budgetItem = new BudgetItem("Public Transport",Double.parseDouble(public_transport),frequency,TYPE,CATEGORY);
                if(public_flag == 99){
                    db.BudgetDAO().updateItem(budgetItem);
                } else if(public_flag == 0){
                    db.BudgetDAO().insert(budgetItem);
                }

            }


            //other
            if(TextUtils.isEmpty(other)){
                if(other_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Other Transport Expenses",0,1,TYPE,CATEGORY);
                    db.BudgetDAO().updateItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(other_sp);
                BudgetItem budgetItem = new BudgetItem("Other Transport Expenses",Double.parseDouble(other),frequency,TYPE,CATEGORY);
                if(other_flag ==99){
                    db.BudgetDAO().updateItem(budgetItem);
                } else if(other_flag == 0){
                    db.BudgetDAO().insert(budgetItem);
                }

            }




            return null;
        }

        @Override
        protected void onPostExecute(String details) {
            Intent intent = new Intent(TransportActivity.this, BudgetMainMenuActivity.class);


            startActivity(intent);
            finish();

            Log.d("DB-ITEM","Added the values");
            // textView_insert.setText("Added Record: " + details);
        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            item = db.BudgetDAO().getCategoryItems(CATEGORY);
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



                if(budgetItem.itemName.equals("Rego and licence")){
                    rego_flag = 99;
                    rego_edit_txt.setText(String.valueOf(budgetItem.amount));
                    rego_spinner.setSelection(budgetItem.frequency-1);

                } else if(budgetItem.itemName.equals("Public Transport")){
                    public_flag = 99;
                    public_edit_txt.setText(String.valueOf(budgetItem.amount));
                    public_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Other Transport Expenses")){
                    other_flag = 99;
                    other_edit_txt.setText(String.valueOf(budgetItem.amount));
                    other_spinner.setSelection(budgetItem.frequency-1);
                }

            }

        }
    }

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
