package com.gerontechies.semonaid.Activities.Budget;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gerontechies.semonaid.Activities.Budget.Tips.MenuActivity;
import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.Income.Skills.SkillsFilterActivity;
import com.gerontechies.semonaid.Activities.Services.ServiceInfoActivity;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.TipItem;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.gerontechies.semonaid.Activities.Income.Skills.SkillsQuizActivity.selectedCertfificationsList;
import static com.gerontechies.semonaid.Activities.Income.Skills.SkillsQuizActivity.selectedSkillsList;

public class BudgetInfoActivity extends AppCompatActivity {

    Button btn_next;
    CardView budget_btn, saving_btn, service_btn;
    SemonaidDB db = null;
    List<TipItem> allItemList = new ArrayList<>();
    List<TipItem> item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_info);
        setTitle("Learn to Save");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();


        ReadDatabase ld = new ReadDatabase();
        ld.execute();

        budget_btn = (CardView) findViewById(R.id.budget_calculator);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);

        budget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, BudgetMainMenuActivity.class);
                startActivity(intent);

            }
        });

        saving_btn = (CardView) findViewById(R.id.savings);

        saving_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, MenuActivity.class);
                intent.putExtra("from_results", "no");
                startActivity(intent);
            }
        });

        service_btn = (CardView) findViewById(R.id.assistance);
        service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetInfoActivity.this, ServiceInfoActivity.class);
                startActivity(intent);
            }
        });


    }

    //loading the tips from the json file
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("BudgetItems.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //adding the data to the db
    private class LoadData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String data = loadJSONFromAsset();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    TipItem item = new TipItem();
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String category = object.getString("category");
                    String tip = object.getString("text");
                    String title = object.getString("title");

                    item.setId(id);
                    item.setCategory(category);
                    item.setName(name);
                    item.setTip(tip);
                    item.setTitle(title);

                    db.AppDAO().insertTipItem(item);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }

    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllTipItem();
            if (!(item.isEmpty() || item == null)) {
                for (TipItem temp : item) {

                    allItemList.add(temp);

                }
            }

            return status;
        }


        @Override
        protected void onPostExecute(String details) {

            if (allItemList.size() > 1) {
            } else { //if the size is less than 1, it loads the data from the db

                LoadData ld = new LoadData();
                ld.execute();

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
                        .setMessage("Budget Calculator:\nUse the calculator to get an overall analysis of your current budget\n\n" +
                                "Save My Earnings:\nHave a look at the list of saving tips we have compiled to figure where you can save more money\n\n" +
                                "View Services:\nContains information on affordable/free services located around Melbourne that can assist you in saving your money")
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


    public void setTitle(String title) {
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
