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

public class PersonalActivity extends AppCompatActivity {

    SemonaidDB db = null;
    private SharedPreferences calculatorPreferences;
    private SharedPreferences.Editor calculatorPrefEditor;
    Bundle BundleBudget;
    String CATEGORY = "Personal Expenses";
    Bundle BudgetCalculator = new Bundle();
    List<BudgetItem> item;
    EditText clothing_edit_txt, doctor_edit_txt, entertainment_edit_txt, pets_edit_txt,  other_edit_txt;
    Spinner clothing_spinner, doctor_spinner, entertainment_spinner, pets_spinner,  other_spinner ;
    int TYPE = 999;
    List<BudgetItem> BudgetItemList = new ArrayList<>();
    int clothing_flag = 0, doctor_flag = 0, entertainment_flag = 0, pets_flag = 0,  other_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        clothing_spinner = (Spinner) findViewById(R.id.spinner_clothing);
        doctor_spinner = (Spinner) findViewById(R.id.spinner_doc);
        entertainment_spinner = (Spinner) findViewById(R.id.spinner_entertainment);
        pets_spinner = (Spinner) findViewById(R.id.spinner_pet);
        other_spinner = (Spinner) findViewById(R.id.spinner_other_personal);

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


        //setting the spinner item style
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,items
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        //setting the spinners content
        clothing_spinner.setAdapter(spinnerArrayAdapter);
        doctor_spinner.setAdapter(spinnerArrayAdapter);
        entertainment_spinner.setAdapter(spinnerArrayAdapter);
        pets_spinner.setAdapter(spinnerArrayAdapter);
        other_spinner.setAdapter(spinnerArrayAdapter);




        clothing_edit_txt = (EditText) findViewById(R.id.editText_clothing);
        doctor_edit_txt = (EditText) findViewById(R.id.editText_doc);
        entertainment_edit_txt = (EditText) findViewById(R.id.editText_entertainment);
        pets_edit_txt = (EditText) findViewById(R.id.editText_pet);
        other_edit_txt = (EditText) findViewById(R.id.editText_other_personal);


        //Setting the fonts for the edit texts
        clothing_edit_txt.setTypeface(font);
        doctor_edit_txt.setTypeface(font);
        entertainment_edit_txt.setTypeface(font);
        pets_edit_txt.setTypeface(font);
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

            String  clothing,  doctor, entertainment, pets, other;
            clothing = clothing_edit_txt.getText().toString();
            entertainment = entertainment_edit_txt.getText().toString();
            doctor = doctor_edit_txt.getText().toString();
            pets = pets_edit_txt.getText().toString();
            other = other_edit_txt.getText().toString();


            //Spinner selected items

            String clothing_sp = clothing_spinner.getSelectedItem().toString();
            String doctor_sp = doctor_spinner.getSelectedItem().toString();
            String entertainment_sp = entertainment_spinner.getSelectedItem().toString();
            String pets_sp = pets_spinner.getSelectedItem().toString();
            String other_sp = other_spinner.getSelectedItem().toString();


            /* The below section checks to see if the value has already been inputted before. If so, it just edits it based on the
             * current selection.
             * Else it adds a new entry to the db with the values*/

            //clothing
            if(TextUtils.isEmpty(clothing)){
                if(clothing_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Clothing",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(clothing_sp);
                BudgetItem budgetItem = new BudgetItem("Clothing",Double.parseDouble(clothing),frequency,TYPE,CATEGORY);
                if(clothing_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(clothing_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }

            //doctor
            if(TextUtils.isEmpty(doctor)){
                if(doctor_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Doctor and Medicines",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }

            } else {
                int frequency = getFrequency(doctor_sp);
                BudgetItem budgetItem = new BudgetItem("Doctor and Medicines",Double.parseDouble(doctor),frequency,TYPE,CATEGORY);
                if(doctor_flag == 99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(doctor_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }



            //entertainment
            if(TextUtils.isEmpty(entertainment)){
                if(entertainment_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Entertainment",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(entertainment_sp);
                BudgetItem budgetItem = new BudgetItem("Entertainment",Double.parseDouble(entertainment),frequency,TYPE,CATEGORY);
                if(entertainment_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(entertainment_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }

            //pets
            if(TextUtils.isEmpty(pets)){
                if(pets_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Pets",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(pets_sp);
                BudgetItem budgetItem = new BudgetItem("Pets",Double.parseDouble(pets),frequency,TYPE,CATEGORY);
                if(pets_flag ==99){
                    db.AppDAO().updateBudgetItem(budgetItem);
                } else if(pets_flag == 0){
                    db.AppDAO().insertBudgetItem(budgetItem);
                }

            }



            //other
            if(TextUtils.isEmpty(other)){
                if(other_flag ==99){
                    BudgetItem budgetItem = new BudgetItem("Other Personal Bills",0,1,TYPE,CATEGORY);
                    db.AppDAO().updateBudgetItem(budgetItem);
                }
            } else {
                int frequency = getFrequency(other_sp);
                BudgetItem budgetItem = new BudgetItem("Other Personal Expenses",Double.parseDouble(other),frequency,TYPE,CATEGORY);
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


            Intent intent = new Intent(PersonalActivity.this, BudgetMainMenuActivity.class);
            PersonalActivity.this.finish();

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
                if(budgetItem.itemName.equals("Clothing")){
                    clothing_flag = 99;
                    clothing_edit_txt.setText(String.valueOf(budgetItem.amount));
                    clothing_spinner.setSelection(budgetItem.frequency-1);

                } else if(budgetItem.itemName.equals("Doctor and Medicines")){
                    doctor_flag = 99;
                    doctor_edit_txt.setText(String.valueOf(budgetItem.amount));
                    doctor_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Entertainment")){
                    entertainment_flag = 99;
                    entertainment_edit_txt.setText(String.valueOf(budgetItem.amount));
                    entertainment_spinner.setSelection(budgetItem.frequency-1);
                } else if(budgetItem.itemName.equals("Pets")){
                    pets_flag = 99;
                    pets_edit_txt.setText(String.valueOf(budgetItem.amount));
                    pets_spinner.setSelection(budgetItem.frequency-1);
                }  else if(budgetItem.itemName.equals("Other Personal Expenses")){
                    other_flag = 99;
                    other_edit_txt.setText(String.valueOf(budgetItem.amount));
                    other_spinner.setSelection(budgetItem.frequency-1);
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
