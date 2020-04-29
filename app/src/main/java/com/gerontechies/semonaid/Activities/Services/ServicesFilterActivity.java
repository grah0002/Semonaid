
package com.gerontechies.semonaid.Activities.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.gerontechies.semonaid.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class ServicesFilterActivity extends AppCompatActivity {

    ChipGroup categories_chips;
    List<String> categoriesList;
    Button filter_save;
    CheckBox cb1,cb2,cb3, cb4, cb5, cb6,cb7, cb8, cb9, cb10, cb11, cb12, cb13;
    List<CheckBox> items = new ArrayList<CheckBox>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_filter);


        filter_save = (Button) findViewById(R.id.btn_filter_save);
        cb1 = (CheckBox) findViewById(R.id.chip);
        cb2= (CheckBox) findViewById(R.id.chip2);
        cb3= (CheckBox) findViewById(R.id.chip3);
        cb4= (CheckBox) findViewById(R.id.chip4);
        cb5= (CheckBox) findViewById(R.id.chip5);
        cb6= (CheckBox) findViewById(R.id.chip6);
        cb7= (CheckBox) findViewById(R.id.chip7);
        cb8= (CheckBox) findViewById(R.id.chip8);
        cb9= (CheckBox) findViewById(R.id.chip9);
        cb10= (CheckBox) findViewById(R.id.chip11);
        cb11= (CheckBox) findViewById(R.id.chip12);
        cb12= (CheckBox) findViewById(R.id.chip13);



        filter_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cb1.isChecked())
                    categoriesList.add(cb1.getText().toString());
                if(cb2.isChecked())
                    categoriesList.add(cb2.getText().toString());

              //  categoriesList.add(txt);

                if(categoriesList.size()>0){
                    for(int i=0; i<categoriesList.size(); i++){
                        Log.d("Chip", categoriesList.get(i));
                    }
                }
            }
        });




    }

}
