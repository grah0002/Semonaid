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
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class BillsActivity extends AppCompatActivity {
    SemonaidDB db = null;
    private SharedPreferences calculatorPreferences;
    private SharedPreferences.Editor calculatorPrefEditor;
    Bundle BundleBudget;
    String CATEGORY = "Utility Bills";
    List<BudgetItem> item;
    EditText gas_edit_txt, water_edit_txt, electricity_edit_txt, phone_edit_txt, insurance_edit_txt, loans_edit_txt, other_edit_txt, internet_edit_txt;
    Spinner gas_spinner, water_spinner, electricity_spinner , phone_spinner, insurance_spinner, loans_spinner, other_spinner, internet_spinner ;
    double incomeTotal = 0.0;
    int TYPE = 999;
    List<BudgetItem> BudgetItemList = new ArrayList<>();
    int gas_flag = 0, water_flag = 0, electricity_flag = 0, phone_flag = 0, insurance_flag = 0, loans_flag = 0, other_flag=0, internet_flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

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


        gas_spinner = (Spinner) findViewById(R.id.spinner_gas);
        water_spinner = (Spinner) findViewById(R.id.spinner_water);
        electricity_spinner = (Spinner) findViewById(R.id.spinner_electricity);
        phone_spinner = (Spinner) findViewById(R.id.spinner_phone);
        insurance_spinner = (Spinner) findViewById(R.id.spinner_insurance);
        loans_spinner = (Spinner) findViewById(R.id.spinner_loans);
        other_spinner = (Spinner) findViewById(R.id.spinner_bills_other);
        internet_spinner = (Spinner) findViewById(R.id.spinner_internet) ;


        //setting the spinner item style
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,items
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        //setting the spinners content
        gas_spinner.setAdapter(spinnerArrayAdapter);
        water_spinner.setAdapter(spinnerArrayAdapter);
        electricity_spinner.setAdapter(spinnerArrayAdapter);
        phone_spinner.setAdapter(spinnerArrayAdapter);
        insurance_spinner.setAdapter(spinnerArrayAdapter);
        loans_spinner.setAdapter(spinnerArrayAdapter);
        other_spinner.setAdapter(spinnerArrayAdapter);
        internet_spinner.setAdapter(spinnerArrayAdapter);


        //setting the textbox items
        gas_edit_txt = (EditText) findViewById(R.id.editText_gas);
        water_edit_txt = (EditText) findViewById(R.id.editText_water);
        electricity_edit_txt = (EditText) findViewById(R.id.editText_electricity);
        phone_edit_txt = (EditText) findViewById(R.id.editText_phone);
        insurance_edit_txt = (EditText) findViewById(R.id.editText_insurance);
        loans_edit_txt = (EditText) findViewById(R.id.editText_loans);
        other_edit_txt = (EditText) findViewById(R.id.editText_bills_other);
        internet_edit_txt = (EditText) findViewById(R.id.editText_internet);


        //Setting the fonts for the edit texts
        gas_edit_txt.setTypeface(font);
        water_edit_txt.setTypeface(font);
        electricity_edit_txt.setTypeface(font);
        phone_edit_txt.setTypeface(font);
        insurance_edit_txt.setTypeface(font);
        loans_edit_txt.setTypeface(font);
        other_edit_txt.setTypeface(font);
        internet_edit_txt.setTypeface(font);


        Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setTypeface(font);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adding to db on button click
                addToDb addToDb = new addToDb();
                addToDb.execute();
            }
        });

    }

    private class addToDb extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            //getting the input values on button click
            String  gas,  water, electricity, phone, internet, insurance, loans, other;
            gas = gas_edit_txt.getText().toString();
            water = electricity_edit_txt.getText().toString();
            electricity = water_edit_txt.getText().toString();
            phone = phone_edit_txt.getText().toString();
            internet = internet_edit_txt.getText().toString();
            insurance = insurance_edit_txt.getText().toString();
            loans = loans_edit_txt.getText().toString();
            other = other_edit_txt.getText().toString();


            //getting the frequency values
            String gas_sp = gas_spinner.getSelectedItem().toString();
            String water_sp = water_spinner.getSelectedItem().toString();
            String electricity_sp = electricity_spinner.getSelectedItem().toString();
            String phone_sp = phone_spinner.getSelectedItem().toString();
            String internet_sp = internet_spinner.getSelectedItem().toString();
            String insurance_sp = insurance_spinner.getSelectedItem().toString();
            String loans_sp = loans_spinner.getSelectedItem().toString();
            String other_sp = other_spinner.getSelectedItem().toString();



       /* The below section checks to see if the value has already been inputted before. If so, it just edits it based on the
       * current selection.
       * Else it adds a new entry to the db with the values*/

            //gas
            if(TextUtils.isEmpty(gas)){
                if(gas_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Gas Bill",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                incomeTotal = incomeTotal + Double.parseDouble(gas);
                int frequency = getFrequency(gas_sp);
                BudgetItem budgetItem = new BudgetItem("Gas Bill",Double.parseDouble(gas),frequency,TYPE,CATEGORY);
                if(gas_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(gas_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);

                }

            }

            //water
            if(TextUtils.isEmpty(water)){
                if(water_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Water Bill",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }

            } else {
                incomeTotal = incomeTotal + Double.parseDouble(water);
                int frequency = getFrequency(water_sp);
                BudgetItem budgetItem = new BudgetItem("Water Bill",Double.parseDouble(water),frequency,TYPE,CATEGORY);
                if(water_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(water_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }



            //electricity
            if(TextUtils.isEmpty(electricity)){
                if(electricity_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Electricity Bill",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                incomeTotal = incomeTotal + Double.parseDouble(electricity);
                int frequency = getFrequency(electricity_sp);
                BudgetItem budgetItem = new BudgetItem("Electricity Bill",Double.parseDouble(electricity),frequency,TYPE,CATEGORY);
                if(electricity_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(electricity_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }

            //phone
            if(TextUtils.isEmpty(phone)){
                if(phone_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Phone Bill",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(phone_sp);
                BudgetItem budgetItem = new BudgetItem("Phone Bill",Double.parseDouble(phone),frequency,TYPE,CATEGORY);
                if(phone_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(phone_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }

            //internet
            if(TextUtils.isEmpty(internet)){
                if(internet_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Internet Bill",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(internet_sp);
                BudgetItem budgetItem = new BudgetItem("Internet Bill",Double.parseDouble(internet),frequency,TYPE,CATEGORY);
                if(internet_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(internet_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }

            //insurance
            if(TextUtils.isEmpty(insurance)){
                if(insurance_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Insurance",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(insurance_sp);
                BudgetItem budgetItem = new BudgetItem("Insurance",Double.parseDouble(insurance),frequency,TYPE,CATEGORY);
                if(insurance_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(insurance_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }

            //loans
            if(TextUtils.isEmpty(loans)){
                if(loans_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Loans",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(loans_sp);
                BudgetItem budgetItem = new BudgetItem("Loans",Double.parseDouble(loans),frequency,TYPE,CATEGORY);
                if(loans_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(loans_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }

            //other
            if(TextUtils.isEmpty(loans)){
                if(other_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Other Bills",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(other_sp);
                BudgetItem budgetItem = new BudgetItem("Other Bills",Double.parseDouble(other),frequency,TYPE,CATEGORY);
                if(other_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(other_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String details) {
            //BillsActivity.this.finish();
            Intent intent = new Intent(BillsActivity.this, BudgetMainMenuActivity.class);

            BillsActivity.this.finish();
            startActivity(intent);

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
                if(budgetItem.itemName.equals("Gas Bill")){
                    gas_flag = 99;
                    gas_edit_txt.setText(String.valueOf(budgetItem.amount));
                    gas_spinner.setSelection(budgetItem.frequency-1);

                } else if(budgetItem.itemName.equals("Water Bill")){
                    water_flag = 99;
                    water_edit_txt.setText(String.valueOf(budgetItem.amount));
                    water_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Electricity Bill")){
                    electricity_flag = 99;
                    electricity_edit_txt.setText(String.valueOf(budgetItem.amount));
                    electricity_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Phone Bill")){
                    phone_flag = 99;
                    phone_edit_txt.setText(String.valueOf(budgetItem.amount));
                    phone_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Internet Bill")){
                    internet_flag = 99;
                    internet_edit_txt.setText(String.valueOf(budgetItem.amount));
                    internet_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Insurance")){
                    insurance_flag = 99;
                    insurance_edit_txt.setText(String.valueOf(budgetItem.amount));
                    insurance_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Other Bills")){
                    other_flag = 99;
                    other_edit_txt.setText(String.valueOf(budgetItem.amount));
                    other_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Loans")){
                    loans_flag = 99;
                    loans_edit_txt.setText(String.valueOf(budgetItem.amount));
                    loans_spinner.setSelection(budgetItem.frequency-1);
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
                        .setMessage("Enter the details in the text box and select from the option on the right whether the value is Weekly, Fortnightly, Monthly, or Yearly. Press 'Save' to continue")
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
