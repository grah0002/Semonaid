package com.gerontechies.semonaid.Activities.MentalWellbeing.Yoga;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.YogaAdapter;
import com.gerontechies.semonaid.Models.YogaDatabase;
import com.gerontechies.semonaid.Models.YogaItem;
import com.gerontechies.semonaid.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class YogaListActivity extends AppCompatActivity {

    RecyclerView yogaRV;
    YogaDatabase db = null;
    List<YogaItem> item ;
    String yogaName;

    List<YogaItem> allItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_list);
        setTitle("Yoga Techniques");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        TextView yogaNameText = (TextView) findViewById(R.id.yogaName_txt);

//        if(yogaName.equals("Mountain")){
//            yogaNameText.setText("Mountain Pose");
  //      } else if( yogaName.equals("Tree")){
    //        yogaNameText.setText("Tree Pose");
      //  } else  if(yogaName.equals("Sphinx")){
//            yogaNameText.setText("Sphinx Pose");
  //      } else  if(yogaName.equals("Bird")){
    //        yogaNameText.setText("Bird Pose");
      //  } else if( yogaName.equals("Dog")){
//            yogaNameText.setText("Downward Facing Dog");
  //      } else if( yogaName.equals("Savasana")){
    //        yogaNameText.setText("Savasana");
      //  }
//        Log.d("YOGA",yogaName);


        db = Room.databaseBuilder(this,
                YogaDatabase.class, "yoga_database")
                .fallbackToDestructiveMigration()
                .build();




        yogaRV = (RecyclerView) findViewById(R.id.yogaRV);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    };

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            Log.d("YY", "II");
            String status = "";
            item = db.YogaDAO().getAll();
            if (!(item.isEmpty() || item == null) ) {
                for (YogaItem temp : item) {
                    allItemList.add(temp);
                    Log.d("YOGA",temp.name);
                }

            }
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            if(allItemList.size()>0){
                RecyclerView.LayoutManager mLayoutManagerYoga = new LinearLayoutManager(YogaListActivity.this, LinearLayoutManager.VERTICAL, false);
                yogaRV.setLayoutManager(mLayoutManagerYoga);
                yogaRV.setItemAnimator(new DefaultItemAnimator());
                YogaAdapter adapter = new YogaAdapter(YogaListActivity.this,  allItemList);
                yogaRV.setAdapter(adapter);
                yogaRV.setNestedScrollingEnabled(false);
            } else{
                LoadData ld = new LoadData();
                ld.execute();
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

                    YogaItem item = new YogaItem();
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String yoga = object.getString("text");
                    String image = object.getString("image");
                    String title = object.getString("title");


                    item.setId(id);
                    item.setName(name);
                    item.setImage(image);
                    item.setYoga(yoga);
                    item.setTitle(title);

                    db.YogaDAO().insert(item);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return " ";
        }


        @Override
        protected void onPostExecute(String details) {
            ReadDatabase rd = new ReadDatabase();
            rd.execute();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("YogaItems.json");
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
    public boolean onOptionsItemSelected(MenuItem item1) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item1.getItemId();

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
                        .setMessage("Have a look at the simpler Yoga poses you could practice.\n\nTap on them to learn more details on how to perform them and what benefits they bring" )
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
        return super.onOptionsItemSelected(item1);
    }

    public void setTitle(String title){
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
