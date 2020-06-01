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

public class HousingExpensesActivity extends AppCompatActivity {

    SemonaidDB db = null;
    private SharedPreferences calculatorPreferences;
    private SharedPreferences.Editor calculatorPrefEditor;
    Bundle BundleBudget;
    String CATEGORY = "Household Expenses";
    List<BudgetItem> item;
    EditText rent_edit_text,groceries_edit_txt , other_edit_txt ;
    Spinner rent_spinner, groceries_spinner, other_spinner ;
    double expenseTotal = 0.0;
    int TYPE = 999; //type 999 values need to be reduced from the total
    List<BudgetItem> BudgetItemList = new ArrayList<>();
    int rent_flag = 0, other_flag = 0, groceries_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing_expenses);

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

        rent_spinner = (Spinner) findViewById(R.id.spinner_rent);
        groceries_spinner = (Spinner) findViewById(R.id.spinner_groceries);
        other_spinner = (Spinner) findViewById(R.id.spinner_house_other);


        //setting the spinner item style
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,items
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        //setting the spinners content
        rent_spinner.setAdapter(spinnerArrayAdapter);
        groceries_spinner.setAdapter(spinnerArrayAdapter);
        other_spinner.setAdapter(spinnerArrayAdapter);

        rent_edit_text = (EditText) findViewById(R.id.editText_rent);
        groceries_edit_txt = (EditText) findViewById(R.id.editText_groceries);
        other_edit_txt = (EditText) findViewById(R.id.editText_house_other);


        //Setting the fonts for the edit texts
        rent_edit_text.setTypeface(font);
        groceries_edit_txt.setTypeface(font);
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

            String  rent,  other_expense, groceries;
            rent = rent_edit_text.getText().toString();
            other_expense = other_edit_txt.getText().toString();
            groceries = groceries_edit_txt.getText().toString();

            //Spinner selected items

            String rent_sp = rent_spinner.getSelectedItem().toString();
            String other_sp = other_spinner.getSelectedItem().toString();
            String groceries_sp = groceries_spinner.getSelectedItem().toString();

            /* The below section checks to see if the value has already been inputted before. If so, it just edits it based on the
             * current selection.
             * Else it adds a new entry to the db with the values*/


            if(TextUtils.isEmpty(rent)){
                if(rent_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Rent",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                expenseTotal = expenseTotal + Double.parseDouble(rent);
                int frequency = getFrequency(rent_sp);
                BudgetItem budgetItem = new BudgetItem("Rent",Double.parseDouble(rent),frequency,TYPE,CATEGORY);
                if(rent_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(rent_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }



            if(TextUtils.isEmpty(other_expense)){
                if(other_flag==99){
                    BudgetItem budgetItem = new BudgetItem("Other Expense",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }

            } else {
                expenseTotal = expenseTotal + Double.parseDouble(other_expense);
                int frequency = getFrequency(other_sp);
                BudgetItem budgetItem = new BudgetItem("Other Expense",Double.parseDouble(other_expense),frequency,TYPE,CATEGORY);
                if(other_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(other_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }



            if(TextUtils.isEmpty(groceries)){
                if(groceries_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Groceries",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                expenseTotal = expenseTotal + Double.parseDouble(groceries);
                int frequency = getFrequency(groceries_sp);
                BudgetItem budgetItem = new BudgetItem("Groceries",Double.parseDouble(groceries),frequency,TYPE,CATEGORY);
                if(groceries_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(groceries_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }




            return null;
        }

        @Override
        protected void onPostExecute(String details) {


            HousingExpensesActivity.this.finish();
            Intent intent = new Intent(HousingExpensesActivity.this, BudgetMainMenuActivity.class);

            finish();

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
                if(budgetItem.itemName.equals("Rent")){
                    rent_flag = 99;
                    rent_edit_text.setText(String.valueOf(budgetItem.amount));
                    rent_spinner.setSelection(budgetItem.frequency-1);

                } else if(budgetItem.itemName.equals("Other Expense")){
                    other_flag = 99;
                    other_edit_txt.setText(String.valueOf(budgetItem.amount));
                    other_spinner.setSelection(budgetItem.frequency-1);

                } else if(budgetItem.itemName.equals("Groceries")){
                    groceries_flag = 99;
                    groceries_edit_txt.setText(String.valueOf(budgetItem.amount));
                    groceries_spinner.setSelection(budgetItem.frequency-1);

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
