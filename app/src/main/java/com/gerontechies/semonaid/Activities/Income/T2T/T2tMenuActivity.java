package com.gerontechies.semonaid.Activities.Income.T2T;

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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;
import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.OpshopDatabase;
import com.gerontechies.semonaid.Models.OpshopItem;
import com.gerontechies.semonaid.Models.T2tDatabase;
import com.gerontechies.semonaid.Models.T2tItem;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class T2tMenuActivity extends AppCompatActivity {

    Button btn_next;
    CardView convert_trash, sell_treasure;
    String jsonData;
    SemonaidDB db = null;
    List<T2tItem> allItemList = new ArrayList<>();
    List<T2tItem> item;

    List<OpshopItem> allItemList1 = new ArrayList<>();
    List<OpshopItem> item1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t2t_menu);
        setTitle("Trash to Treasure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();




        ReadDatabase ld = new ReadDatabase();
        ld.execute();

        convert_trash = (CardView) findViewById(R.id.convert_trash);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);

        convert_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(T2tMenuActivity.this, T2tCategoryListActivity.class);
                startActivity(intent);

            }
        });

        sell_treasure = (CardView) findViewById(R.id.sell_treasure);

        sell_treasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(T2tMenuActivity.this, OpshopListActivity.class);
                intent.putExtra("from_results", "no");

                startActivity(intent);
            }
        });
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllT2TItems();
            if (!(item.isEmpty() || item == null) ){
                for (T2tItem temp : item) {

                    allItemList.add(temp);

                }
            }
            item1 = db.AppDAO().getAllOpShops();
            if (!(item1.isEmpty() || item1 == null) ){
                for (OpshopItem temp : item1) {

                    allItemList1.add(temp);

                }
            }
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            if(allItemList.size()>1){
                Log.d("Data", "Loaded");
            } else{

                LoadData ld = new LoadData();
                ld.execute();

            }

            if(allItemList1.size()>1){
                Log.d("Data", "Loaded");
            } else{

                LoadData1 ld1 = new LoadData1();
                ld1.execute();

            }
        }
    }

    private class LoadData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String data =  loadJSONFromAsset();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    T2tItem item = new T2tItem();
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String category = object.getString("category");
                    String t2t = object.getString("title");
                    String desc = object.getString("desc");
                    String material = object.getString("materials");


                    item.setId(id);
                    item.setCategory(category);
                    item.setName(name);
                    item.setT2t(t2t);
                    item.setDesc(desc);
                    item.setMaterials(material);

                    db.AppDAO().insertT2TItem(item);
                    Log.d("trash", t2t);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


        @Override
        protected void onPostExecute(String details) {

        }

    }

    private class LoadData1 extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String data =  loadJSONFromAsset1();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    OpshopItem item_1 = new OpshopItem();
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String address = object.getString("address");
                    String street = object.getString("street");
                    String suburb = object.getString("suburb");
                    String latt = object.getString("lat");
                    double lat = Double.parseDouble(latt);
                    String lngg = object.getString("lng");
                    double lng = Double.parseDouble(lngg);
                    String monday = object.getString("monday");
                    String tuesday = object.getString("tuesday");
                    String wednesday = object.getString("wednesday");
                    String thursday = object.getString("thursday");
                    String friday = object.getString("friday");
                    String saturday = object.getString("saturday");
                    String sunday = object.getString("sunday");
                    String phone = object.getString("phone");
                    String website = object.getString("website");

                    item_1.setId(id);
                    item_1.setName(name);
                    item_1.setAddress(address);
                    item_1.setStreet(street);
                    item_1.setSuburb(suburb);
                    item_1.setLat(lat);
                    item_1.setLng(lng);
                    item_1.setMonday(monday);
                    item_1.setTuesday(tuesday);
                    item_1.setWednesday(wednesday);
                    item_1.setThursday(thursday);
                    item_1.setFriday(friday);
                    item_1.setSaturday(saturday);
                    item_1.setSunday(sunday);
                    item_1.setPhone(phone);
                    item_1.setWebsite(website);

                    db.AppDAO().insertOpShop(item_1);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


        @Override
        protected void onPostExecute(String details) {

        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("T2TItems.json");
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

    public String loadJSONFromAsset1() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("OpShopList.json");
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
                        .setMessage("Convert Trash:\nLook at the ways in which you can change things you don't need into something useful\n\n" +
                                "Sell Treasure:\nFind out where you can go to sell these new items you have created")
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
