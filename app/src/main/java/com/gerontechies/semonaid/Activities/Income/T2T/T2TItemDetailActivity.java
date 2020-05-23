package com.gerontechies.semonaid.Activities.Income.T2T;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.T2tAdapter;
import com.gerontechies.semonaid.Adapters.T2tInstructionsAdapter;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.T2TInstructionsItem;
import com.gerontechies.semonaid.Models.T2tItem;
import com.gerontechies.semonaid.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class T2TItemDetailActivity extends AppCompatActivity {

    RecyclerView t2tRV;
    SemonaidDB db = null;
    T2tItem item ;
    String id;
    String t2tName;
    List<T2TInstructionsItem> allItemList = new ArrayList<>();
    TextView nameTxt;
    ChipGroup materialsChipGroup;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t2_t_item_detail);

        setTitle("Instruction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getStringExtra("trash_id");

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();


        nameTxt = (TextView) findViewById(R.id.name_txt);
        materialsChipGroup = (ChipGroup) findViewById(R.id.materials_group);
        imageView = (ImageView) findViewById(R.id.item_image);
        t2tRV = (RecyclerView) findViewById(R.id.t2tRV);
        ReadDatabase rd = new ReadDatabase();
        rd.execute();
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getIdT2TItem(Integer.parseInt(id));
            return  status;
        }


        @Override
        protected void onPostExecute(String details) {

            Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
            nameTxt.setText(item.t2t);
            try {
                //setting the material chips
                JSONArray materials = new JSONArray(item.materials);
                for (int j = 0; j < materials.length(); j++) {
                    JSONObject object = materials.getJSONObject(j);
                    String name = object.getString("name");
                    Chip chip = new Chip(T2TItemDetailActivity.this);
                    chip.setTypeface(font);
                    chip.setText(name);
                    chip.setTextColor(T2TItemDetailActivity.this.getResources().getColorStateList(R.color.white));
                    chip.setChipBackgroundColor(T2TItemDetailActivity.this.getResources().getColorStateList(R.color.colorPrimary));
                    materialsChipGroup.addView(chip);

                }

                JSONArray instructions = new JSONArray(item.desc);
                for (int j = 0; j < instructions.length(); j++) {
                    JSONObject object = instructions.getJSONObject(j);
                    String name = object.getString("text");
                    T2TInstructionsItem t2TInstructionsItem = new T2TInstructionsItem();
                    t2TInstructionsItem.setText(name);
                    allItemList.add(t2TInstructionsItem);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            if(allItemList.size()>0){
                RecyclerView.LayoutManager mLayoutManagerT2t = new LinearLayoutManager(T2TItemDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                t2tRV.setLayoutManager(mLayoutManagerT2t);
                t2tRV.setItemAnimator(new DefaultItemAnimator());
                T2tInstructionsAdapter adapter = new T2tInstructionsAdapter(T2TItemDetailActivity.this,  allItemList);
                t2tRV.setAdapter(adapter);
                t2tRV.setNestedScrollingEnabled(false);
            }

            String src = item.getName();
            if (src.equals("BCC")) {
                imageView.setImageResource(R.drawable.bottlecapcross);
                Log.d("IMAge", src + "---");
            }
            if (src.equals("MJPV")) {
                imageView.setImageResource(R.drawable.masonjarvases);
                Log.d("IMAge", src + "---");
            }
            if (src.equals("PB")) {
                imageView.setImageResource(R.drawable.paperbagbracelets);
                Log.d("IMAge", src + "---");
            }
            if (src.equals("PPM")) {
                imageView.setImageResource(R.drawable.paperbagplacemat);
                Log.d("IMAge", src + "---");
            }
            if (src.equals("TN")) {
                imageView.setImageResource(R.drawable.tshirtnecklace);
                Log.d("IMAge", src + "---");
            }
            if (src.equals("DTB")) {
                imageView.setImageResource(R.drawable.denimtotebag);
                Log.d("IMAge", src + "---");
            }
            if (src.equals("PBL")) {
                imageView.setImageResource(R.drawable.pilllamp);
                Log.d("IMAge", src + "---");
            }
            if (src.equals("FBV")) {
                imageView.setImageResource(R.drawable.pillbamboovase);
                Log.d("IMAge", src + "---");
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
                        .setMessage("This page shows the details on how to make " + item.t2t+ " from your trash" )
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
